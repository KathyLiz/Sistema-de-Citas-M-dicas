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
@Table(name = "gruposanguineo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gruposanguineo.findAll", query = "SELECT g FROM Gruposanguineo g"),
    @NamedQuery(name = "Gruposanguineo.findByIdgrsanguineo", query = "SELECT g FROM Gruposanguineo g WHERE g.idgrsanguineo = :idgrsanguineo"),
    @NamedQuery(name = "Gruposanguineo.findByNombregrsanguineo", query = "SELECT g FROM Gruposanguineo g WHERE g.nombregrsanguineo = :nombregrsanguineo")})
public class Gruposanguineo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idgrsanguineo")
    private Integer idgrsanguineo;
    @Basic(optional = false)
    @Column(name = "nombregrsanguineo")
    private String nombregrsanguineo;
    @OneToMany(mappedBy = "idgrsanguineo")
    private Collection<Persona> personaCollection;

    public Gruposanguineo() {
    }

    public Gruposanguineo(Integer idgrsanguineo) {
        this.idgrsanguineo = idgrsanguineo;
    }

    public Gruposanguineo(Integer idgrsanguineo, String nombregrsanguineo) {
        this.idgrsanguineo = idgrsanguineo;
        this.nombregrsanguineo = nombregrsanguineo;
    }

    public Integer getIdgrsanguineo() {
        return idgrsanguineo;
    }

    public void setIdgrsanguineo(Integer idgrsanguineo) {
        this.idgrsanguineo = idgrsanguineo;
    }

    public String getNombregrsanguineo() {
        return nombregrsanguineo;
    }

    public void setNombregrsanguineo(String nombregrsanguineo) {
        this.nombregrsanguineo = nombregrsanguineo;
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
        hash += (idgrsanguineo != null ? idgrsanguineo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gruposanguineo)) {
            return false;
        }
        Gruposanguineo other = (Gruposanguineo) object;
        if ((this.idgrsanguineo == null && other.idgrsanguineo != null) || (this.idgrsanguineo != null && !this.idgrsanguineo.equals(other.idgrsanguineo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DL.Gruposanguineo[ idgrsanguineo=" + idgrsanguineo + " ]";
    }
    
}
