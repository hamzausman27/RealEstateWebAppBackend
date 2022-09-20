package com.realestate.RealEstate.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAll();

    //@Query(value = "SELECT description  from notification n where u.agent_id= ?1 order by request_id desc ", nativeQuery = true)
    List<Notification> getAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Notification n " +
            "SET n.isRead = TRUE WHERE n.userId = ?1")
    int updateNotificationsStatus(Long userId);
}
