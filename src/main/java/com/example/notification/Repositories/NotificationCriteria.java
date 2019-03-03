package com.example.notification.Repositories;

import com.example.notification.models.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class NotificationCriteria {

    @PersistenceContext
    private EntityManager em;


    public List<Notification> getNotificationByContent(String content , String state) {


        // First Create Criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // use criteria builder to build criteria query for notification model
        CriteriaQuery <Notification> cq = cb.createQuery(Notification.class);

        // determine root model of criteria query
        Root <Notification> notificationRoot = cq.from(Notification.class);


        // predicates condition
        Predicate contentCondition = cb.equal(notificationRoot.get("message"), content);
        Predicate stateCondition = cb.equal(notificationRoot.get("sendingState") , state );
        Predicate contentAndState=  cb.and(contentCondition , stateCondition);

        // add predicates condition to criteria query
        cq.where(contentAndState);

        // using criteria query
        TypedQuery <Notification> typedQuery = em.createQuery(cq.select(notificationRoot));

        List<Notification> notifications = typedQuery.getResultList();

        return notifications;
    }
}
