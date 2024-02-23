package com.example.progetto._02.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class BookingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String cognome;
    private String username;
    private String password;
    @ManyToMany
    private List<Event> bookedEvents;
}
