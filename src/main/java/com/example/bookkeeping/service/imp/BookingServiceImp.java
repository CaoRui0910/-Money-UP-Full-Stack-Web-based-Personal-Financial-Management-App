package com.example.bookkeeping.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bookkeeping.mapper.BookingMapper;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookingServiceImp extends ServiceImpl<BookingMapper, Booking> implements BookingService {
    private final BookingMapper bookingMapper;

    public int register(Booking booking) {
        return bookingMapper.insert(booking);
    }

    public int sumBookingPrice(int userId, int year, int month, String kind, int day){
        QueryWrapper<Booking> bookingWrapper = new QueryWrapper<Booking>();
        if (userId > 0)bookingWrapper.eq("user_id", userId);
        if (year > 0)bookingWrapper.eq("year", year);
        if (month > 0)bookingWrapper.eq("month", month);
        if (day > 0)bookingWrapper.eq("day", day);
        if (kind != null)bookingWrapper.eq("kind", kind);
        bookingWrapper.select("sum(price) as total");
        Map<String, Object> map = getMap(bookingWrapper);
        if (map == null)return 0;
        return Integer.parseInt(map.get("total").toString());
    }

    public List<Booking> getBookingInfo(int userId, int year, int month, int day){
        QueryWrapper<Booking> bookingWrapper = new QueryWrapper<Booking>();
        if (userId > 0)bookingWrapper.eq("user_id", userId);
        if (year > 0)bookingWrapper.eq("year", year);
        if (month > 0)bookingWrapper.eq("month", month);
        if (day > 0)bookingWrapper.eq("day", day);
        return bookingMapper.selectList(bookingWrapper.orderByDesc("day"));
    }

    public List<Map<String, Object>> getBookingGroupBy(int userId, int year, int month, int day, String kind){
        QueryWrapper<Booking> bookingWrapper = new QueryWrapper<Booking>();
        if (userId > 0)bookingWrapper.eq("user_id", userId);
        if (year > 0)bookingWrapper.eq("year", year);
        if (month > 0)bookingWrapper.eq("month", month);
        if (day > 0)bookingWrapper.eq("day", day);
        bookingWrapper.eq("kind", kind);
        bookingWrapper.select("name, sum(price) as price");
        bookingWrapper.groupBy("name");
        List<Map<String, Object>> map = listMaps(bookingWrapper.orderByDesc("price"));
        return map;
    }
}
