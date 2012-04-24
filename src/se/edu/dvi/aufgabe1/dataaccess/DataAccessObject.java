/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.dataaccess;

import edu.whs.dvi.ApplicationException;
import edu.whs.dvi.aufgabe1.entities.Artikel;
import edu.whs.dvi.aufgabe1.entities.Bestellung;
import edu.whs.dvi.aufgabe1.entities.Kategorie;
import edu.whs.dvi.aufgabe1.entities.Kunde;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import se.edu.dvi.aufgabe1.DVIAufgabe1;
import se.edu.dvi.aufgabe1.entities.Bestellposition;

/**
 *
 * @author steeb
 */
public class DataAccessObject implements edu.whs.dvi.aufgabe1.dataaccess.DataAccessObject {

    private EntityManager em;
    private EntityManagerFactory emf;

    public DataAccessObject() {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "em starting ...");
        emf = Persistence.createEntityManagerFactory("DVI_Aufgabe1PU");
        em = emf.createEntityManager();
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... em starting");
    }

    @Override
    public Collection<Kategorie> getAllKategorie(Kategorie oberkategorie) {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "SQL: getAllkategorie ({0})", oberkategorie);
        Query q;
        Collection<Kategorie> r = null;
        try {
            if (oberkategorie == null) {
                q = em.createNamedQuery("getAllOberkategorie");
            } else {
                q = em.createNamedQuery("getAllKategorie");
                q.setParameter("kat", oberkategorie);
            }
            r = q.getResultList();
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "SQL Result: {0}", r);
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
        }
        return r;
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> artikel, long kundennr) throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "startBestellung (artikel {0} kundennr {1}", new Object[]{artikel, kundennr});

        se.edu.dvi.aufgabe1.entities.Bestellung bestellung = null;
        try {
            se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennr);
            if (kunde == null) {
                throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennr + " ist nicht vorhanden.");
            }

            bestellung = new se.edu.dvi.aufgabe1.entities.Bestellung();
            Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellposition = new HashSet<edu.whs.dvi.aufgabe1.entities.Bestellposition>();
            for (Artikel ar : artikel.keySet()) {
                bestellposition.add(new Bestellposition(bestellung, ar, artikel.get(ar).intValue(), new Long(ar.getPreis()).intValue()));
            }
            bestellung.setBestellpositionen(bestellposition);
            bestellung.setBestelldatum(Calendar.getInstance().getTime());
            bestellung.setKunde(kunde);

            em.getTransaction().begin();
            em.persist(bestellung);
            em.getTransaction().commit();
            em.refresh(kunde);
        } catch (RollbackException re) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Commit schlug fehl", re);
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... startBestellung");

        return bestellung;
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> artikel, String name, String vorname, String strasse, String plz, String wohnort) throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "startBestellung (artikel {0} name {1} vorname {2} strasse {3} plz {4} wohnort {5} ...", new Object[]{artikel, name, vorname, strasse, plz, wohnort});

        se.edu.dvi.aufgabe1.entities.Bestellung bestellung = null;
        try {
            bestellung = new se.edu.dvi.aufgabe1.entities.Bestellung();
            Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellposition = new HashSet<edu.whs.dvi.aufgabe1.entities.Bestellposition>();
            for (Artikel ar : artikel.keySet()) {
                //if (ar.getLagerbestand() >= artikel.get(ar).intValue())
                    bestellposition.add(new Bestellposition(bestellung, ar, artikel.get(ar).intValue(), new Long(ar.getPreis()).intValue()));
                //else
                    //throw new ApplicationException("Artikel nicht vorhanden", "Artikel nicht vorhanden");
            }
            bestellung.setBestellpositionen(bestellposition);
            bestellung.setBestelldatum(Calendar.getInstance().getTime());
            se.edu.dvi.aufgabe1.entities.Kunde kunde = new se.edu.dvi.aufgabe1.entities.Kunde(name, vorname, strasse, plz, wohnort);
            bestellung.setKunde(kunde);

            em.getTransaction().begin();
            em.persist(bestellung);
            em.getTransaction().commit();
            em.refresh(kunde);
        } catch (RollbackException re) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Commit schlug fehl", re);
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... startBestellung");

        return bestellung;
    }

    @Override
    public Collection<Bestellung> getAllBestellungFor(long kundennummer) throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "getAllBestellungFor (kundennummer {0}) ...", kundennummer);

        se.edu.dvi.aufgabe1.entities.Kunde kunde = null;

        try {
            kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer);
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "K {0}", kunde);
            if (kunde == null) {
                throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");
            }
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        }

        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... getAllBestellungFor");

        return ((Collection<edu.whs.dvi.aufgabe1.entities.Bestellung>) kunde.getBestellungen());
    }

    @Override
    public Collection<Kunde> getAllKunde() {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "getAllKunde");
        try {
            return em.createNamedQuery("getAllKunde").getResultList();
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
        }
        return null;
    }

    @Override
    public void removeKunde(long kundennummer) throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "removeKunde (kundennummer {0}) ...", kundennummer);

        try {
            se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer);
            if (kunde == null) {
                throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");
            }

            em.getTransaction().begin();
            em.remove(em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer));
            em.getTransaction().commit();
        } catch (RollbackException re) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Commit schlug fehl", re);
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... removeKunde");
    }

    @Override
    public void updateKunde(long kundennummer, String name, String vorname, String strasse, String plz, String wohnort) throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "updateKunde (kundennummer {0}, name {1}, vorname {2}, strasse {3}, plz {4}, wohnort {5}) ...", new Object[]{kundennummer, name, vorname, strasse, plz, wohnort});

        try {
            se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer);
            if (kunde == null) {
                throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");
            }

            kunde.setName(name);
            kunde.setVorname(vorname);
            kunde.setStrasse(strasse);
            kunde.setPLZ(plz);
            kunde.setWohnort(wohnort);

            em.getTransaction().begin();
            em.merge(kunde);
            em.getTransaction().commit();
        } catch (RollbackException re) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Commit schlug fehl", re);
        } catch (PersistenceException e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Entity-Manager meldet einen Fehler", e);
            em.getTransaction().rollback();
        } catch (Throwable e) {
            Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.SEVERE, "Unerwarteter Fehler", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... updateKunde");
    }

    @Override
    public void close() throws ApplicationException {
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "em closing ...");
        em.close();
        emf.close();
        Logger.getLogger(DVIAufgabe1.class.getName()).log(Level.INFO, "... em closing");
    }
}
