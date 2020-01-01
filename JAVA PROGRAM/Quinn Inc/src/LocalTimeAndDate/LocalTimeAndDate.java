/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LocalTimeAndDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Lucas.L.H.H
 */

public class LocalTimeAndDate {
    
    public String retrieveLocalTimeAndDate(){ 
          // Retrieves current date and time from localhost, format: day/month/year - hour:minute:second (12/12/2019 - 08:24:23) 
        DateTimeFormatter dtfRLocalTimeAndDate = DateTimeFormatter.ofPattern("dd/MM/yyyy  -  HH:mm:ss");
        LocalDateTime ldtRLocalTimeAndDate = LocalDateTime.now();
        return dtfRLocalTimeAndDate.format(ldtRLocalTimeAndDate);
    }
    
    public String retrieveLocalTimeHourMin(){
          // Retrieves current time from localhost, format: hour.minute (12.53)
        DateTimeFormatter dtfRLocalTimeHourMin = DateTimeFormatter.ofPattern("HH.mm");
        LocalDateTime ldtRLocalTimeHourMin = LocalDateTime.now();
        return dtfRLocalTimeHourMin.format(ldtRLocalTimeHourMin);
    }
    
    public String retrieveLocalDate(){ 
          // Retrieves current date from localhost, format: day/month/year (23/12/2019)
        DateTimeFormatter dtfRLocalDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ldtRLocalDate = LocalDateTime.now();
        return dtfRLocalDate.format(ldtRLocalDate);
    }
    
    public String retrieveLocalDateSqlQuery(){ 
          // Retrieves current date from localhost, format: year-month-day (2019-12-24)
        DateTimeFormatter dtfRLocalDateSqlQuery = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime ldtRLocalDateSqlQuery = LocalDateTime.now();
        return dtfRLocalDateSqlQuery.format(ldtRLocalDateSqlQuery);
    }
    
    public String retrieveLocalDateTimeCReceipt(){ 
          // Retrieves current date and time from localhost, format: year-month-day hour:minute:seconds.milliseconds (2019-12-13 14:32:55:234)
        DateTimeFormatter dtfRLocalDateTimeCR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime ldtLocalDateTimeCR = LocalDateTime.now();
        return dtfRLocalDateTimeCR.format(ldtLocalDateTimeCR);
    }
    
    public String retrieveLocalDay(){ 
          // Retrieves current day from localhost, format: day (23)
        DateTimeFormatter dtfRLocalDay = DateTimeFormatter.ofPattern("dd");
        LocalDateTime ldtRLocalDay = LocalDateTime.now();
        return dtfRLocalDay.format(ldtRLocalDay);
    }
    
    public String retrieveLocalMonth(){ 
          // Retrieves current month from localhost, format: month (12)
        DateTimeFormatter dtfRLocalDay = DateTimeFormatter.ofPattern("MM");
        LocalDateTime ldtRLocalDay = LocalDateTime.now();
        return dtfRLocalDay.format(ldtRLocalDay);
    }
    
    public String retrieveLocalTimeWith12HourClock(){ 
          // Retrieves current time from localhost, format: hour:month 12-hour-clock(am or pm) (10:23 am)
        DateFormat dfRTimeWith12HourClock = new SimpleDateFormat("hh:mm aa");
        return dfRTimeWith12HourClock.format(new Date());
    }
    
}
