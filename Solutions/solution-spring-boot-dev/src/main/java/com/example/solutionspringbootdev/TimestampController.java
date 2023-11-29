package com.example.solutionspringbootdev;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class TimestampController {

    @RequestMapping("/date")
    public String getDate() {
        return "Current date: " + LocalDate.now().toString();
    }

    @RequestMapping("/time")
    public String getTime() {
        return "Current time: " + LocalTime.now().toString();
    }
}
