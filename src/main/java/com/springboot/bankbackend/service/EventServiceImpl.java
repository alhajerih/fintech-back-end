package com.springboot.bankbackend.service;

import com.springboot.bankbackend.entity.EventEntity;
import com.springboot.bankbackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public EventEntity createEvent(EventEntity event) {
        return eventRepository.save(event);
    }
}
