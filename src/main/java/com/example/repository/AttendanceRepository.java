

package com.example.attendance.repository;

import com.example.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // existing method (keep this)
    List<Attendance> findByUserIdOrderByLoginTimeDesc(Long userId);

    // ⭐ total attendance count by user
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId")
    long countAttendanceByUserId(@Param("userId") Long userId);

    // ⭐ fraud attendance count by user
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId AND a.flagged = true")
    long countFraudByUserId(@Param("userId") Long userId);
}
