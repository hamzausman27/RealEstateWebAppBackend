package com.realestate.RealEstate.notification;

import com.realestate.RealEstate.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {
    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public List<UserNotificationResponse> getUnreadUserNotifications(Long userId){
       List<UserNotificationResponse> result = new ArrayList<>();

        List<Notification> notificationList = notificationRepository.getAllByUserId(userId);
        List<Notification> unreadNotifications = notificationList.stream().filter(val -> !val.isRead()).collect(Collectors.toList());
        for(Notification notification: unreadNotifications){
            result.add(new UserNotificationResponse(notification.getDescription(),notification.getCreatedAt(),notification.isRead()));
        }
        logger.info("Total unread notifications for user id:" + userId+" are :"+result.size());
        return result;
    }
    public void addUserNotification(Long userId,String desc){
        logger.info("Adding notification of userId:"+userId+" -> notification:"+desc);
        notificationRepository.save(new Notification(userId,desc));
    }
    public void setAllNotificationRead(Long userId){
        logger.info("Updating all user notifications as read for user:" + userId);
        notificationRepository.updateNotificationsStatus(userId);
    }

}
