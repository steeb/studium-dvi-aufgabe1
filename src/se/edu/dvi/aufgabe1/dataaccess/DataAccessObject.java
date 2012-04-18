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
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import se.edu.dvi.aufgabe1.DVIAufgabe1;
import se.edu.dvi.aufgabe1.entities.Bestellposition;

/**
 *
 * @author steeb
 */
public class DataAccessObject implements edu.whs.dvi.aufgabe1.dataaccess.DataAccessObject {

    private EntityManager em;

    public DataAccessObject() {
        DVIAufgabe1.LOGGER.log(Level.INFO, "em starting ...");
        em = Persistence.createEntityManagerFactory("DVI_Aufgabe1PU").createEntityManager();
        DVIAufgabe1.LOGGER.log(Level.INFO, "... em starting");
    }

    @Override
    public Collection<Kategorie> getAllKategorie(Kategorie oberkategorie) {
        DVIAufgabe1.LOGGER.log(Level.INFO, "SQL: getAllkategorie ({0})", oberkategorie);
        Query q;
        if (oberkategorie == null) {
            q = em.createNamedQuery("getAllOberkategorie");
        } else {
            q = em.createNamedQuery("getAllKategorie");
            q.setParameter("kat", oberkategorie);
        }
        DVIAufgabe1.LOGGER.log(Level.INFO, "SQL Result: {0}", q.getResultList());
        return q.getResultList();
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> artikel, long kundennr) throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "startBestellung (artikel {0} kundennr {1}", new Object[]{artikel, kundennr});
        
        se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennr); 
        if (kunde == null) throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennr + " ist nicht vorhanden.");
        
        se.edu.dvi.aufgabe1.entities.Bestellung bestellung = new se.edu.dvi.aufgabe1.entities.Bestellung();
        Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellposition= new HashSet<edu.whs.dvi.aufgabe1.entities.Bestellposition>();
        for (Artikel ar : artikel.keySet())
            bestellposition.add(new Bestellposition(bestellung, ar, artikel.get(ar).intValue(), new Long(ar.getPreis()).intValue()));
        bestellung.setBestellpositionen(bestellposition);
        bestellung.setBestelldatum(Calendar.getInstance().getTime());
        bestellung.setKunde(kunde);
        
        em.getTransaction().begin();
        em.persist(bestellung);
        em.getTransaction().commit();
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "... startBestellung");
        
        return bestellung;
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> artikel, String name, String vorname, String strasse, String plz, String wohnort) throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "startBestellung (artikel {0} name {1} vorname {2} strasse {3} plz {4} wohnort {5} ...", new Object[]{artikel, name, vorname, strasse, plz, wohnort});
        
        se.edu.dvi.aufgabe1.entities.Bestellung bestellung = new se.edu.dvi.aufgabe1.entities.Bestellung();
        Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellposition= new HashSet<edu.whs.dvi.aufgabe1.entities.Bestellposition>();
        for (Artikel ar : artikel.keySet())
            bestellposition.add(new Bestellposition(bestellung, ar, artikel.get(ar).intValue(), new Long(ar.getPreis()).intValue()));
        bestellung.setBestellpositionen(bestellposition);
        bestellung.setBestelldatum(Calendar.getInstance().getTime());
        bestellung.setKunde(new se.edu.dvi.aufgabe1.entities.Kunde(name, vorname, strasse, plz, wohnort));
        
        em.getTransaction().begin();
        em.persist(bestellung);
        em.getTransaction().commit();
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "... startBestellung");
        
        return bestellung;
    }

    @Override
    public Collection<Bestellung> getAllBestellungFor(long kundennummer) throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "getAllBestellungFor (kundennummer {0}) ...", kundennummer);
        
        se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer); 
        if (kunde == null) throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "... getAllBestellungFor");
        
        return ((Collection<edu.whs.dvi.aufgabe1.entities.Bestellung>)kunde.getBestellungen());
    }

    @Override
    public Collection<Kunde> getAllKunde() {
        DVIAufgabe1.LOGGER.log(Level.INFO, "getAllKunde");
        return em.createNamedQuery("getAllKunde").getResultList();
    }

    @Override
    public void removeKunde(long kundennummer) throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "removeKunde (kundennummer {0}) ...", kundennummer);
        
        em.getTransaction().begin();
        se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer);
        if (kunde == null) throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");

        em.remove(em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer));
        em.getTransaction().commit();
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "... removeKunde");
    }

    @Override
    public void updateKunde(long kundennummer, String name, String vorname, String strasse, String plz, String wohnort) throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "updateKunde (kundennummer {0}, name {1}, vorname {2}, strasse {3}, plz {4}, wohnort {5}) ...", new Object[]{kundennummer, name, vorname, strasse, plz, wohnort});
        
        em.getTransaction().begin();
        se.edu.dvi.aufgabe1.entities.Kunde kunde = em.find(se.edu.dvi.aufgabe1.entities.Kunde.class, kundennummer);
        if (kunde == null) throw new ApplicationException("Kunde nicht vorhanden", "Ein Kunde mit der Kundennr.: " + kundennummer + " ist nicht vorhanden.");

        DVIAufgabe1.LOGGER.log(Level.INFO, "Kunde aus DB vor Änderung {0}", kunde);
        
        kunde.setName(name);
        kunde.setVorname(vorname);
        kunde.setStrasse(strasse);
        kunde.setPLZ(plz);
        kunde.setWohnort(wohnort);
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "Kunde nach Änderung {0}", kunde);
        
        em.merge(kunde);
        em.getTransaction().commit();
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "Kunde nach refresh {0}", kunde);
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "... updateKunde");
    }

    @Override
    public void close() throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "em closing ...");
        em.close();
        DVIAufgabe1.LOGGER.log(Level.INFO, "... em closing");
    }
}
