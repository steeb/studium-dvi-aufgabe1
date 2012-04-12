/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.whs.dvi.aufgabe1.dataaccess;

import edu.whs.dvi.ApplicationException;
import edu.whs.dvi.aufgabe1.entities.Artikel;
import edu.whs.dvi.aufgabe1.entities.Bestellung;
import edu.whs.dvi.aufgabe1.entities.Kategorie;
import edu.whs.dvi.aufgabe1.entities.Kunde;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import se.edu.whs.dvi.aufgabe1.DVIAufgabe1;

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
        DVIAufgabe1.LOGGER.log(Level.INFO, "SQL: getAllkategorien ({0})", oberkategorie);
        Query q;
        if (oberkategorie == null)
            q = em.createNamedQuery("getAllOberkategorie");
        else {
            q = em.createNamedQuery("getAllKategorie");
            q.setParameter("kat", oberkategorie);
        }
        DVIAufgabe1.LOGGER.log(Level.INFO, "SQL Result: {0}", q.getResultList());
        return q.getResultList();
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> artikel, long kundennr) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Bestellung startBestellung(Map<Artikel, Integer> arg0, String arg1, String arg2, String arg3, String arg4, String arg5) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Bestellung> getAllBestellungFor(long kundennummer) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Kunde> getAllKunde() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeKunde(long kundennummer) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateKunde(long arg0, String arg1, String arg2, String arg3, String arg4, String arg5) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() throws ApplicationException {
        DVIAufgabe1.LOGGER.log(Level.INFO, "em closing ...");
        em.close();
        DVIAufgabe1.LOGGER.log(Level.INFO, "... em closing");
    }
    
}
