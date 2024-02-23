package com.example.progetto._02.repositories;

import com.example.progetto._02.entities.BookingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepository extends JpaRepository<BookingUser,Integer>, PagingAndSortingRepository<BookingUser,Integer> {
}
