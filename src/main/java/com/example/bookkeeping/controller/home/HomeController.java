package com.example.bookkeeping.controller.home;

import com.example.bookkeeping.controller.booking.BookingInfo;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.service.BookingService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final BookingService bookingService;

    @GetMapping("/")
    public String index(Model model, Integer year, Integer month){
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        Calendar now = Calendar.getInstance();
        year = year != null ? year : now.get(Calendar.YEAR);
        month = month != null ? month : now.get(Calendar.MONTH) + 1;
        int bookingOutSum = bookingService.sumBookingPrice(account.getId(), year, month, "out", 0);
        int bookingGetSum = bookingService.sumBookingPrice(account.getId(), year, month, "get", 0);
        List<BookingInfo> bookingInfoList = new LinkedList<>();
        for (int i = 31; i > 0; i--){
            List<Booking> bookingList = bookingService.getBookingInfo(account.getId(), year, month, i);
            if (bookingList.size() != 0){
                BookingInfo bookingInfo = new BookingInfo();
                bookingInfo.setBookingList(bookingList);
                bookingInfo.setDate(String.format("%d-%d-%d", year, month, i));
                bookingInfo.setGetPrice(bookingService.sumBookingPrice(account.getId(), year, month, "get", i));
                bookingInfo.setOutPrice(bookingService.sumBookingPrice(account.getId(), year, month, "out", i));
                bookingInfoList.add(bookingInfo);
            }
        }
        System.out.println(bookingInfoList);
        model.addAttribute("bookingOutSum", bookingOutSum);
        model.addAttribute("bookingGetSum", bookingGetSum);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("bookingInfoList", bookingInfoList);
        return "home/index";
    }
}
