/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import BL.exceptions.NonexistentEntityException;
import DL.Especialidad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DL.Medico;
import DL.Turno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Kathy
 */
public class EspecialidadJpaController implements Serializable {

    public EspecialidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especialidad especialidad) {
        if (especialidad.getTurnoCollection() == null) {
            especialidad.setTurnoCollection(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medico idmedico = especialidad.getIdmedico();
            if (idmedico != null) {
                idmedico = em.getReference(idmedico.getClass(), idmedico.getIdmedico());
                especialidad.setIdmedico(idmedico);
            }
            Collection<Turno> attachedTurnoCollection = new ArrayList<Turno>();
            for (Turno turnoCollectionTurnoToAttach : especialidad.getTurnoCollection()) {
                turnoCollectionTurnoToAttach = em.getReference(turnoCollectionTurnoToAttach.getClass(), turnoCollectionTurnoToAttach.getIdTurno());
                attachedTurnoCollection.add(turnoCollectionTurnoToAttach);
            }
            especialidad.setTurnoCollection(attachedTurnoCollection);
            em.persist(especialidad);
            if (idmedico != null) {
                idmedico.getEspecialidadCollection().add(especialidad);
                idmedico = em.merge(idmedico);
            }
            for (Turno turnoCollectionTurno : especialidad.getTurnoCollection()) {
                Especialidad oldIdEspecialidadOfTurnoCollectionTurno = turnoCollectionTurno.getIdEspecialidad();
                turnoCollectionTurno.setIdEspecialidad(especialidad);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
                if (oldIdEspecialidadOfTurnoCollectionTurno != null) {
                    oldIdEspecialidadOfTurnoCollectionTurno.getTurnoCollection().remove(turnoCollectionTurno);
                    oldIdEspecialidadOfTurnoCollectionTurno = em.merge(oldIdEspecialidadOfTurnoCollectionTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especialidad especialidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad persistentEspecialidad = em.find(Especialidad.class, especialidad.getIdEspecialidad());
            Medico idmedicoOld = persistentEspecialidad.getIdmedico();
            Medico idmedicoNew = especialidad.getIdmedico();
            Collection<Turno> turnoCollectionOld = persistentEspecialidad.getTurnoCollection();
            Collection<Turno> turnoCollectionNew = especialidad.getTurnoCollection();
            if (idmedicoNew != null) {
                idmedicoNew = em.getReference(idmedicoNew.getClass(), idmedicoNew.getIdmedico());
                especialidad.setIdmedico(idmedicoNew);
            }
            Collection<Turno> attachedTurnoCollectionNew = new ArrayList<Turno>();
            for (Turno turnoCollectionNewTurnoToAttach : turnoCollectionNew) {
                turnoCollectionNewTurnoToAttach = em.getReference(turnoCollectionNewTurnoToAttach.getClass(), turnoCollectionNewTurnoToAttach.getIdTurno());
                attachedTurnoCollectionNew.add(turnoCollectionNewTurnoToAttach);
            }
            turnoCollectionNew = attachedTurnoCollectionNew;
            especialidad.setTurnoCollection(turnoCollectionNew);
            especialidad = em.merge(especialidad);
            if (idmedicoOld != null && !idmedicoOld.equals(idmedicoNew)) {
                idmedicoOld.getEspecialidadCollection().remove(especialidad);
                idmedicoOld = em.merge(idmedicoOld);
            }
            if (idmedicoNew != null && !idmedicoNew.equals(idmedicoOld)) {
                idmedicoNew.getEspecialidadCollection().add(especialidad);
                idmedicoNew = em.merge(idmedicoNew);
            }
            for (Turno turnoCollectionOldTurno : turnoCollectionOld) {
                if (!turnoCollectionNew.contains(turnoCollectionOldTurno)) {
                    turnoCollectionOldTurno.setIdEspecialidad(null);
                    turnoCollectionOldTurno = em.merge(turnoCollectionOldTurno);
                }
            }
            for (Turno turnoCollectionNewTurno : turnoCollectionNew) {
                if (!turnoCollectionOld.contains(turnoCollectionNewTurno)) {
                    Especialidad oldIdEspecialidadOfTurnoCollectionNewTurno = turnoCollectionNewTurno.getIdEspecialidad();
                    turnoCollectionNewTurno.setIdEspecialidad(especialidad);
                    turnoCollectionNewTurno = em.merge(turnoCollectionNewTurno);
                    if (oldIdEspecialidadOfTurnoCollectionNewTurno != null && !oldIdEspecialidadOfTurnoCollectionNewTurno.equals(especialidad)) {
                        oldIdEspecialidadOfTurnoCollectionNewTurno.getTurnoCollection().remove(turnoCollectionNewTurno);
                        oldIdEspecialidadOfTurnoCollectionNewTurno = em.merge(oldIdEspecialidadOfTurnoCollectionNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especialidad.getIdEspecialidad();
                if (findEspecialidad(id) == null) {
                    throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.");
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
            Especialidad especialidad;
            try {
                especialidad = em.getReference(Especialidad.class, id);
                especialidad.getIdEspecialidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.", enfe);
            }
            Medico idmedico = especialidad.getIdmedico();
            if (idmedico != null) {
                idmedico.getEspecialidadCollection().remove(especialidad);
                idmedico = em.merge(idmedico);
            }
            Collection<Turno> turnoCollection = especialidad.getTurnoCollection();
            for (Turno turnoCollectionTurno : turnoCollection) {
                turnoCollectionTurno.setIdEspecialidad(null);
                turnoCollectionTurno = em.merge(turnoCollectionTurno);
            }
            em.remove(especialidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especialidad> findEspecialidadEntities() {
        return findEspecialidadEntities(true, -1, -1);
    }

    public List<Especialidad> findEspecialidadEntities(int maxResults, int firstResult) {
        return findEspecialidadEntities(false, maxResults, firstResult);
    }

    private List<Especialidad> findEspecialidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especialidad.class));
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

    public Especialidad findEspecialidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especialidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especialidad> rt = cq.from(Especialidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
