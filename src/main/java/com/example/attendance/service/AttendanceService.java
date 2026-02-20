package com.example.attendance.service;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    public Attendance markAttendance(User user, Attendance newAttendance) {

        // ⭐ FIXED LINE (sorted history)
        List<Attendance> history =
                attendanceRepository.findByUserIdOrderByLoginTimeDesc(user.getId());

        if (!history.isEmpty()) {
            // since list is DESC, first element is latest
            Attendance lastAttendance = history.get(0);
            applyFraudRules(user, lastAttendance, newAttendance);
        }

        newAttendance.setUser(user);
        return attendanceRepository.save(newAttendance);
    }

    private void applyFraudRules(User user, Attendance lastAttendance, Attendance newAttendance) {

        boolean fraudDetected = false;

        long seconds = Duration.between(
                lastAttendance.getLoginTime(),
                newAttendance.getLoginTime()
        ).getSeconds();

        // ⭐ Rule 1: Rapid login
        if (seconds < 60) {
            System.out.println("Rapid login detected");
            user.setFraudScore(user.getFraudScore() + 20);
            fraudDetected = true;
        }

        // ⭐ Rule 2: Device change
        if (!lastAttendance.getDeviceId().equals(newAttendance.getDeviceId())) {
            System.out.println("Device change detected");
            user.setFraudScore(user.getFraudScore() + 30);
            fraudDetected = true;
        }

        // ⭐ Rule 3: Location jump
        double latDiff = Math.abs(lastAttendance.getLatitude() - newAttendance.getLatitude());
        double longDiff = Math.abs(lastAttendance.getLongitude() - newAttendance.getLongitude());

        if (latDiff > 5 || longDiff > 5) {
            System.out.println("Location jump detected");
            user.setFraudScore(user.getFraudScore() + 25);
            fraudDetected = true;
        }

        // ⭐ Rule 4: Flag user if fraudScore high
        if (user.getFraudScore() > 50) {
            user.setFlagged(true);
        }

        // ⭐ Mark attendance fraud
        if (fraudDetected) {
            newAttendance.setFlagged(true);
        }

        userRepository.save(user);
    }
}
