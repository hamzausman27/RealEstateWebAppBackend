package com.realestate.RealEstate.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByPhoneNumber(String phoneNumber);
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByEmail(String email);
    //Optional<AppUser> getPassword(String phonenumber);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.verified = TRUE WHERE a.phoneNumber = ?1")
    int enableAppUser(String phoneNumber);



    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.verified = TRUE WHERE a.id = ?1")
    int verifyAppUser(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.locked = TRUE WHERE a.id = ?1")
    int blockAppUser(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.locked = FALSE WHERE a.id = ?1")
    int unBlockAppUser(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?2 WHERE a.phoneNumber = ?1")
    Integer updateUserPassword(String phoneNumber,String encodedPassword);


    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.notificationSound = FALSE WHERE a.id = ?1")
    int muteNotificationSound(Long userId);


    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.notificationSound = TRUE WHERE a.id = ?1")
    int unMuteNotificationSound(Long userId);


    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.phoneNumber = ?2,a.fullName=?3,a.area=?4 WHERE a.id = ?1")
    int editUserInfo(Long userId,String newPhone,String newName,String newAddress);

}
