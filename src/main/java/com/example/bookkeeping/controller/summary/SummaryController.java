package com.example.bookkeeping.controller.summary;

import com.example.bookkeeping.controller.booking.BookingInfo;
import com.example.bookkeeping.controller.budget.BudgetInfo;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.service.BookingService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class SummaryController {
    private final BookingService bookingService;

    @GetMapping("/summary")
    public String index(Model model, Integer kind, Integer year, Integer month, Integer day){
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        Calendar now = Calendar.getInstance();
        year = year != null ? year : now.get(Calendar.YEAR);
        month = month != null ? month : now.get(Calendar.MONTH) + 1;
        day = day != null ? day : now.get(Calendar.DATE);
        kind = kind != null ? kind : 1;
        List<Map<String, Object>> bookingInfo;
        List<SummaryInfo> summaryInfoList = new LinkedList<>();
        int price = 0;
        int bookingAll = 0;
        if (kind == 1){
            bookingAll = bookingService.sumBookingPrice(account.getId(), year, month, "out", day);
            bookingInfo = bookingService.getBookingGroupBy(account.getId(), year, month, day, "out");
        }else if(kind == 2){
            bookingAll = bookingService.sumBookingPrice(account.getId(), year, month, "out", 0);
            bookingInfo = bookingService.getBookingGroupBy(account.getId(), year, month, 0, "out");
        }else{
            bookingAll = bookingService.sumBookingPrice(account.getId(), year, 0, "out", 0);
            bookingInfo = bookingService.getBookingGroupBy(account.getId(), year, 0, 0, "out");
        }
        for (Map<String, Object> bookDetail: bookingInfo){
            SummaryInfo summaryInfo = new SummaryInfo();
            summaryInfo.setName((String) bookDetail.get("name"));
            price = Integer.parseInt(bookDetail.get("price").toString());
            summaryInfo.setPrice(price);
            summaryInfo.setPercent(bookingAll == 0 ? String.valueOf('0') : division(price, bookingAll));
            summaryInfoList.add(summaryInfo);
        }
        model.addAttribute("summaryInfoList", summaryInfoList);
        model.addAttribute("bookingAll", bookingAll);
        model.addAttribute("kind", kind);
        return "booking/summary";
    }

    @GetMapping("/bill")
    public String bill(Model model){
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        List<BudgetInfo> summaryInfoList = new LinkedList<>();
        int bookingOut = bookingService.sumBookingPrice(account.getId(), year, 0, "out", 0);
        int bookingGet = bookingService.sumBookingPrice(account.getId(), year, 0, "get", 0);
        for (int i = 12; i > 0; i--){
            int outSum = bookingService.sumBookingPrice(account.getId(), year, i, "out", 0);
            int getSum = bookingService.sumBookingPrice(account.getId(), year, i, "get", 0);
            if (outSum > 0 || getSum > 0){
                BudgetInfo budgetInfo = new BudgetInfo();
                budgetInfo.setMonth(i);
                budgetInfo.setGetSum(getSum);
                budgetInfo.setOutSum(outSum);
                budgetInfo.setLast(getSum - outSum);
                summaryInfoList.add(budgetInfo);
            }
        }
        model.addAttribute("summaryInfoList", summaryInfoList);
        model.addAttribute("bookingOut", bookingOut);
        model.addAttribute("bookingGet", bookingGet);
        return "booking/bill";
    }


    public static String division(int a ,int b){
        String result = "";
        float num =(float)a/b*100;

        DecimalFormat df = new DecimalFormat("0.0");

        result = df.format(num);

        return result;

    }
}
