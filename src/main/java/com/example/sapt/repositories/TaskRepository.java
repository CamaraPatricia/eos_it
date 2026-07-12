package com.example.sapt.repositories;

import com.example.sapt.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task , Long> {
    @Modifying
    @Query("""
            DELETE FROM Task t
            WHERE t.statusTypeId IN (SELECT 
            st.statusTypeId FROM StatusType st 
            WHERE st.statusName = :statusName)
""")
    int deleteAllByStatusName(@Param("statusName") String statusName);

    @Query("""

SELECT t FROM Task t
            WHERE t.statusTypeId IN (SELECT 
            st.statusTypeId FROM StatusType st 
            WHERE st.statusName = :statusName)""")
    List<Task> findByStatusName(@Param("statusName") String statusName);

    int deleteAllByUserId(Long userId);
}
