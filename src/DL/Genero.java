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
@Table(name = "genero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g"),
    @NamedQuery(name = "Genero.findByIdgenero", query = "SELECT g FROM Genero g WHERE g.idgenero = :idgenero"),
    @NamedQuery(name = "Genero.findByNombregenero", query = "SELECT g FROM Genero g WHERE g.nombregenero = :nombregenero")})
public class Genero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idgenero")
    private Integer idgenero;
    @Basic(optional = false)
    @Column(name = "nombregenero")
    private String nombregenero;
    @OneToMany(mappedBy = "idgenero")
    private Collection<Persona> personaCollection;

    public Genero() {
    }

    public Genero(Integer idgenero) {
        this.idgenero = idgenero;
    }

    public Genero(Integer idgenero, String nombregenero) {
        this.idgenero = idgenero;
        this.nombregenero = nombregenero;
    }

    public Integer getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(Integer idgenero) {
        this.idgenero = idgenero;
    }

    public String getNombregenero() {
        return nombregenero;
    }

    public void setNombregenero(String nombregenero) {
        this.nombregenero = nombregenero;
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
        hash += (idgenero != null ? idgenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.idgenero == null && other.idgenero != null) || (this.idgenero != null && !this.idgenero.equals(other.idgenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DL.Genero[ idgenero=" + idgenero + " ]";
    }
    
}
