package com.realestate.RealEstate.userrequests;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRequestRepository extends JpaRepository<UserRequest,Long> {



    @Query(value = "SELECT DISTINCT(request_id)  from user_request u where u.sender_user_id= ?1 order by request_id asc ", nativeQuery = true)
    List<Long> fetchSentRequestIds(Long senderId);

    @Query(value = "SELECT DISTINCT(request_id)  from user_request u where u.receiver_user_id= ?1 order by request_id asc ", nativeQuery = true)
    List<Long> fetchReceivedRequestIds(Long receiverId);



}
