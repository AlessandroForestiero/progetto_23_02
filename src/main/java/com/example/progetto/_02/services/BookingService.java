package com.example.progetto._02.services;

import com.example.progetto._02.entities.BookingUser;
import com.example.progetto._02.repositories.BookingRepository;
import com.example.progetto._02.requests.BookingUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    public BookingUser getBookingById(int bookingId)  {
        return bookingRepository.findById((bookingId)).orElse(null);
    }
    public Page<BookingUser> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }
    public BookingUser saveBooking(BookingUserRequest bookingUserRequest){
        BookingUser bookingUser = new BookingUser();
        bookingUser.setNome(bookingUserRequest.getNome());
        bookingUser.setCognome(bookingUserRequest.getCognome());
        bookingUser.setUsername(bookingUserRequest.getUsername());

        return bookingRepository.save(bookingUser);
    }
    public void deleteBooking(int id){
        BookingUser bookingUser = getBookingById(id);
        bookingRepository.delete(bookingUser);
    }
}
