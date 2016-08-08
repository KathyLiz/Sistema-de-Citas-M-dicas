/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import BL.exceptions.NonexistentEntityException;
import BL.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DL.Estadocivil;
import DL.Genero;
import DL.Gruposanguineo;
import DL.Ocupacion;
import DL.Funcionario;
import DL.Persona;
import java.util.ArrayList;
import java.util.Collection;
import DL.Turno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Kathy
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getFuncionarioCollection() == null) {
            persona.setFuncionarioCollection(new ArrayList<Funcionario>());
        }
        if (persona.getTurnoCollection() == null) {
            persona.setTurnoCollection(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadocivil idestadocivil = persona.getIdestadocivil();
            if (idestadocivil != null) {
                idestadocivil = em.getReference(idestadocivil.getClass(), idestadocivil.getIdestadocivil());
                persona.setIdestadocivil(idestadocivil);
            }
            Genero idgenero = persona.getIdgenero();
            if (idgenero != null) {
                idgenero = em.getReference(idgenero.getClass(), idgenero.getIdgenero());
                persona.setIdgenero(idgenero);
            }
            Gruposanguineo idgrsanguineo = persona.getIdgrsanguineo();
            if (idgrsanguineo != null) {
                idgrsanguineo = em.getReference(idgrsanguineo.getClass(), idgrsanguineo.getIdgrsanguineo());
                persona.setIdgrsanguineo(idgrsanguineo);
            }
            Ocupacion idocupacion = persona.getIdocupacion();
            if (idocupacion != null) {
                idocupacion = em.getReference(idocupacion.getClass(), idocupacion.getIdocupacion());
                persona.setIdocupacion(idocupacion);
            }
            Collection<Funcionario> attachedFuncionarioCollection = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollectionFuncionarioToAttach : persona.getFuncionarioCollection()) {
                funcionarioCollectionFuncionarioToAttach = em.getReference(funcionarioCollectionFuncionarioToAttach.getClass(), funcionarioCollectionFuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollection.add(funcionarioCollectionFuncionarioToAttach);
            }
            persona.setFuncionarioCollection(attachedFuncionarioCollection);
            Collection<Turno> attachedTurnoCollection = new ArrayList<Turno>();
            for (Turno turnoCollectionTurnoToAttach : persona.getTurnoCollection()) {
                turnoCollectionTurnoToAttach = em.getReference(turnoCollectionTurnoToAttach.getClass(), turnoCollectionTurnoToAttach.getIdTurno());
                attachedTurnoCollection.add(turnoCollectionTurnoToAttach);
            }
            persona.setTurnoCollection(attachedTurnoCollection);
            em.persist(persona);
            if (idestadocivil != null) {
                idestadocivil.getPersonaCollection().add(persona);
                idestadocivil = em.merge(idestadocivil);
            }
            if (idgenero != null) {
                idgenero.getPersonaCollection().add(persona);
                idgenero = em.merge(idgenero);
            }
            if (idgrsanguineo != null) {
                idgrsanguineo.getPersonaCollection().add(persona);
                idgrsanguineo = em.merge(idgrsanguineo);
            }
            if (idocupacion != null) {
                idocupacion.getPersonaCollection().add(persona);
                idocupacion = em.merge(idocupacion);
            }
            for (Funcionario funcionarioCollectionFuncionario : persona.getFuncionarioCollection()) {
                Persona oldIdpersonaOfFuncionarioCollectionFuncionario = funcionarioCollectionFuncionario.getIdpersona();
                funcionarioCollectionFuncionario.setIdpersona(persona);
                funcionarioCollectionFuncionario = em.merge(funcionarioCollectionFuncionario);
                if (oldIdpersonaOfFuncionarioCollectionFuncionario != null) {
                    oldIdpersonaOfFuncionarioCollectionFuncionario.getFuncionarioCollection().remove(funcionarioCollectionFuncionario);
                    oldIdpersonaOfFuncionarioCollectionFuncionario = em.merge(oldIdpersonaOfFuncionarioCollectionFuncionario);
                }
            }
            for (Turno turnoCollectionTurno : persona.getTurnoCollection()) {
                Persona oldIdpersonaOfTurnoCollectionTurno = turnoCollectionTurno.getIdpersona();
                turnoCollectionTurno.setIdpersona(persona);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
                if (oldIdpersonaOfTurnoCollectionTurno != null) {
                    oldIdpersonaOfTurnoCollectionTurno.getTurnoCollection().remove(turnoCollectionTurno);
                    oldIdpersonaOfTurnoCollectionTurno = em.merge(oldIdpersonaOfTurnoCollectionTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getIdpersona()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdpersona());
            Estadocivil idestadocivilOld = persistentPersona.getIdestadocivil();
            Estadocivil idestadocivilNew = persona.getIdestadocivil();
            Genero idgeneroOld = persistentPersona.getIdgenero();
            Genero idgeneroNew = persona.getIdgenero();
            Gruposanguineo idgrsanguineoOld = persistentPersona.getIdgrsanguineo();
            Gruposanguineo idgrsanguineoNew = persona.getIdgrsanguineo();
            Ocupacion idocupacionOld = persistentPersona.getIdocupacion();
            Ocupacion idocupacionNew = persona.getIdocupacion();
            Collection<Funcionario> funcionarioCollectionOld = persistentPersona.getFuncionarioCollection();
            Collection<Funcionario> funcionarioCollectionNew = persona.getFuncionarioCollection();
            Collection<Turno> turnoCollectionOld = persistentPersona.getTurnoCollection();
            Collection<Turno> turnoCollectionNew = persona.getTurnoCollection();
            if (idestadocivilNew != null) {
                idestadocivilNew = em.getReference(idestadocivilNew.getClass(), idestadocivilNew.getIdestadocivil());
                persona.setIdestadocivil(idestadocivilNew);
            }
            if (idgeneroNew != null) {
                idgeneroNew = em.getReference(idgeneroNew.getClass(), idgeneroNew.getIdgenero());
                persona.setIdgenero(idgeneroNew);
            }
            if (idgrsanguineoNew != null) {
                idgrsanguineoNew = em.getReference(idgrsanguineoNew.getClass(), idgrsanguineoNew.getIdgrsanguineo());
                persona.setIdgrsanguineo(idgrsanguineoNew);
            }
            if (idocupacionNew != null) {
                idocupacionNew = em.getReference(idocupacionNew.getClass(), idocupacionNew.getIdocupacion());
                persona.setIdocupacion(idocupacionNew);
            }
            Collection<Funcionario> attachedFuncionarioCollectionNew = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollectionNewFuncionarioToAttach : funcionarioCollectionNew) {
                funcionarioCollectionNewFuncionarioToAttach = em.getReference(funcionarioCollectionNewFuncionarioToAttach.getClass(), funcionarioCollectionNewFuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollectionNew.add(funcionarioCollectionNewFuncionarioToAttach);
            }
            funcionarioCollectionNew = attachedFuncionarioCollectionNew;
            persona.setFuncionarioCollection(funcionarioCollectionNew);
            Collection<Turno> attachedTurnoCollectionNew = new ArrayList<Turno>();
            for (Turno turnoCollectionNewTurnoToAttach : turnoCollectionNew) {
                turnoCollectionNewTurnoToAttach = em.getReference(turnoCollectionNewTurnoToAttach.getClass(), turnoCollectionNewTurnoToAttach.getIdTurno());
                attachedTurnoCollectionNew.add(turnoCollectionNewTurnoToAttach);
            }
            turnoCollectionNew = attachedTurnoCollectionNew;
            persona.setTurnoCollection(turnoCollectionNew);
            persona = em.merge(persona);
            if (idestadocivilOld != null && !idestadocivilOld.equals(idestadocivilNew)) {
                idestadocivilOld.getPersonaCollection().remove(persona);
                idestadocivilOld = em.merge(idestadocivilOld);
            }
            if (idestadocivilNew != null && !idestadocivilNew.equals(idestadocivilOld)) {
                idestadocivilNew.getPersonaCollection().add(persona);
                idestadocivilNew = em.merge(idestadocivilNew);
            }
            if (idgeneroOld != null && !idgeneroOld.equals(idgeneroNew)) {
                idgeneroOld.getPersonaCollection().remove(persona);
                idgeneroOld = em.merge(idgeneroOld);
            }
            if (idgeneroNew != null && !idgeneroNew.equals(idgeneroOld)) {
                idgeneroNew.getPersonaCollection().add(persona);
                idgeneroNew = em.merge(idgeneroNew);
            }
            if (idgrsanguineoOld != null && !idgrsanguineoOld.equals(idgrsanguineoNew)) {
                idgrsanguineoOld.getPersonaCollection().remove(persona);
                idgrsanguineoOld = em.merge(idgrsanguineoOld);
            }
            if (idgrsanguineoNew != null && !idgrsanguineoNew.equals(idgrsanguineoOld)) {
                idgrsanguineoNew.getPersonaCollection().add(persona);
                idgrsanguineoNew = em.merge(idgrsanguineoNew);
            }
            if (idocupacionOld != null && !idocupacionOld.equals(idocupacionNew)) {
                idocupacionOld.getPersonaCollection().remove(persona);
                idocupacionOld = em.merge(idocupacionOld);
            }
            if (idocupacionNew != null && !idocupacionNew.equals(idocupacionOld)) {
                idocupacionNew.getPersonaCollection().add(persona);
                idocupacionNew = em.merge(idocupacionNew);
            }
            for (Funcionario funcionarioCollectionOldFuncionario : funcionarioCollectionOld) {
                if (!funcionarioCollectionNew.contains(funcionarioCollectionOldFuncionario)) {
                    funcionarioCollectionOldFuncionario.setIdpersona(null);
                    funcionarioCollectionOldFuncionario = em.merge(funcionarioCollectionOldFuncionario);
                }
            }
            for (Funcionario funcionarioCollectionNewFuncionario : funcionarioCollectionNew) {
                if (!funcionarioCollectionOld.contains(funcionarioCollectionNewFuncionario)) {
                    Persona oldIdpersonaOfFuncionarioCollectionNewFuncionario = funcionarioCollectionNewFuncionario.getIdpersona();
                    funcionarioCollectionNewFuncionario.setIdpersona(persona);
                    funcionarioCollectionNewFuncionario = em.merge(funcionarioCollectionNewFuncionario);
                    if (oldIdpersonaOfFuncionarioCollectionNewFuncionario != null && !oldIdpersonaOfFuncionarioCollectionNewFuncionario.equals(persona)) {
                        oldIdpersonaOfFuncionarioCollectionNewFuncionario.getFuncionarioCollection().remove(funcionarioCollectionNewFuncionario);
                        oldIdpersonaOfFuncionarioCollectionNewFuncionario = em.merge(oldIdpersonaOfFuncionarioCollectionNewFuncionario);
                    }
                }
            }
            for (Turno turnoCollectionOldTurno : turnoCollectionOld) {
                if (!turnoCollectionNew.contains(turnoCollectionOldTurno)) {
                    turnoCollectionOldTurno.setIdpersona(null);
                    turnoCollectionOldTurno = em.merge(turnoCollectionOldTurno);
                }
            }
            for (Turno turnoCollectionNewTurno : turnoCollectionNew) {
                if (!turnoCollectionOld.contains(turnoCollectionNewTurno)) {
                    Persona oldIdpersonaOfTurnoCollectionNewTurno = turnoCollectionNewTurno.getIdpersona();
                    turnoCollectionNewTurno.setIdpersona(persona);
                    turnoCollectionNewTurno = em.merge(turnoCollectionNewTurno);
                    if (oldIdpersonaOfTurnoCollectionNewTurno != null && !oldIdpersonaOfTurnoCollectionNewTurno.equals(persona)) {
                        oldIdpersonaOfTurnoCollectionNewTurno.getTurnoCollection().remove(turnoCollectionNewTurno);
                        oldIdpersonaOfTurnoCollectionNewTurno = em.merge(oldIdpersonaOfTurnoCollectionNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdpersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdpersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Estadocivil idestadocivil = persona.getIdestadocivil();
            if (idestadocivil != null) {
                idestadocivil.getPersonaCollection().remove(persona);
                idestadocivil = em.merge(idestadocivil);
            }
            Genero idgenero = persona.getIdgenero();
            if (idgenero != null) {
                idgenero.getPersonaCollection().remove(persona);
                idgenero = em.merge(idgenero);
            }
            Gruposanguineo idgrsanguineo = persona.getIdgrsanguineo();
            if (idgrsanguineo != null) {
                idgrsanguineo.getPersonaCollection().remove(persona);
                idgrsanguineo = em.merge(idgrsanguineo);
            }
            Ocupacion idocupacion = persona.getIdocupacion();
            if (idocupacion != null) {
                idocupacion.getPersonaCollection().remove(persona);
                idocupacion = em.merge(idocupacion);
            }
            Collection<Funcionario> funcionarioCollection = persona.getFuncionarioCollection();
            for (Funcionario funcionarioCollectionFuncionario : funcionarioCollection) {
                funcionarioCollectionFuncionario.setIdpersona(null);
                funcionarioCollectionFuncionario = em.merge(funcionarioCollectionFuncionario);
            }
            Collection<Turno> turnoCollection = persona.getTurnoCollection();
            for (Turno turnoCollectionTurno : turnoCollection) {
                turnoCollectionTurno.setIdpersona(null);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }
    
     public Persona findByCedula(String cedula) {
        EntityManager em = getEntityManager();
        try {
            //obtener la consulta por cedula de la clase Entity Persona
            Query queryPersonaByCedula = em.createNamedQuery("Persona.findByCedula");
            //enviar el parámetro de consulta 
            queryPersonaByCedula.setParameter("cedula", cedula);
            //obtener el resultado de la consulta
            Persona persona = (Persona) queryPersonaByCedula.getSingleResult();
            return persona;
        } finally {
            em.close();
        }
    }

        public Persona findByApellido(String nombres) {
        EntityManager em = getEntityManager();
        try {
            //obtener la consulta por cedula de la clase Entity Persona
            Query queryPersonaByCedula = em.createNamedQuery("Persona.findByPrimerapellido");
            //enviar el parámetro de consulta 
            queryPersonaByCedula.setParameter("primerapellido", nombres);
            //obtener el resultado de la consulta
            Persona persona = (Persona) queryPersonaByCedula.getSingleResult();
            return persona;
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
