package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.EventRequestBO;
import com.springboot.bankbackend.bo.EventResponseBO;
import com.springboot.bankbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventResponseBO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public EventResponseBO createEvent(@RequestBody EventRequestBO request) {
        return eventService.createEvent(request);
    }
}
