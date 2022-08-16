package com.realestate.RealEstate.search;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    @Override
    List<UserLocation> findAll();

  //  findByUserID
}
