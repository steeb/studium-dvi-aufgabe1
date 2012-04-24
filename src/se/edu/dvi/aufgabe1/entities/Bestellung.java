/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.entities;

import edu.whs.dvi.aufgabe2.entities.Versandfirma;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author steeb
 */
@Entity
public class Bestellung implements edu.whs.dvi.aufgabe1.entities.Bestellung, Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long bestellNr;
    @Temporal(TemporalType.DATE)
    private Date bestellDatum;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Kunde kunde;
    @OneToMany(targetEntity=Bestellposition.class,mappedBy="bestellung",cascade=CascadeType.ALL,orphanRemoval=true)
    Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellpositionen;

    public Bestellung(Date bestellDatum, Kunde kunde, Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellpositionen) {
        this.bestellDatum = bestellDatum;
        this.kunde = kunde;
        this.bestellpositionen = bestellpositionen;
    }

    public Bestellung() {
    }

    public void setBestellpositionen(Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> bestellpositionen) {
        this.bestellpositionen = bestellpositionen;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> getBestellpositionen() {
        return bestellpositionen;
    }
    
    @Override
    public Date getBestelldatum() {
        return bestellDatum;
    }

    public void setBestelldatum(Date bestellDatum) {
        this.bestellDatum = bestellDatum;
    }

    @Override
    public long getBestellnr() {
        return bestellNr;
    }

    public void setBestellnr(long bestellNr) {
        this.bestellNr = bestellNr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bestellung other = (Bestellung) obj;
        if (this.bestellNr != other.bestellNr) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.bestellNr ^ (this.bestellNr >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return bestellNr + "";
    }

    @Override
    public Collection<edu.whs.dvi.aufgabe1.entities.Bestellposition> getPositionen() {
        return this.bestellpositionen;
    }

    @Override
    public Kunde getKunde() {
        return this.kunde;
    }

    @Override
    public Versand getVersand() {
        return null;
    }
    
    public class Versand implements edu.whs.dvi.aufgabe2.entities.Versand {

        @Override
        public long getVersandnr() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Versandfirma getVersandfirma() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    
}
