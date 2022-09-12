package com.realestate.RealEstate.notification;

import com.realestate.RealEstate.userrequests.UserRequestInfoResponse;
import com.realestate.RealEstate.userrequests.UserRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRequestService userRequestService;

    @PostMapping("/getUserUpdatedData")
    public NotificationsAndReceivedRequestResponse getUserUpdatedData(@RequestBody NotificationRequest notificationRequest){
        List<UserNotificationResponse> notificationResponseList = notificationService.getUserNotifications(notificationRequest.getId());
        List<UserRequestInfoResponse> allReceivedRequests = userRequestService.fetchReceivedRequests(notificationRequest.getId());
        List<UserRequestInfoResponse> receivedRequestsToday = allReceivedRequests.stream().filter(request -> Objects.equals(request.getCreatedAt().toLocalDate(), LocalDate.now())).collect(Collectors.toList());
                                return new NotificationsAndReceivedRequestResponse(notificationResponseList,allReceivedRequests,receivedRequestsToday);

    }

}
