package com.example.progetto._02.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class EventRequest {
    @NotBlank(message = "titolo obbligatorio")
    private String title;
    @NotBlank(message = "descrizione obbligatoria")
    private String description;
    @NotBlank(message = "data obbligatorio")
    private LocalDateTime date;
    @NotBlank(message = "location obbligatoria")
    private String location;
    @NotBlank(message = "numero posti disponibili obbligatorio")
    private int availableSeats;
}
