/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.edu.dvi.aufgabe1.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
    @NamedQuery(name="getAllKategorie",
        query="select k from Kategorie k where k.oberkategorie = :kat"),
    @NamedQuery(name="getAllOberkategorie",
        query="select k from Kategorie k where k.oberkategorie is null")
})

/**
 *
 * @author steeb
 */
@Entity
public class Kategorie implements edu.whs.dvi.aufgabe1.entities.Kategorie, Serializable {
    
    @Id
    String name;
    @OneToMany(targetEntity=Artikel.class,mappedBy="kategorie",cascade= CascadeType.ALL)
    Collection<edu.whs.dvi.aufgabe1.entities.Artikel> artikel;
    @ManyToOne
    Kategorie oberkategorie;

    public Kategorie(String name, Collection<edu.whs.dvi.aufgabe1.entities.Artikel> artikel, Kategorie oberkategorie) {
        this.name = name;
        this.artikel = artikel;
        this.oberkategorie = oberkategorie;
    }

    public Kategorie() {
    }

    @Override
    public Collection<edu.whs.dvi.aufgabe1.entities.Artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(Collection<edu.whs.dvi.aufgabe1.entities.Artikel> artikel) {
        this.artikel = artikel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kategorie other = (Kategorie) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Kategorie getOberkategorie() {
        return oberkategorie;
    }

    public void setOberkategorie(Kategorie oberkategorie) {
        this.oberkategorie = oberkategorie;
    }

}
