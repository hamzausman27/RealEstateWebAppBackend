package com.realestate.RealEstate.sms.passcode;

import com.realestate.RealEstate.registration.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PasscodeVerificationRepository extends JpaRepository<PasscodeVerification, Long> {

    Optional<PasscodeVerification> findByPhoneNumber(String phoneNumber);


}
