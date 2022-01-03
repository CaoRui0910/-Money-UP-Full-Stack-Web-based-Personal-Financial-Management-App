package com.example.bookkeeping.controller.booking;

import com.example.bookkeeping.model.Booking;
import lombok.Data;

import java.util.List;

@Data
public class BookingInfo {
    private String date;

    private Integer getPrice;

    private Integer outPrice;

    private List<Booking> bookingList;
}
