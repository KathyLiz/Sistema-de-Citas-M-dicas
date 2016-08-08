/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import BL.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DL.Especialidad;
import DL.Persona;
import DL.Turno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Kathy
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad idEspecialidad = turno.getIdEspecialidad();
            if (idEspecialidad != null) {
                idEspecialidad = em.getReference(idEspecialidad.getClass(), idEspecialidad.getIdEspecialidad());
                turno.setIdEspecialidad(idEspecialidad);
            }
            Persona idpersona = turno.getIdpersona();
            if (idpersona != null) {
                idpersona = em.getReference(idpersona.getClass(), idpersona.getIdpersona());
                turno.setIdpersona(idpersona);
            }
            em.persist(turno);
            if (idEspecialidad != null) {
                idEspecialidad.getTurnoCollection().add(turno);
                idEspecialidad = em.merge(idEspecialidad);
            }
            if (idpersona != null) {
                idpersona.getTurnoCollection().add(turno);
                idpersona = em.merge(idpersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getIdTurno());
            Especialidad idEspecialidadOld = persistentTurno.getIdEspecialidad();
            Especialidad idEspecialidadNew = turno.getIdEspecialidad();
            Persona idpersonaOld = persistentTurno.getIdpersona();
            Persona idpersonaNew = turno.getIdpersona();
            if (idEspecialidadNew != null) {
                idEspecialidadNew = em.getReference(idEspecialidadNew.getClass(), idEspecialidadNew.getIdEspecialidad());
                turno.setIdEspecialidad(idEspecialidadNew);
            }
            if (idpersonaNew != null) {
                idpersonaNew = em.getReference(idpersonaNew.getClass(), idpersonaNew.getIdpersona());
                turno.setIdpersona(idpersonaNew);
            }
            turno = em.merge(turno);
            if (idEspecialidadOld != null && !idEspecialidadOld.equals(idEspecialidadNew)) {
                idEspecialidadOld.getTurnoCollection().remove(turno);
                idEspecialidadOld = em.merge(idEspecialidadOld);
            }
            if (idEspecialidadNew != null && !idEspecialidadNew.equals(idEspecialidadOld)) {
                idEspecialidadNew.getTurnoCollection().add(turno);
                idEspecialidadNew = em.merge(idEspecialidadNew);
            }
            if (idpersonaOld != null && !idpersonaOld.equals(idpersonaNew)) {
                idpersonaOld.getTurnoCollection().remove(turno);
                idpersonaOld = em.merge(idpersonaOld);
            }
            if (idpersonaNew != null && !idpersonaNew.equals(idpersonaOld)) {
                idpersonaNew.getTurnoCollection().add(turno);
                idpersonaNew = em.merge(idpersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turno.getIdTurno();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
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
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getIdTurno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            Especialidad idEspecialidad = turno.getIdEspecialidad();
            if (idEspecialidad != null) {
                idEspecialidad.getTurnoCollection().remove(turno);
                idEspecialidad = em.merge(idEspecialidad);
            }
            Persona idpersona = turno.getIdpersona();
            if (idpersona != null) {
                idpersona.getTurnoCollection().remove(turno);
                idpersona = em.merge(idpersona);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
