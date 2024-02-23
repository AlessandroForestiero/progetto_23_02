package com.example.progetto._02.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int availableSeats;
    @ManyToMany(mappedBy = "bookedEvents")
    private List<BookingUser> bookedByBookingUsers = new ArrayList<>() {
    };
}

