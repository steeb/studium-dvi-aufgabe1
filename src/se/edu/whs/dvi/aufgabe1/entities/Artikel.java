/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.whs.dvi.aufgabe1.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author steeb
 */
@Entity
public class Artikel implements edu.whs.dvi.aufgabe1.entities.Artikel, Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long artikelNr;
    private String name;
    private String beschreibung;
    private long Preis;
    private long lagerbestand;
    @ManyToOne
    private Kategorie kategorie;

    public Artikel() {
    }

    public Artikel(long artikelNr, String name, String beschreibung, long Preis, long lagerbestand, Kategorie kategorie) {
        this.artikelNr = artikelNr;
        this.name = name;
        this.beschreibung = beschreibung;
        this.Preis = Preis;
        this.lagerbestand = lagerbestand;
        this.kategorie = kategorie;
    }

    public void setPreis(long Preis) {
        this.Preis = Preis;
    }

    public void setArtikelNr(long artikelNr) {
        this.artikelNr = artikelNr;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public void setLagerbestand(long lagerbestand) {
        this.lagerbestand = lagerbestand;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getPreis() {
        return Preis;
    }

    @Override
    public long getArtikelNr() {
        return artikelNr;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public Kategorie getKategorie() {
        return kategorie;
    }

    @Override
    public long getLagerbestand() {
        return lagerbestand;
    }

    @Override
    public String getName() {
        return name;
    }
    

    
}
