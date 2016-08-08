/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import BL.exceptions.NonexistentEntityException;
import BL.exceptions.PreexistingEntityException;
import DL.Ocupacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DL.Persona;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Kathy
 */
public class OcupacionJpaController implements Serializable {

    public OcupacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ocupacion ocupacion) throws PreexistingEntityException, Exception {
        if (ocupacion.getPersonaCollection() == null) {
            ocupacion.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : ocupacion.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdpersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            ocupacion.setPersonaCollection(attachedPersonaCollection);
            em.persist(ocupacion);
            for (Persona personaCollectionPersona : ocupacion.getPersonaCollection()) {
                Ocupacion oldIdocupacionOfPersonaCollectionPersona = personaCollectionPersona.getIdocupacion();
                personaCollectionPersona.setIdocupacion(ocupacion);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldIdocupacionOfPersonaCollectionPersona != null) {
                    oldIdocupacionOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldIdocupacionOfPersonaCollectionPersona = em.merge(oldIdocupacionOfPersonaCollectionPersona);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOcupacion(ocupacion.getIdocupacion()) != null) {
                throw new PreexistingEntityException("Ocupacion " + ocupacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ocupacion ocupacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocupacion persistentOcupacion = em.find(Ocupacion.class, ocupacion.getIdocupacion());
            Collection<Persona> personaCollectionOld = persistentOcupacion.getPersonaCollection();
            Collection<Persona> personaCollectionNew = ocupacion.getPersonaCollection();
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdpersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            ocupacion.setPersonaCollection(personaCollectionNew);
            ocupacion = em.merge(ocupacion);
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    personaCollectionOldPersona.setIdocupacion(null);
                    personaCollectionOldPersona = em.merge(personaCollectionOldPersona);
                }
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    Ocupacion oldIdocupacionOfPersonaCollectionNewPersona = personaCollectionNewPersona.getIdocupacion();
                    personaCollectionNewPersona.setIdocupacion(ocupacion);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldIdocupacionOfPersonaCollectionNewPersona != null && !oldIdocupacionOfPersonaCollectionNewPersona.equals(ocupacion)) {
                        oldIdocupacionOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldIdocupacionOfPersonaCollectionNewPersona = em.merge(oldIdocupacionOfPersonaCollectionNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ocupacion.getIdocupacion();
                if (findOcupacion(id) == null) {
                    throw new NonexistentEntityException("The ocupacion with id " + id + " no longer exists.");
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
            Ocupacion ocupacion;
            try {
                ocupacion = em.getReference(Ocupacion.class, id);
                ocupacion.getIdocupacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocupacion with id " + id + " no longer exists.", enfe);
            }
            Collection<Persona> personaCollection = ocupacion.getPersonaCollection();
            for (Persona personaCollectionPersona : personaCollection) {
                personaCollectionPersona.setIdocupacion(null);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            em.remove(ocupacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ocupacion> findOcupacionEntities() {
        return findOcupacionEntities(true, -1, -1);
    }

    public List<Ocupacion> findOcupacionEntities(int maxResults, int firstResult) {
        return findOcupacionEntities(false, maxResults, firstResult);
    }

    private List<Ocupacion> findOcupacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ocupacion.class));
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

    public Ocupacion findOcupacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocupacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOcupacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ocupacion> rt = cq.from(Ocupacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
