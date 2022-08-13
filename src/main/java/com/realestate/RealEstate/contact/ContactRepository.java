package com.realestate.RealEstate.contact;

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
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<List<Contact>> findByName(String name);

    List<Contact> findAllByAppUser(AppUser appUser);
    Optional<Contact> findByPhoneNumber(String phoneNumber);
    Optional<List<Contact>> findByTag(String tag);
    Optional <Contact> findByAddress(String address);
    Optional<List<Contact>> findByDateOfCreation(LocalDateTime dateOfCreation);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c " +
            "SET c.name = ?2 " +
            "WHERE c.contactId = ?1")
    int updateClientName(Long contactId, String name);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c " +
            "SET c.address = ?2 " +
            "WHERE c.contactId = ?1")
    int updateClientAddress(Long contactId, String address);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c " +
            "SET c.phoneNumber = ?2 " +
            "WHERE c.contactId = ?1")
    int updateClientPhoneNumber(Long contactId, String phoneNumber);
}
