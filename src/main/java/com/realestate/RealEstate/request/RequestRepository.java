package com.realestate.RealEstate.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findById( Long id);

}
