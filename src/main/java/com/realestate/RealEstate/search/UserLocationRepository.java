package com.realestate.RealEstate.search;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Transactional(readOnly = true)
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    @Transactional
    @Modifying
//    @Query(value = "INSERT INTO user_location SET id=1,agent_id = ?1,coordinate=?2", nativeQuery = true)
    @Query(value = "INSERT INTO user_location(id,agent_id,coordinate) values (1,?1,ST_GeomFromText('?2'))", nativeQuery = true)
    void addLocation(String agentId, Point point);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE UserLocation a " +
//            "SET a.coordinate = ST_PointFromText('POINT(sl)') WHERE a.id = 1")
//    int enableAppUser(String phoneNumber);


    @Transactional
    @Modifying
    @Query(value = "update user_location SET coordinate=ST_PointFromText((select latitude from user_location where id =1)) where id =1 " , nativeQuery = true)
    void updateCoordinate();

}
