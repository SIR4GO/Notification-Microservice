package com.example.notification.specifications;


import com.example.notification.models.Notification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static Specification<Notification> getNotificationByContentAndStateFromDateAndTime(String content , String state , String fromDate)
    {
        return new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate contentCondition = criteriaBuilder.equal(root.get("message") , content);
                Predicate stateCondition = criteriaBuilder.equal(root.get("sendingState") , state);

               // String now = "2019-02-28 16:15:49";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(fromDate, formatter);
                Predicate dateCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("createDateTime") , localDateTime);


                Predicate contentAndStateAndDateTimeCondition = criteriaBuilder.and(contentCondition ,stateCondition , dateCondition);

                return contentAndStateAndDateTimeCondition;
            }
        };

    }

    public static Specification<Notification> getNotificationByContentAndStateToDateAndTime(String content , String state , String toDate)
    {
        return new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // String now = "2019-02-28 16:15:49"; // convert to local date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(toDate, formatter);

                Predicate contentCondition = criteriaBuilder.equal(root.get("message") , content);
                Predicate stateCondition = criteriaBuilder.equal(root.get("sendingState") , state);
                Predicate dateCondition = criteriaBuilder.lessThanOrEqualTo(root.get("createDateTime") , localDateTime);
                Predicate contentAndStateAndDateTimeCondition = criteriaBuilder.and(contentCondition ,stateCondition , dateCondition);

                return contentAndStateAndDateTimeCondition;
            }
        };

    }

    public static Specification<Notification> getNotificationByContentAndStateFromAndToDateAndTime(String content , String state ,String fromDate, String toDate)
    {
        return new Specification<Notification>() {
            @Override
            public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // String now = "2019-02-28 16:15:49"; // convert to local date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fromLocalDateTime = LocalDateTime.parse(fromDate, formatter);
                LocalDateTime toLocalDateTime = LocalDateTime.parse(toDate, formatter);

                Predicate contentCondition = criteriaBuilder.equal(root.get("message") , content);
                Predicate stateCondition = criteriaBuilder.equal(root.get("sendingState") , state);
                Predicate fromDateCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("createDateTime") , fromLocalDateTime);
                Predicate toDateCondition = criteriaBuilder.lessThanOrEqualTo(root.get("createDateTime") , toLocalDateTime);

                Predicate contentAndStateAndDateTimeCondition = criteriaBuilder.and(contentCondition ,stateCondition , fromDateCondition , toDateCondition);

                return contentAndStateAndDateTimeCondition;
            }
        };

    }



}
