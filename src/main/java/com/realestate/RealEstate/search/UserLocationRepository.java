package com.realestate.RealEstate.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {



}
