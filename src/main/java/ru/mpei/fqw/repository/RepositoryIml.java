package ru.mpei.fqw.repository;

import org.springframework.stereotype.Repository;
import ru.mpei.fqw.model.FaultCurrentModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositoryIml {
    @PersistenceContext
    private EntityManager em;

    public void save(Object object) {
        em.persist(object);
    }

    public List<FaultCurrentModel> getFaultCurrentInfo(String nameCurrent) {
        String name="IBUR"+ nameCurrent;
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();

    }
    public List<FaultCurrentModel> getFaultCurrentInfoA1(){
       // return em.createQuery("SELECT e FROM FaultCurrentModel e" , FaultCurrentModel.class).setMaxResults(300).getResultList();
        String name="IBURA1";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoB1() {
        String name="IBURB1";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoC1() {
        String name="IBURC1";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoA2() {
        String name="IBURA2";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoB2() {
        String name="IBURB2";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoC2() {
        String name="IBURC2";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoA3() {
        String name="IBURA3";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoB3() {
        String name="IBURB3";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

    public List<FaultCurrentModel> getFaultCurrentInfoC3() {
        String name="IBURC3";
        return em.createQuery("SELECT e FROM FaultCurrentModel e WHERE e.name = :value").setParameter("value", name).getResultList();
    }

}

