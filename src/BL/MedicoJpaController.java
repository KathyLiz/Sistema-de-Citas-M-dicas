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
import DL.Funcionario;
import DL.Especialidad;
import DL.Medico;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Kathy
 */
public class MedicoJpaController implements Serializable {

    public MedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medico medico) throws PreexistingEntityException, Exception {
        if (medico.getEspecialidadCollection() == null) {
            medico.setEspecialidadCollection(new ArrayList<Especialidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario idfuncionario = medico.getIdfuncionario();
            if (idfuncionario != null) {
                idfuncionario = em.getReference(idfuncionario.getClass(), idfuncionario.getIdfuncionario());
                medico.setIdfuncionario(idfuncionario);
            }
            Collection<Especialidad> attachedEspecialidadCollection = new ArrayList<Especialidad>();
            for (Especialidad especialidadCollectionEspecialidadToAttach : medico.getEspecialidadCollection()) {
                especialidadCollectionEspecialidadToAttach = em.getReference(especialidadCollectionEspecialidadToAttach.getClass(), especialidadCollectionEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadCollection.add(especialidadCollectionEspecialidadToAttach);
            }
            medico.setEspecialidadCollection(attachedEspecialidadCollection);
            em.persist(medico);
            if (idfuncionario != null) {
                idfuncionario.getMedicoCollection().add(medico);
                idfuncionario = em.merge(idfuncionario);
            }
            for (Especialidad especialidadCollectionEspecialidad : medico.getEspecialidadCollection()) {
                Medico oldIdmedicoOfEspecialidadCollectionEspecialidad = especialidadCollectionEspecialidad.getIdmedico();
                especialidadCollectionEspecialidad.setIdmedico(medico);
                especialidadCollectionEspecialidad = em.merge(especialidadCollectionEspecialidad);
                if (oldIdmedicoOfEspecialidadCollectionEspecialidad != null) {
                    oldIdmedicoOfEspecialidadCollectionEspecialidad.getEspecialidadCollection().remove(especialidadCollectionEspecialidad);
                    oldIdmedicoOfEspecialidadCollectionEspecialidad = em.merge(oldIdmedicoOfEspecialidadCollectionEspecialidad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedico(medico.getIdmedico()) != null) {
                throw new PreexistingEntityException("Medico " + medico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medico medico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medico persistentMedico = em.find(Medico.class, medico.getIdmedico());
            Funcionario idfuncionarioOld = persistentMedico.getIdfuncionario();
            Funcionario idfuncionarioNew = medico.getIdfuncionario();
            Collection<Especialidad> especialidadCollectionOld = persistentMedico.getEspecialidadCollection();
            Collection<Especialidad> especialidadCollectionNew = medico.getEspecialidadCollection();
            if (idfuncionarioNew != null) {
                idfuncionarioNew = em.getReference(idfuncionarioNew.getClass(), idfuncionarioNew.getIdfuncionario());
                medico.setIdfuncionario(idfuncionarioNew);
            }
            Collection<Especialidad> attachedEspecialidadCollectionNew = new ArrayList<Especialidad>();
            for (Especialidad especialidadCollectionNewEspecialidadToAttach : especialidadCollectionNew) {
                especialidadCollectionNewEspecialidadToAttach = em.getReference(especialidadCollectionNewEspecialidadToAttach.getClass(), especialidadCollectionNewEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadCollectionNew.add(especialidadCollectionNewEspecialidadToAttach);
            }
            especialidadCollectionNew = attachedEspecialidadCollectionNew;
            medico.setEspecialidadCollection(especialidadCollectionNew);
            medico = em.merge(medico);
            if (idfuncionarioOld != null && !idfuncionarioOld.equals(idfuncionarioNew)) {
                idfuncionarioOld.getMedicoCollection().remove(medico);
                idfuncionarioOld = em.merge(idfuncionarioOld);
            }
            if (idfuncionarioNew != null && !idfuncionarioNew.equals(idfuncionarioOld)) {
                idfuncionarioNew.getMedicoCollection().add(medico);
                idfuncionarioNew = em.merge(idfuncionarioNew);
            }
            for (Especialidad especialidadCollectionOldEspecialidad : especialidadCollectionOld) {
                if (!especialidadCollectionNew.contains(especialidadCollectionOldEspecialidad)) {
                    especialidadCollectionOldEspecialidad.setIdmedico(null);
                    especialidadCollectionOldEspecialidad = em.merge(especialidadCollectionOldEspecialidad);
                }
            }
            for (Especialidad especialidadCollectionNewEspecialidad : especialidadCollectionNew) {
                if (!especialidadCollectionOld.contains(especialidadCollectionNewEspecialidad)) {
                    Medico oldIdmedicoOfEspecialidadCollectionNewEspecialidad = especialidadCollectionNewEspecialidad.getIdmedico();
                    especialidadCollectionNewEspecialidad.setIdmedico(medico);
                    especialidadCollectionNewEspecialidad = em.merge(especialidadCollectionNewEspecialidad);
                    if (oldIdmedicoOfEspecialidadCollectionNewEspecialidad != null && !oldIdmedicoOfEspecialidadCollectionNewEspecialidad.equals(medico)) {
                        oldIdmedicoOfEspecialidadCollectionNewEspecialidad.getEspecialidadCollection().remove(especialidadCollectionNewEspecialidad);
                        oldIdmedicoOfEspecialidadCollectionNewEspecialidad = em.merge(oldIdmedicoOfEspecialidadCollectionNewEspecialidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medico.getIdmedico();
                if (findMedico(id) == null) {
                    throw new NonexistentEntityException("The medico with id " + id + " no longer exists.");
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
            Medico medico;
            try {
                medico = em.getReference(Medico.class, id);
                medico.getIdmedico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medico with id " + id + " no longer exists.", enfe);
            }
            Funcionario idfuncionario = medico.getIdfuncionario();
            if (idfuncionario != null) {
                idfuncionario.getMedicoCollection().remove(medico);
                idfuncionario = em.merge(idfuncionario);
            }
            Collection<Especialidad> especialidadCollection = medico.getEspecialidadCollection();
            for (Especialidad especialidadCollectionEspecialidad : especialidadCollection) {
                especialidadCollectionEspecialidad.setIdmedico(null);
                especialidadCollectionEspecialidad = em.merge(especialidadCollectionEspecialidad);
            }
            em.remove(medico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medico> findMedicoEntities() {
        return findMedicoEntities(true, -1, -1);
    }

    public List<Medico> findMedicoEntities(int maxResults, int firstResult) {
        return findMedicoEntities(false, maxResults, firstResult);
    }

    private List<Medico> findMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medico.class));
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

    public Medico findMedico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medico> rt = cq.from(Medico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
