package com.realestate.RealEstate.deal;

import com.realestate.RealEstate.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<List<Deal>> findByTag(String tag);
    Optional<List<Deal>> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<List<Deal>> findByAmountGreaterThanEqual(double amount);
    Optional<List<Deal>> findByArea(String area);

    List<Deal> findAllByAppUser(AppUser appUser);

    @Transactional
    @Modifying
    @Query("UPDATE Deal d " +
            "SET d.dealStatus = ?2 " +
            "WHERE d.id = ?1")
    int updateDealStatus(Long id,DealStatus dealStatus);

}
