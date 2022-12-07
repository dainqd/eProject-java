package com.example.eproject.dto;

import com.example.eproject.entity.Events;
import com.example.eproject.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventsDto {
    private long id;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private float ticketPrice;
    private int ticketNumber;
    private String organiser;
    private Location location = new Location();
    private String thumbnail;

    public EventsDto(Events events) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String sTodayAsString = df.format(events.getStartDate());
        String eTodayAsString = df.format(events.getEndDate());
        BeanUtils.copyProperties(events, this);
        this.setStartDate(sTodayAsString);
        this.setEndDate(eTodayAsString);
    }
}
