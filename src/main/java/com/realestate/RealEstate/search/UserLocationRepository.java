package com.realestate.RealEstate.search;

import com.realestate.RealEstate.appuser.AppUser;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    @Override
    List<UserLocation> findAll();

    Optional<UserLocation> findByAppUser(AppUser appUser);
    @Transactional
    @Modifying
    @Query("UPDATE UserLocation u " +
            "SET u.latitude = ?2,u.longitude=?3 " +
            "WHERE u.id = ?1")
    void updateExistingUserLocation(Long id,double latitude,double longitude);
}
