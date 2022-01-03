package com.example.bookkeeping.controller.booking;

import com.example.bookkeeping.helper.ResponseVo;
import com.example.bookkeeping.helper.ResultUtil;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.service.BookingService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/booking")
    public String booking(Model model){
        return "booking/booking";
    }

    @PostMapping("/booking")
    @ResponseBody
    public ResponseVo booking(Booking bookingForm){
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        bookingForm.setUserId(account.getId());
        int num = bookingService.register(bookingForm);
        if (num > 0) {
            return ResultUtil.success("保存成功");
        } else {
            return ResultUtil.error("保存失败");
        }
    }
}
