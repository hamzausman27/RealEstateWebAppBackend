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
            "SET a.userLongitude = ?2,a.userLatitude = ?3 WHERE a.phoneNumber = ?1")
    boolean updateUserLocation(String phoneNumber, String longitude,String latitude);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?2 WHERE a.phoneNumber = ?1")
    Integer updateUserPassword(String phoneNumber,String encodedPassword);



//    @Transactional
//    @Modifying
//    @Query("SELECT AppUser a " +
//            "SET a.userLongitude = ?2,a.userLatitude = ?3 WHERE a.phoneNumber = ?1")
//    boolean updateUserLocation(String phoneNumber, String longitude,String latitude);
}
