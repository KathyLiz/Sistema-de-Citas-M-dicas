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
@Table(name = "ocupacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocupacion.findAll", query = "SELECT o FROM Ocupacion o"),
    @NamedQuery(name = "Ocupacion.findByIdocupacion", query = "SELECT o FROM Ocupacion o WHERE o.idocupacion = :idocupacion"),
    @NamedQuery(name = "Ocupacion.findByNombreocupacion", query = "SELECT o FROM Ocupacion o WHERE o.nombreocupacion = :nombreocupacion")})
public class Ocupacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idocupacion")
    private Integer idocupacion;
    @Basic(optional = false)
    @Column(name = "nombreocupacion")
    private String nombreocupacion;
    @OneToMany(mappedBy = "idocupacion")
    private Collection<Persona> personaCollection;

    public Ocupacion() {
    }

    public Ocupacion(Integer idocupacion) {
        this.idocupacion = idocupacion;
    }

    public Ocupacion(Integer idocupacion, String nombreocupacion) {
        this.idocupacion = idocupacion;
        this.nombreocupacion = nombreocupacion;
    }

    public Integer getIdocupacion() {
        return idocupacion;
    }

    public void setIdocupacion(Integer idocupacion) {
        this.idocupacion = idocupacion;
    }

    public String getNombreocupacion() {
        return nombreocupacion;
    }

    public void setNombreocupacion(String nombreocupacion) {
        this.nombreocupacion = nombreocupacion;
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
        hash += (idocupacion != null ? idocupacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocupacion)) {
            return false;
        }
        Ocupacion other = (Ocupacion) object;
        if ((this.idocupacion == null && other.idocupacion != null) || (this.idocupacion != null && !this.idocupacion.equals(other.idocupacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DL.Ocupacion[ idocupacion=" + idocupacion + " ]";
    }
    
}
