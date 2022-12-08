package com.example.eproject.controller;

import com.example.eproject.dto.EventsDto;
import com.example.eproject.service.EventsService;
import com.example.eproject.service.MessageResourceService;
import com.example.eproject.util.Enums;
import com.example.eproject.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("events")
@Slf4j
public class EventController {
    final EventsService eventsService;
    final MessageResourceService messageResourceService;

    @GetMapping("list")
    public String getList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<EventsDto> eventsDto = eventsService.findAllByStatusNoDelete(Enums.EventsStatus.ACTIVE, pageable).map(EventsDto::new);
            model.addAttribute("eventsDto", eventsDto);
//            eventsDto.getContent().forEach(eventsDto1 -> {
//                System.out.println(eventsDto1.getStartDate());
//                String date = Utils.convertToString(eventsDto1.getStartDate());
//                System.out.println(date);
//                model.addAttribute("date", date);
//            });
            return "v1/events/list";
        } catch (Exception e) {
            return "/error/404";
        }
    }

    @GetMapping("detail/{id}")
    public String getDetail(Model model, @PathVariable("id") long id) {
        try {
            Optional<EventsDto> eventsDtoOptional = eventsService.findByIdAndStatus(id, Enums.EventsStatus.ACTIVE).map(EventsDto::new);
            if (!eventsDtoOptional.isPresent()) {
                return "/error/404";
            }
            model.addAttribute("events", eventsDtoOptional.get());
            String startDateStr = Utils.convertToString(eventsDtoOptional.get().getStartDate());
            model.addAttribute("startDateStr", startDateStr);
            String endDateStr = Utils.convertToString(eventsDtoOptional.get().getEndDate());
            model.addAttribute("endDateStr", endDateStr);
            return "v1/events/detail";
        } catch (Exception e) {
            return "/error/404";
        }
    }
}
