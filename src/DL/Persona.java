/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DL;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kathy
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByIdpersona", query = "SELECT p FROM Persona p WHERE p.idpersona = :idpersona"),
    @NamedQuery(name = "Persona.findByNombres", query = "SELECT p FROM Persona p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Persona.findByPrimerapellido", query = "SELECT p FROM Persona p WHERE p.primerapellido = :primerapellido"),
    @NamedQuery(name = "Persona.findBySegundoapellido", query = "SELECT p FROM Persona p WHERE p.segundoapellido = :segundoapellido"),
    @NamedQuery(name = "Persona.findByFechanacimiento", query = "SELECT p FROM Persona p WHERE p.fechanacimiento = :fechanacimiento"),
    @NamedQuery(name = "Persona.findByCedula", query = "SELECT p FROM Persona p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Persona.findByTelefonocasa", query = "SELECT p FROM Persona p WHERE p.telefonocasa = :telefonocasa"),
    @NamedQuery(name = "Persona.findByTelefonocelular", query = "SELECT p FROM Persona p WHERE p.telefonocelular = :telefonocelular"),
    @NamedQuery(name = "Persona.findByTelefonooficina", query = "SELECT p FROM Persona p WHERE p.telefonooficina = :telefonooficina"),
    @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idpersona")
    private Integer idpersona;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "primerapellido")
    private String primerapellido;
    @Basic(optional = false)
    @Column(name = "segundoapellido")
    private String segundoapellido;
    @Basic(optional = false)
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "telefonocasa")
    private String telefonocasa;
    @Basic(optional = false)
    @Column(name = "telefonocelular")
    private String telefonocelular;
    @Basic(optional = false)
    @Column(name = "telefonooficina")
    private String telefonooficina;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @JoinColumn(name = "idestadocivil", referencedColumnName = "idestadocivil")
    @ManyToOne
    private Estadocivil idestadocivil;
    @JoinColumn(name = "idgenero", referencedColumnName = "idgenero")
    @ManyToOne
    private Genero idgenero;
    @JoinColumn(name = "idgrsanguineo", referencedColumnName = "idgrsanguineo")
    @ManyToOne
    private Gruposanguineo idgrsanguineo;
    @JoinColumn(name = "idocupacion", referencedColumnName = "idocupacion")
    @ManyToOne
    private Ocupacion idocupacion;
    @OneToMany(mappedBy = "idpersona")
    private Collection<Funcionario> funcionarioCollection;
    @OneToMany(mappedBy = "idpersona")
    private Collection<Turno> turnoCollection;

    public Persona() {
    }

    public Persona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public Persona(Integer idpersona, String nombres, String primerapellido, String segundoapellido, Date fechanacimiento, String cedula, String email, String telefonocasa, String telefonocelular, String telefonooficina, String direccion) {
        this.idpersona = idpersona;
        this.nombres = nombres;
        this.primerapellido = primerapellido;
        this.segundoapellido = segundoapellido;
        this.fechanacimiento = fechanacimiento;
        this.cedula = cedula;
        this.email = email;
        this.telefonocasa = telefonocasa;
        this.telefonocelular = telefonocelular;
        this.telefonooficina = telefonooficina;
        this.direccion = direccion;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonocasa() {
        return telefonocasa;
    }

    public void setTelefonocasa(String telefonocasa) {
        this.telefonocasa = telefonocasa;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonooficina() {
        return telefonooficina;
    }

    public void setTelefonooficina(String telefonooficina) {
        this.telefonooficina = telefonooficina;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Estadocivil getIdestadocivil() {
        return idestadocivil;
    }

    public void setIdestadocivil(Estadocivil idestadocivil) {
        this.idestadocivil = idestadocivil;
    }

    public Genero getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(Genero idgenero) {
        this.idgenero = idgenero;
    }

    public Gruposanguineo getIdgrsanguineo() {
        return idgrsanguineo;
    }

    public void setIdgrsanguineo(Gruposanguineo idgrsanguineo) {
        this.idgrsanguineo = idgrsanguineo;
    }

    public Ocupacion getIdocupacion() {
        return idocupacion;
    }

    public void setIdocupacion(Ocupacion idocupacion) {
        this.idocupacion = idocupacion;
    }

    @XmlTransient
    public Collection<Funcionario> getFuncionarioCollection() {
        return funcionarioCollection;
    }

    public void setFuncionarioCollection(Collection<Funcionario> funcionarioCollection) {
        this.funcionarioCollection = funcionarioCollection;
    }

    @XmlTransient
    public Collection<Turno> getTurnoCollection() {
        return turnoCollection;
    }

    public void setTurnoCollection(Collection<Turno> turnoCollection) {
        this.turnoCollection = turnoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersona != null ? idpersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idpersona == null && other.idpersona != null) || (this.idpersona != null && !this.idpersona.equals(other.idpersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DL.Persona[ idpersona=" + idpersona + " ]";
    }
    
}
