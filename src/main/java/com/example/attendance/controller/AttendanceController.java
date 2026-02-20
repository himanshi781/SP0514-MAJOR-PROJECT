package com.example.attendance.controller;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import com.example.attendance.service.AttendanceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceController(AttendanceService attendanceService,
                                AttendanceRepository attendanceRepository,
                                UserRepository userRepository) {
        this.attendanceService = attendanceService;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    // ⭐ POST Attendance (UPDATED)
    @PostMapping("/attendance")
    public Attendance markAttendance(@RequestBody Attendance attendance) {

        User user = userRepository.findById(attendance.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return attendanceService.markAttendance(user, attendance);
    }

    // ⭐ GET all attendance for a user (UNCHANGED)
    @GetMapping("/attendance/user/{userId}")
    public List<Attendance> getAttendanceByUser(@PathVariable Long userId) {
        return attendanceRepository.findByUserIdOrderByLoginTimeDesc(userId);
    }
}
