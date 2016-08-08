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
import DL.Cargo;
import DL.Funcionario;
import DL.Horario;
import DL.Persona;
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
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getMedicoCollection() == null) {
            funcionario.setMedicoCollection(new ArrayList<Medico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo idcargo = funcionario.getIdcargo();
            if (idcargo != null) {
                idcargo = em.getReference(idcargo.getClass(), idcargo.getIdcargo());
                funcionario.setIdcargo(idcargo);
            }
            Horario idhorario = funcionario.getIdhorario();
            if (idhorario != null) {
                idhorario = em.getReference(idhorario.getClass(), idhorario.getIdhorario());
                funcionario.setIdhorario(idhorario);
            }
            Persona idpersona = funcionario.getIdpersona();
            if (idpersona != null) {
                idpersona = em.getReference(idpersona.getClass(), idpersona.getIdpersona());
                funcionario.setIdpersona(idpersona);
            }
            Collection<Medico> attachedMedicoCollection = new ArrayList<Medico>();
            for (Medico medicoCollectionMedicoToAttach : funcionario.getMedicoCollection()) {
                medicoCollectionMedicoToAttach = em.getReference(medicoCollectionMedicoToAttach.getClass(), medicoCollectionMedicoToAttach.getIdmedico());
                attachedMedicoCollection.add(medicoCollectionMedicoToAttach);
            }
            funcionario.setMedicoCollection(attachedMedicoCollection);
            em.persist(funcionario);
            if (idcargo != null) {
                idcargo.getFuncionarioCollection().add(funcionario);
                idcargo = em.merge(idcargo);
            }
            if (idhorario != null) {
                idhorario.getFuncionarioCollection().add(funcionario);
                idhorario = em.merge(idhorario);
            }
            if (idpersona != null) {
                idpersona.getFuncionarioCollection().add(funcionario);
                idpersona = em.merge(idpersona);
            }
            for (Medico medicoCollectionMedico : funcionario.getMedicoCollection()) {
                Funcionario oldIdfuncionarioOfMedicoCollectionMedico = medicoCollectionMedico.getIdfuncionario();
                medicoCollectionMedico.setIdfuncionario(funcionario);
                medicoCollectionMedico = em.merge(medicoCollectionMedico);
                if (oldIdfuncionarioOfMedicoCollectionMedico != null) {
                    oldIdfuncionarioOfMedicoCollectionMedico.getMedicoCollection().remove(medicoCollectionMedico);
                    oldIdfuncionarioOfMedicoCollectionMedico = em.merge(oldIdfuncionarioOfMedicoCollectionMedico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getIdfuncionario()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getIdfuncionario());
            Cargo idcargoOld = persistentFuncionario.getIdcargo();
            Cargo idcargoNew = funcionario.getIdcargo();
            Horario idhorarioOld = persistentFuncionario.getIdhorario();
            Horario idhorarioNew = funcionario.getIdhorario();
            Persona idpersonaOld = persistentFuncionario.getIdpersona();
            Persona idpersonaNew = funcionario.getIdpersona();
            Collection<Medico> medicoCollectionOld = persistentFuncionario.getMedicoCollection();
            Collection<Medico> medicoCollectionNew = funcionario.getMedicoCollection();
            if (idcargoNew != null) {
                idcargoNew = em.getReference(idcargoNew.getClass(), idcargoNew.getIdcargo());
                funcionario.setIdcargo(idcargoNew);
            }
            if (idhorarioNew != null) {
                idhorarioNew = em.getReference(idhorarioNew.getClass(), idhorarioNew.getIdhorario());
                funcionario.setIdhorario(idhorarioNew);
            }
            if (idpersonaNew != null) {
                idpersonaNew = em.getReference(idpersonaNew.getClass(), idpersonaNew.getIdpersona());
                funcionario.setIdpersona(idpersonaNew);
            }
            Collection<Medico> attachedMedicoCollectionNew = new ArrayList<Medico>();
            for (Medico medicoCollectionNewMedicoToAttach : medicoCollectionNew) {
                medicoCollectionNewMedicoToAttach = em.getReference(medicoCollectionNewMedicoToAttach.getClass(), medicoCollectionNewMedicoToAttach.getIdmedico());
                attachedMedicoCollectionNew.add(medicoCollectionNewMedicoToAttach);
            }
            medicoCollectionNew = attachedMedicoCollectionNew;
            funcionario.setMedicoCollection(medicoCollectionNew);
            funcionario = em.merge(funcionario);
            if (idcargoOld != null && !idcargoOld.equals(idcargoNew)) {
                idcargoOld.getFuncionarioCollection().remove(funcionario);
                idcargoOld = em.merge(idcargoOld);
            }
            if (idcargoNew != null && !idcargoNew.equals(idcargoOld)) {
                idcargoNew.getFuncionarioCollection().add(funcionario);
                idcargoNew = em.merge(idcargoNew);
            }
            if (idhorarioOld != null && !idhorarioOld.equals(idhorarioNew)) {
                idhorarioOld.getFuncionarioCollection().remove(funcionario);
                idhorarioOld = em.merge(idhorarioOld);
            }
            if (idhorarioNew != null && !idhorarioNew.equals(idhorarioOld)) {
                idhorarioNew.getFuncionarioCollection().add(funcionario);
                idhorarioNew = em.merge(idhorarioNew);
            }
            if (idpersonaOld != null && !idpersonaOld.equals(idpersonaNew)) {
                idpersonaOld.getFuncionarioCollection().remove(funcionario);
                idpersonaOld = em.merge(idpersonaOld);
            }
            if (idpersonaNew != null && !idpersonaNew.equals(idpersonaOld)) {
                idpersonaNew.getFuncionarioCollection().add(funcionario);
                idpersonaNew = em.merge(idpersonaNew);
            }
            for (Medico medicoCollectionOldMedico : medicoCollectionOld) {
                if (!medicoCollectionNew.contains(medicoCollectionOldMedico)) {
                    medicoCollectionOldMedico.setIdfuncionario(null);
                    medicoCollectionOldMedico = em.merge(medicoCollectionOldMedico);
                }
            }
            for (Medico medicoCollectionNewMedico : medicoCollectionNew) {
                if (!medicoCollectionOld.contains(medicoCollectionNewMedico)) {
                    Funcionario oldIdfuncionarioOfMedicoCollectionNewMedico = medicoCollectionNewMedico.getIdfuncionario();
                    medicoCollectionNewMedico.setIdfuncionario(funcionario);
                    medicoCollectionNewMedico = em.merge(medicoCollectionNewMedico);
                    if (oldIdfuncionarioOfMedicoCollectionNewMedico != null && !oldIdfuncionarioOfMedicoCollectionNewMedico.equals(funcionario)) {
                        oldIdfuncionarioOfMedicoCollectionNewMedico.getMedicoCollection().remove(medicoCollectionNewMedico);
                        oldIdfuncionarioOfMedicoCollectionNewMedico = em.merge(oldIdfuncionarioOfMedicoCollectionNewMedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionario.getIdfuncionario();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getIdfuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            Cargo idcargo = funcionario.getIdcargo();
            if (idcargo != null) {
                idcargo.getFuncionarioCollection().remove(funcionario);
                idcargo = em.merge(idcargo);
            }
            Horario idhorario = funcionario.getIdhorario();
            if (idhorario != null) {
                idhorario.getFuncionarioCollection().remove(funcionario);
                idhorario = em.merge(idhorario);
            }
            Persona idpersona = funcionario.getIdpersona();
            if (idpersona != null) {
                idpersona.getFuncionarioCollection().remove(funcionario);
                idpersona = em.merge(idpersona);
            }
            Collection<Medico> medicoCollection = funcionario.getMedicoCollection();
            for (Medico medicoCollectionMedico : medicoCollection) {
                medicoCollectionMedico.setIdfuncionario(null);
                medicoCollectionMedico = em.merge(medicoCollectionMedico);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
