package com.springboot;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonthsOutput {
    
    @GetMapping("/TASK1/getMonthsFromDateRange")
    public ResponseEntity<?> getMonthsFromDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDateStr, 
                                                     @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > 365) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Success=False, Date range cannot exceed 365 days.");
        }
        
        List<String> months = new ArrayList<>();
        YearMonth currentMonth = YearMonth.from(startDate);
        while (!currentMonth.isAfter(YearMonth.from(endDate))) {
            months.add(currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
            currentMonth = currentMonth.plusMonths(1);
        }
        
        return ResponseEntity.ok(months);
    }
    
}