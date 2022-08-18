package com.realestate.RealEstate.userseachoption;

import com.realestate.RealEstate.appuser.AppUser;
import com.realestate.RealEstate.utils.SearchOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserSearchOptionRepository  extends JpaRepository<UserSearchOption, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE UserSearchOption u " +
            "SET u.searchOption = ?2 WHERE u.appUser = ?1")
    void updateUserOption(AppUser appUser, SearchOption newSearchOption);
}
