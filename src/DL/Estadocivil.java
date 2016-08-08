/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DL;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kathy
 */
@Entity
@Table(name = "estadocivil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadocivil.findAll", query = "SELECT e FROM Estadocivil e"),
    @NamedQuery(name = "Estadocivil.findByIdestadocivil", query = "SELECT e FROM Estadocivil e WHERE e.idestadocivil = :idestadocivil"),
    @NamedQuery(name = "Estadocivil.findByNombreestadocivil", query = "SELECT e FROM Estadocivil e WHERE e.nombreestadocivil = :nombreestadocivil")})
public class Estadocivil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idestadocivil")
    private Integer idestadocivil;
    @Basic(optional = false)
    @Column(name = "nombreestadocivil")
    private String nombreestadocivil;
    @OneToMany(mappedBy = "idestadocivil")
    private Collection<Persona> personaCollection;

    public Estadocivil() {
    }

    public Estadocivil(Integer idestadocivil) {
        this.idestadocivil = idestadocivil;
    }

    public Estadocivil(Integer idestadocivil, String nombreestadocivil) {
        this.idestadocivil = idestadocivil;
        this.nombreestadocivil = nombreestadocivil;
    }

    public Integer getIdestadocivil() {
        return idestadocivil;
    }

    public void setIdestadocivil(Integer idestadocivil) {
        this.idestadocivil = idestadocivil;
    }

    public String getNombreestadocivil() {
        return nombreestadocivil;
    }

    public void setNombreestadocivil(String nombreestadocivil) {
        this.nombreestadocivil = nombreestadocivil;
    }

    @XmlTransient
    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadocivil != null ? idestadocivil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadocivil)) {
            return false;
        }
        Estadocivil other = (Estadocivil) object;
        if ((this.idestadocivil == null && other.idestadocivil != null) || (this.idestadocivil != null && !this.idestadocivil.equals(other.idestadocivil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DL.Estadocivil[ idestadocivil=" + idestadocivil + " ]";
    }
    
}
