package com.realestate.RealEstate.notification;

import com.realestate.RealEstate.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    List<UserNotificationResponse> getUserNotifications(Long userId){
       List<UserNotificationResponse> result = new ArrayList<>();
        for(Notification notification: notificationRepository.getAllByUserId(userId)){
            result.add(new UserNotificationResponse(notification.getDescription(),notification.getCreatedAt(),notification.isRead()));
        }
        logger.info("Total notifications for user id:" + userId+" are :"+result.size());
        return result;
    }
    public void addUserNotification(Long userId,String desc){
        logger.info("Adding notification of userId:"+userId+" -> notification:"+desc);
        notificationRepository.save(new Notification(userId,desc));
    }


}
