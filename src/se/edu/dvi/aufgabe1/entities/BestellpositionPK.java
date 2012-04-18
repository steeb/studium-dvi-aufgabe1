/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.entities;

import java.io.Serializable;

/**
 *
 * @author steeb
 */
public class BestellpositionPK implements Serializable {

    long bestellung;
    long artikel;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BestellpositionPK other = (BestellpositionPK) obj;
        if (this.bestellung != other.bestellung) {
            return false;
        }
        if (this.artikel != other.artikel) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.bestellung ^ (this.bestellung >>> 32));
        hash = 97 * hash + (int) (this.artikel ^ (this.artikel >>> 32));
        return hash;
    }

    
    
    
}
