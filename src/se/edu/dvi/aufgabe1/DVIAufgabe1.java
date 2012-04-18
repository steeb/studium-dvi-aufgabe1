/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.edu.dvi.aufgabe1.dataaccess.DataAccessObject;


/**
 *
 * @author steeb
 */
public class DVIAufgabe1 {

    public final static Logger LOGGER = Logger.getLogger("Allgemein");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DVIAufgabe1.LOGGER.setLevel(Level.ALL);
        
        DVIAufgabe1.LOGGER.log(Level.INFO, "smf ...");
        new edu.whs.dvi.ui.ShopMainFrame(new DataAccessObject()).setVisible(true);
        DVIAufgabe1.LOGGER.log(Level.INFO, "... smf");
    }
}
