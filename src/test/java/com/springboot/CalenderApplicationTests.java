package com.springboot;
import org.springframework.beans.factory.annotation.Autowired;
////import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class CalenderApplicationTests {

	 private MonthsOutput monthsOutput;

    @Before
	    public void setUp() {
	        monthsOutput = new MonthsOutput();
    }
           @Test
	        public void testGetMonthsFromDateRange() {
	        String startDateStr = "2022-01-01";
	        String endDateStr = "2022-03-31";
	        ResponseEntity<?> responseEntity = monthsOutput.getMonthsFromDateRange(startDateStr, endDateStr);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        List<String> months = (List<String>) responseEntity.getBody();
	        assertEquals(3, months.size());
            assertEquals("January 2022", months.get(0));
            assertEquals("February 2022", months.get(1));
            assertEquals("March 2022", months.get(2));
	       }

               @Test
               public void testGetMonthsFromDateRangeWithDateRangeExceeding365Days() {
                   LocalDate startDate = LocalDate.now();
                   LocalDate endDate = startDate.plusDays(366);
                   String startDateStr = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   String endDateStr = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   ResponseEntity<?> responseEntity = monthsOutput.getMonthsFromDateRange(startDateStr, endDateStr);
                   assertEquals(HttpStatus.FAILED_DEPENDENCY, responseEntity.getStatusCode());
                   assertEquals("Success=False, Date range cannot exceed 365 days.", responseEntity.getBody());

           }
	    
    }


