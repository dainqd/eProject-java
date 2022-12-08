package com.example.eproject.entity;

import com.example.eproject.dto.CourseDto;
import com.example.eproject.dto.EventsDto;
import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Events extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @Lob
    private String content;
    private Date startDate;
    private Date endDate;
    private float ticketPrice;
    private int ticketNumber;
    private String organiser;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location = new Location();
    private String thumbnail;
    @Enumerated(EnumType.STRING)
    private Enums.EventsStatus status;

    public Events(EventsDto eventsDto) {
        BeanUtils.copyProperties(eventsDto, this);
    }
}
