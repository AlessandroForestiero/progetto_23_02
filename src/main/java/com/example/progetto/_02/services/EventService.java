package com.example.progetto._02.services;

import com.example.progetto._02.entities.Event;
import com.example.progetto._02.repositories.EventRepository;
import com.example.progetto._02.requests.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElseThrow();
    }

    public Page<Event> GetAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }


    public Event CreateEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setLocation((eventRequest.getLocation()));
        event.setDate((eventRequest.getDate()));
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setAvailableSeats(eventRequest.getAvailableSeats());
        return eventRepository.save(event);
    }
    public Event UpdateEvent(EventRequest eventRequest,int id){
        Event event = getEventById(id);
        event.setLocation((eventRequest.getLocation()));
        event.setDate((eventRequest.getDate()));
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setAvailableSeats(eventRequest.getAvailableSeats());
        return eventRepository.save(event);
    }

    public void deleteEvent(int id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }
}
