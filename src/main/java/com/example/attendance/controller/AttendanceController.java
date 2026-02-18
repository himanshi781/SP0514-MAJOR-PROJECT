package com.example.attendance.controller;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                                UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Attendance markAttendance(@RequestParam Long userId,
                                     @RequestParam double latitude,
                                     @RequestParam double longitude,
                                     @RequestParam String deviceId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setLoginTime(LocalDateTime.now());
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);
        attendance.setDeviceId(deviceId);

        return attendanceRepository.save(attendance);
    }
}
