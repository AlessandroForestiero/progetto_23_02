package com.example.progetto._02.controllers;

import com.example.progetto._02.entities.Event;
import com.example.progetto._02.exceptions.NotFoundException;
import com.example.progetto._02.requests.EventRequest;
import com.example.progetto._02.security.JwtTools;
import com.example.progetto._02.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private JwtTools jwtTools;
    private boolean isUserAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventRequest eventRequest, BindingResult bindingResult,Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Richiesta non valida", "details", bindingResult.getAllErrors()));
        }
            if (!isUserAdmin(authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Accesso negato", "details", "Solo gli admin possono creare eventi"));
            }
        Event createdEvent = eventService.CreateEvent(eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/event/{Id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> updateEvent(@RequestBody @Valid EventRequest eventRequest,@PathVariable int id,BindingResult bindingResult,Authentication authentication) {
        if (!isUserAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Accesso negato", "details", "Solo gli admin possono modificare eventi"));
        }
        try {
            Event updatedEvent = eventService.UpdateEvent(eventRequest,id);
            return ResponseEntity.ok(updatedEvent);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento non trovato"));
        }

    }

    @DeleteMapping("/event/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable int id,Authentication authentication,BindingResult bindingResult) {
        if (!isUserAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Accesso negato", "details", "Solo gli admin possono eliminare eventi"));
        }
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento non trovato"));
        }
    }

}
