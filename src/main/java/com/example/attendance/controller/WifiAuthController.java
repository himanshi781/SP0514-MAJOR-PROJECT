

package com.example.attendance.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wifi")
public class WifiAuthController {

    private static final String AUTHORIZED_SSID = "Pratyush_Hotspot";

    @PostMapping("/markAttendance")
    public String markAttendance(
            @RequestHeader(value = "wifi-ssid", required = false) String ssid,
            HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();

        boolean ssidValid = ssid != null && ssid.equals(AUTHORIZED_SSID);
        boolean ipValid = isCampusNetwork(clientIp);

        if (ssidValid && ipValid) {
            return "✅ Attendance Marked - Authorized Network Verified (SSID: "
                    + ssid + ", IP: " + clientIp + ")";
        } else {
            return "❌ Unauthorized Network - Attendance Rejected (SSID: "
                    + ssid + ", IP: " + clientIp + ")";
        }
    }

    private boolean isCampusNetwork(String ip) {
        return ip.startsWith("192.168.43.")   // Android hotspot
                || ip.startsWith("192.168.137.") // Windows hotspot
                || ip.startsWith("172.20.10.")   // iPhone hotspot
                || ip.equals("127.0.0.1")
                || ip.equals("::1")
                || ip.equals("0:0:0:0:0:0:0:1");
    }
}