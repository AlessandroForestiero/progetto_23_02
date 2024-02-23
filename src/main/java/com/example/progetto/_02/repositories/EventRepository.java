package com.example.progetto._02.repositories;

import com.example.progetto._02.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends JpaRepository<Event,Integer>, PagingAndSortingRepository<Event,Integer> {
}
