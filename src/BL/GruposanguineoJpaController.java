/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import BL.exceptions.NonexistentEntityException;
import BL.exceptions.PreexistingEntityException;
import DL.Gruposanguineo;
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
public class GruposanguineoJpaController implements Serializable {

    public GruposanguineoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gruposanguineo gruposanguineo) throws PreexistingEntityException, Exception {
        if (gruposanguineo.getPersonaCollection() == null) {
            gruposanguineo.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : gruposanguineo.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdpersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            gruposanguineo.setPersonaCollection(attachedPersonaCollection);
            em.persist(gruposanguineo);
            for (Persona personaCollectionPersona : gruposanguineo.getPersonaCollection()) {
                Gruposanguineo oldIdgrsanguineoOfPersonaCollectionPersona = personaCollectionPersona.getIdgrsanguineo();
                personaCollectionPersona.setIdgrsanguineo(gruposanguineo);
                personaCollectionPersona = em.merge(personaCollectionPersona);
                if (oldIdgrsanguineoOfPersonaCollectionPersona != null) {
                    oldIdgrsanguineoOfPersonaCollectionPersona.getPersonaCollection().remove(personaCollectionPersona);
                    oldIdgrsanguineoOfPersonaCollectionPersona = em.merge(oldIdgrsanguineoOfPersonaCollectionPersona);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGruposanguineo(gruposanguineo.getIdgrsanguineo()) != null) {
                throw new PreexistingEntityException("Gruposanguineo " + gruposanguineo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gruposanguineo gruposanguineo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gruposanguineo persistentGruposanguineo = em.find(Gruposanguineo.class, gruposanguineo.getIdgrsanguineo());
            Collection<Persona> personaCollectionOld = persistentGruposanguineo.getPersonaCollection();
            Collection<Persona> personaCollectionNew = gruposanguineo.getPersonaCollection();
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdpersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            gruposanguineo.setPersonaCollection(personaCollectionNew);
            gruposanguineo = em.merge(gruposanguineo);
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    personaCollectionOldPersona.setIdgrsanguineo(null);
                    personaCollectionOldPersona = em.merge(personaCollectionOldPersona);
                }
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    Gruposanguineo oldIdgrsanguineoOfPersonaCollectionNewPersona = personaCollectionNewPersona.getIdgrsanguineo();
                    personaCollectionNewPersona.setIdgrsanguineo(gruposanguineo);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                    if (oldIdgrsanguineoOfPersonaCollectionNewPersona != null && !oldIdgrsanguineoOfPersonaCollectionNewPersona.equals(gruposanguineo)) {
                        oldIdgrsanguineoOfPersonaCollectionNewPersona.getPersonaCollection().remove(personaCollectionNewPersona);
                        oldIdgrsanguineoOfPersonaCollectionNewPersona = em.merge(oldIdgrsanguineoOfPersonaCollectionNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gruposanguineo.getIdgrsanguineo();
                if (findGruposanguineo(id) == null) {
                    throw new NonexistentEntityException("The gruposanguineo with id " + id + " no longer exists.");
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
            Gruposanguineo gruposanguineo;
            try {
                gruposanguineo = em.getReference(Gruposanguineo.class, id);
                gruposanguineo.getIdgrsanguineo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gruposanguineo with id " + id + " no longer exists.", enfe);
            }
            Collection<Persona> personaCollection = gruposanguineo.getPersonaCollection();
            for (Persona personaCollectionPersona : personaCollection) {
                personaCollectionPersona.setIdgrsanguineo(null);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            em.remove(gruposanguineo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gruposanguineo> findGruposanguineoEntities() {
        return findGruposanguineoEntities(true, -1, -1);
    }

    public List<Gruposanguineo> findGruposanguineoEntities(int maxResults, int firstResult) {
        return findGruposanguineoEntities(false, maxResults, firstResult);
    }

    private List<Gruposanguineo> findGruposanguineoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gruposanguineo.class));
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

    public Gruposanguineo findGruposanguineo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gruposanguineo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGruposanguineoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gruposanguineo> rt = cq.from(Gruposanguineo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
