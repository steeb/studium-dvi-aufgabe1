/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
    @NamedQuery(name="getAllKunde",query="select k from Kunde k")
})

/**
 *
 * @author steeb
 */
@Entity
public class Kunde implements edu.whs.dvi.aufgabe1.entities.Kunde, Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long kundennr;
    private String name;
    private String vorname;
    private String strasse;
    private String plz;
    private String wohnort;
    @OneToMany(mappedBy="kunde",targetEntity=Bestellung.class,cascade=CascadeType.ALL)
    Collection<edu.whs.dvi.aufgabe1.entities.Bestellung> bestellungen;

    public Kunde() {
    }
    
    public Kunde(long kundennr) {
        this.kundennr = kundennr;
    }
    
    public Kunde(long kundennr, String name, String vorname, String strasse, String plz, String wohnort) {
        this.kundennr = kundennr;
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.plz = plz;
        this.wohnort = wohnort;
    }

    public Kunde(String name, String vorname, String strasse, String plz, String wohnort) {
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.plz = plz;
        this.wohnort = wohnort;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPLZ() {
        return plz;
    }

    public void setPLZ(String plz) {
        this.plz = plz;
    }

    @Override
    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    @Override
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Override
    public String getWohnort() {
        return wohnort;
    }

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    @Override
    public long getKundennr() {
        return kundennr;
    }

    public void setKundennr(long kundennr) {
        this.kundennr = kundennr;
    }
    
    public Collection<edu.whs.dvi.aufgabe1.entities.Bestellung> getBestellungen() {
        return this.bestellungen;
    }
    
        @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kunde other = (Kunde) obj;
        if (this.kundennr != other.kundennr) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (this.kundennr ^ (this.kundennr >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return name + ", " + vorname + "(" + kundennr + ")";
    }

    
}
