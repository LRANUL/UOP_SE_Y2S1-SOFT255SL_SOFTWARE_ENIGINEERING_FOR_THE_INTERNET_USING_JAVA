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
        DateTimeFormatter dtfRLocalTimeAndDate = DateTimeFormatter.ofPattern("dd/MM/yyyy  -  HH:mm:ss");
        LocalDateTime ldtRLocalTimeAndDate = LocalDateTime.now();
        return dtfRLocalTimeAndDate.format(ldtRLocalTimeAndDate);
    }
    
    public String retrieveLocalTimeHourMin(){
        DateTimeFormatter dtfRLocalTimeHourMin = DateTimeFormatter.ofPattern("HH.mm");
        LocalDateTime ldtRLocalTimeHourMin = LocalDateTime.now();
        return dtfRLocalTimeHourMin.format(ldtRLocalTimeHourMin);
    }
    
    public String retrieveLocalDate(){ 
        DateTimeFormatter dtfRLocalDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ldtRLocalDate = LocalDateTime.now();
        return dtfRLocalDate.format(ldtRLocalDate);
    }
    
    public String retrieveLocalDateSqlQuery(){ 
        DateTimeFormatter dtfRLocalDateSqlQuery = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime ldtRLocalDateSqlQuery = LocalDateTime.now();
        return dtfRLocalDateSqlQuery.format(ldtRLocalDateSqlQuery);
    }
    
    public String retrieveLocalDateTimeCReceipt(){ 
        DateTimeFormatter dtfRLocalDateTimeCR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime ldtLocalDateTimeCR = LocalDateTime.now();
        return dtfRLocalDateTimeCR.format(ldtLocalDateTimeCR);
    }
    
    public String retrieveLocalDay(){ 
        DateTimeFormatter dtfRLocalDay = DateTimeFormatter.ofPattern("dd");
        LocalDateTime ldtRLocalDay = LocalDateTime.now();
        return dtfRLocalDay.format(ldtRLocalDay);
    }
    
    public String retrieveLocalMonth(){ 
        DateTimeFormatter dtfRLocalDay = DateTimeFormatter.ofPattern("MM");
        LocalDateTime ldtRLocalDay = LocalDateTime.now();
        return dtfRLocalDay.format(ldtRLocalDay);
    }
    
    public String retrieveLocalTimeWith12HourClock(){ 
        DateFormat dfRTimeWith12HourClock = new SimpleDateFormat("hh:mm aa");
        return dfRTimeWith12HourClock.format(new Date());
    }
    
}
