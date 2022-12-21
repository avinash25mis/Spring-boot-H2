package avi.dao;


import avi.dto.request.UpdateRequest;
import avi.model.common.GenericEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
@Transactional
public class CommonRepository {

    private static Logger logger = LoggerFactory.getLogger(CommonRepository.class);
    @Autowired
    private JpaContext jpaContext;

    @PersistenceContext
    EntityManager em;



   public <T extends GenericEntity> T saveOrUpdate(T entity){
        T managedEntity=null;
        if(entity!=null && entity.getId()==null) {
            em.persist(entity);
       }else{
            em.merge(entity);
       }
        return entity;
    }


    public <T extends GenericEntity> void delete(T entity){

       if(entity!=null && entity.getId()!=null) {
            em.remove(entity);
        }

    }


   public <T extends GenericEntity> List<T> saveOrUpdateList(List<T> entityList){
      List<T> managedEntityList=new ArrayList<>();
       for(T entity:entityList) {
          if (entity.getId() == null) {
              em.persist(entity);
          } else {
              em.merge(entity);

          }
           managedEntityList.add(entity);
      }
       return managedEntityList;
   }


    public <T extends GenericEntity> T findById(Class<T> entity, Long id){
        return em.find(entity,id);

    }



    public List getLastRecords(String className, int n) {
        String from = "FROM ";
        String orderBy = " order by id DESC";
        String queryString = from + className + orderBy;
        Query query = em.createQuery(queryString);
        query.setMaxResults(n);
        return query.getResultList();

    }

    public List getRecordWithId(String className, Long id) {
        String from = "FROM ";
        String orderBy = " WHERE  id =:id";
        String queryString = from + className + orderBy;
        Query query = em.createQuery(queryString);
        query.setParameter("id", id);
        return query.getResultList();

    }

    public List executeSelect(String queryString, Object... param) {
        logger.info(queryString);
        Query query = em.createQuery(queryString);
        for (int i = 0; i < param.length; i++) {
            query.setParameter("param" + (i + 1), param[i]);
        }
        return query.getResultList();
    }


    public int runNativeQuery(UpdateRequest request) {
        String queryString = "UPDATE " + request.getClassName() + " SET " + request.getField() + "=" + request.getFieldValue() + " WHERE " + request.getClause() + " = " + request.getClauseValue();
        Query query = em.createNativeQuery(queryString);
        int rowAffected = query.executeUpdate();
        return rowAffected;
    }












}
