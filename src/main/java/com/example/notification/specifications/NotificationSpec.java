package com.example.notification.specifications;


import com.example.notification.models.Notification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@SuppressWarnings("ALL")
public class NotificationSpec {

  public static Specification<Notification> getNotificationByContentAndState(String content , String state)
  {
      return new Specification<Notification>() {
          @Override
          public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

              Predicate contentCondition = criteriaBuilder.equal(root.get("message") , content);
              Predicate stateCondition = criteriaBuilder.equal(root.get("sendingState") , state);

              Predicate contentAndState = criteriaBuilder.and(contentCondition ,stateCondition);

              return contentAndState;
          }
      };

  }

    public static Specification<Notification> getNotificationByContentAndStateFromDateAndTime(String content , String state , String from)
    {
        return new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate contentCondition = criteriaBuilder.equal(root.get("message") , content);
                Predicate stateCondition = criteriaBuilder.equal(root.get("sendingState") , state);
                Predicate dateCondition = criteriaBuilder.greaterThan(root.get("createDateTime") , from);

                Predicate contentAndStateAndDateTimeCondition = criteriaBuilder.and(contentCondition ,stateCondition,dateCondition);

                return contentAndStateAndDateTimeCondition;
            }
        };

    }




}
