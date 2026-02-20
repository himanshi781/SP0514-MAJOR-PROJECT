package com.example.attendance.controller;

import com.example.attendance.model.Attendance;
import com.example.attendance.repository.AttendanceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AttendanceRepository attendanceRepository;

    public AnalyticsController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping("/user/{id}/fraud-percentage")
public Map<String, Object> getFraudPercentage(@PathVariable Long id) {

    Map<String, Object> response = new HashMap<>();

    long totalAttendance = attendanceRepository.countAttendanceByUserId(id);
    long fraudCount = attendanceRepository.countFraudByUserId(id);

    double fraudPercentage = 0;
    if (totalAttendance > 0) {
        fraudPercentage = Math.round(((double) fraudCount / totalAttendance * 100) * 100.0) / 100.0;

    }

    response.put("userId", id);
    response.put("totalAttendance", totalAttendance);
    response.put("fraudCount", fraudCount);
    response.put("fraudPercentage", fraudPercentage);

    return response;
}

    
}
