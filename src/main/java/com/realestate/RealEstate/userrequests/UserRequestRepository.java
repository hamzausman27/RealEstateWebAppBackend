package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRequestRepository extends JpaRepository<UserRequest,Long> {

  Optional<UserRequest> searchByRequest(Request request);


    @Query(value = "SELECT DISTINCT(request_id)  from user_request u where u.sender_user_id= ?1 order by request_id desc ", nativeQuery = true)
    List<Long> fetchSentRequestIds(Long senderId);

    @Query(value = "SELECT DISTINCT(request_id)  from user_request u where u.receiver_user_id= ?1 order by request_id desc ", nativeQuery = true)
    List<Long> fetchReceivedRequestIds(Long receiverId);

    @Query(value = "SELECT DISTINCT(sender_user_id)  from user_request u where u.request_id= ?1 ", nativeQuery = true)
    long fetchSenderId(Long requestId);

//  @Query(value = "SELECT id  from user_request u where u.request_id= ?1 ", nativeQuery = true)
//  long fetchUserRequestId(Long requestId);


  @Transactional
  @Modifying
  @Query("UPDATE UserRequest u " +
          "SET u.accepted = TRUE WHERE u.receiverUser= ?1 and u.request= ?2 ")
    int updateReceivedRequestAsRead(AppUser receiver,Request request);


  @Query(value = "Select id FROM user_request u where u.receiver_user_id= ?1 and u.request_id= ?2 ", nativeQuery = true)
  long fetchReceivedRequestId(Long receiverId,Long requestId);

}
