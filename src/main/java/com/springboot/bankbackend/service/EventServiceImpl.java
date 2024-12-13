package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.EventRequestBO;
import com.springboot.bankbackend.bo.EventResponseBO;
import com.springboot.bankbackend.entity.EventEntity;
import com.springboot.bankbackend.entity.StepsEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.EventRepository;
import com.springboot.bankbackend.repository.StepsRepository;
import com.springboot.bankbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    private UserRepository userRepository;
private StepsRepository stepsRepository;
    // Get all Events
    @Override
    public List<EventResponseBO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(event -> new EventResponseBO(event.getId(),event.getDateTime(),
                        event.getLocationName(),event.getFixedPoints(),event.getStartTime(),event.getEndTime(),event.getDate()))
                .collect(Collectors.toList());
    }


    //Create an Event
    @Override
    public EventResponseBO createEvent(EventRequestBO eventRequestBO) {
        // Create and populate the EventEntity
        EventEntity event = new EventEntity();
        event.setDateTime(eventRequestBO.getDateTime());
        event.setLocationName(eventRequestBO.getLocationName());
        event.setFixedPoints(eventRequestBO.getFixedPoints());
        event.setStartTime(eventRequestBO.getStartTime());
        event.setEndTime(eventRequestBO.getEndTime());
        event.setDate(eventRequestBO.getDate());

        // Save the event entity to the database
        EventEntity savedEvent = eventRepository.save(event);

        // Use the EventResponseBO constructor to map the data explicitly
        return new EventResponseBO(
                savedEvent.getId(),
                savedEvent.getDateTime(),
                savedEvent.getLocationName(),
                savedEvent.getFixedPoints(),
                savedEvent.getStartTime(),
                savedEvent.getEndTime(),
                savedEvent.getDate()
        );
    }



}
