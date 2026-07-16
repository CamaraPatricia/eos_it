package com.example.sapt.repositories;

import com.example.sapt.entities.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, String> {
    List<StatusType> deleteByStatusName(String statusName);

    @Modifying
    @Query("""
        DELETE FROM StatusType s
        WHERE s.createdBy = :createdBy
    """)
    int deleteByCreatedBy(@Param("createdBy") String createdBy);

    StatusType findByStatusName(String statusName);
}
