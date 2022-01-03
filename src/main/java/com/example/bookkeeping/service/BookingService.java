package com.example.bookkeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bookkeeping.model.Booking;

import java.util.List;
import java.util.Map;

public interface BookingService extends IService<Booking> {
    int register(Booking booking);
    int sumBookingPrice(int userId, int year, int month, String kind, int day);
    List<Booking> getBookingInfo(int userId, int year, int month, int day);

    List<Map<String, Object>> getBookingGroupBy(int userId, int year, int month, int day, String kind);
}
