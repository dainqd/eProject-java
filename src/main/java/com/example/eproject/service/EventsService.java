package com.example.eproject.service;

import com.example.eproject.dto.EventsDto;
import com.example.eproject.entity.Events;
import com.example.eproject.repository.EventsRepository;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventsService {
    final EventsRepository eventsRepository;
    final MessageResourceService messageResourceService;

    public Page<Events> findAll(Pageable pageable) {
        return eventsRepository.findAll(pageable);
    }

    public Page<Events> findAllByStatus(Enums.EventsStatus status, Pageable pageable) {
        return eventsRepository.findAllByStatus(status, pageable);
    }

    public Optional<Events> findById(long id) {
        return eventsRepository.findById(id);
    }

    public Optional<Events> findByIdAndStatus(long id, Enums.EventsStatus status) {
        if (status == Enums.EventsStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("event.not.found"));
        }
        return eventsRepository.findByIdAndStatus(id, status);
    }

    public Page<Events> findAllByStatusNoDelete(Enums.EventsStatus status, Pageable pageable) {
        if (status == Enums.EventsStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("event.not.found"));
        }
        return eventsRepository.findAllByStatus(status, pageable);
    }

    public Events create(EventsDto eventsDto, long adminID) {
        Events events = new Events();

        BeanUtils.copyProperties(eventsDto, events);

        events.setStartDate(Date.valueOf(eventsDto.getStartDate()));
        events.setEndDate(Date.valueOf(eventsDto.getEndDate()));

        System.out.println(events.getStartDate() + " " + events.getEndDate());
        events.setCreatedAt(LocalDateTime.now());
        events.setCreatedBy(adminID);
        return eventsRepository.save(events);
    }

    public void delete(Events events, long adminID) {
        events.setStatus(Enums.EventsStatus.DELETED);
        events.setDeletedAt(LocalDateTime.now());
        events.setDeletedBy(adminID);
        eventsRepository.save(events);
    }

    public Events update(EventsDto eventsDto, long adminID) {
        Optional<Events> optionalEvents = eventsRepository.findById(eventsDto.getId());
        if (!optionalEvents.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("event.not.found"));
        }
        Events events = optionalEvents.get();

        BeanUtils.copyProperties(eventsDto, events);

        events.setStartDate(Date.valueOf(eventsDto.getStartDate()));
        events.setEndDate(Date.valueOf(eventsDto.getEndDate()));

        events.setUpdatedAt(LocalDateTime.now());
        events.setUpdatedBy(adminID);
        return eventsRepository.save(events);
    }
}
