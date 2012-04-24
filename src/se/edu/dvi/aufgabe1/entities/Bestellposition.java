/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

/**
 *
 * @author steeb
 */
@Entity
@IdClass(BestellpositionPK.class)
public class Bestellposition implements edu.whs.dvi.aufgabe1.entities.Bestellposition, Serializable {
    
    @Id
    @OneToOne
    private Bestellung bestellung;
    @Id
    @OneToOne(targetEntity=Artikel.class)
    private edu.whs.dvi.aufgabe1.entities.Artikel artikel;
    private int anzahl;
    private int preis;

    public Bestellposition() {
    }

    public Bestellposition(Bestellung bestellung, edu.whs.dvi.aufgabe1.entities.Artikel artikel, int anzahl, int preis) {
        this.bestellung = bestellung;
        this.artikel = artikel;
        this.anzahl = anzahl;
        this.preis = preis;
    }

    @Override
    public Bestellung getBestellung() {
        return this.bestellung;
    }

    @Override
    public Artikel getArtikel() {
        return (Artikel) this.artikel;
    }

    @Override
    public int getAnzahl() {
        return this.anzahl;
    }

    @Override
    public long getPreis() {
        return this.preis;
    }
    
    @Override
    public String toString() {
        return anzahl + "x " + artikel.getName();
    }
    
}
