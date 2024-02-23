package com.example.progetto._02.controllers;

import com.example.progetto._02.security.JwtTools;
import com.example.progetto._02.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingUserController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtTools jwtTools;

    @PostMapping("/book/{id}")
    public ResponseEntity<?> bookEvent(@PathVariable int id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Accesso negato", "details", "Utente non autenticato"));
        }

        try {
            String username = jwtTools.extractUsernameFromAuthentication(authentication);
            bookingService.(id, username);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Prenotazione effettuata con successo"));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento non trovato"));
        } catch (BookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Prenotazione non valida", "details", e.getMessage()));
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> allEvents = bookingService.getAllEvents();
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/user-bookings")
    public ResponseEntity<List<Event>> getUserBookings(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Accesso negato", "details", "Utente non autenticato"));
        }

        String username = jwtTools.extractUsernameFromAuthentication(authentication);
        List<Event> userBookings = bookingService.getUserBookings(username);
        return ResponseEntity.ok(userBookings);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Accesso negato", "details", "Utente non autenticato"));
        }

        try {
            String username = jwtTools.extractUsernameFromAuthentication(authentication);
            bookingService.cancelBooking(bookingId, username);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Prenotazione non trovata"));
        } catch (BookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Impossibile annullare la prenotazione", "details", e.getMessage()));
        }
    }
}

