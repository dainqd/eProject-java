package com.example.eproject.restapi;

import com.example.eproject.dto.EventsDto;
import com.example.eproject.entity.Events;
import com.example.eproject.service.EventsService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/events")
public class EventsApi {
    final EventsService eventsService;
    final MessageResourceService messageResourceService;

    @GetMapping()
    public Page<EventsDto> getList(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Enums.EventsStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (status == Enums.EventsStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("event.not.found"));
        } else if (status != null){
            return eventsService.findAllByStatusNoDelete(status, pageable).map(EventsDto::new);
        }
        return eventsService.findAllByStatusNoDelete(Enums.EventsStatus.ACTIVE, pageable).map(EventsDto::new);
    }

    @GetMapping("/{id}")
    public EventsDto getDetail(@RequestParam(value = "id", required = false, defaultValue = "1") long id,
                               @RequestParam(value = "status", required = false, defaultValue = "") Enums.EventsStatus status){
        if (status == Enums.EventsStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("event.not.found"));
        }
        Optional<Events> optionalEvents = eventsService.findByIdAndStatus(id, status);
        if (!optionalEvents.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("id.not.found"));
        }
        return new EventsDto(optionalEvents.get());
    }
}
