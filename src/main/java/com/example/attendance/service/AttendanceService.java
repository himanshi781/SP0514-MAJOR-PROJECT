package com.example.attendance.service;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public Attendance markAttendance(Long userId,
                                     double latitude,
                                     double longitude,
                                     String deviceId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setLoginTime(LocalDateTime.now());
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);
        attendance.setDeviceId(deviceId);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        // Apply fraud detection after saving
        applyFraudRules(user, savedAttendance);

        return savedAttendance;
    }

    private void applyFraudRules(User user, Attendance newAttendance) {

        List<Attendance> previous =
                attendanceRepository.findByUserIdOrderByLoginTimeDesc(user.getId());

        if (previous.size() < 2) return;

        // The second record is the previous login
        Attendance lastAttendance = previous.get(1);

        // Rule 1: Rapid login within 1 minute
        long minutes = Duration.between(
                lastAttendance.getLoginTime(),
                newAttendance.getLoginTime()
        ).toMinutes();

        if (minutes < 1) {
            System.out.println("Rapid Login detected!");
            user.setFraudScore(user.getFraudScore() + 20);
        }

        // Rule 2: Device change
        if (!lastAttendance.getDeviceId().equals(newAttendance.getDeviceId())) {
            System.out.println("Device change detected!");
            user.setFraudScore(user.getFraudScore() + 30);
        }

        // Rule 3: Large location jump
        double latDiff = Math.abs(lastAttendance.getLatitude() - newAttendance.getLatitude());
        double longDiff = Math.abs(lastAttendance.getLongitude() - newAttendance.getLongitude());

        if (latDiff > 5 || longDiff > 5) {
            user.setFraudScore(user.getFraudScore() + 25);
        }

        // Rule 4: Flag if fraudScore > 50
        if (user.getFraudScore() > 50) {
            user.setFlagged(true);
        }

        userRepository.save(user);
    }
}
