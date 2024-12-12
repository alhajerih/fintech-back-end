package com.springboot.bankbackend.service;

import com.springboot.bankbackend.entity.EventEntity;

import java.util.List;

public interface EventService {
    EventEntity createEvent(EventEntity event);
    List<EventEntity> getAllEvents();
}
