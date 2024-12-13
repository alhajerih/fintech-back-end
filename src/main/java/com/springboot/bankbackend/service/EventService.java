package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.EventRequestBO;
import com.springboot.bankbackend.bo.EventResponseBO;
import com.springboot.bankbackend.entity.EventEntity;

import java.util.List;

public interface EventService {
    List<EventResponseBO> getAllEvents();
    EventResponseBO createEvent(EventRequestBO eventRequestBO);

}
