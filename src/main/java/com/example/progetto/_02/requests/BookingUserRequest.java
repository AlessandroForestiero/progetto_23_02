package com.example.progetto._02.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingUserRequest {
    @NotBlank(message = "nome obbligatorio")
    private String nome;
    @NotBlank(message = "cognome obbligatorio")
    private String cognome;
    @NotBlank(message = "username obbligatorio")
    private String username;

}
