package com.example.bookkeeping.controller.budget;

import com.example.bookkeeping.controller.booking.BookingInfo;
import com.example.bookkeeping.helper.ResponseVo;
import com.example.bookkeeping.helper.ResultUtil;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.model.Budget;
import com.example.bookkeeping.service.BookingService;
import com.example.bookkeeping.service.BudgetService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    private final BookingService bookingService;
    private final Calendar now = Calendar.getInstance();
    private final int year = now.get(Calendar.YEAR);
    private final int month = now.get(Calendar.MONTH) + 1;

    @GetMapping("/budget")
    public String budget(Model model){
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        int bookingOutSum = bookingService.sumBookingPrice(account.getId(), year, 0, "out", 0);
        int num = budgetService.sumBudgetPrice(account.getId(), year, 0);
        List<BudgetInfo> budgetInfoList = new LinkedList<>();
        for (int i = 12; i > 0; i--){
            int outSum = bookingService.sumBookingPrice(account.getId(), year, i, "out", 0);
            int getSum = budgetService.sumBudgetPrice(account.getId(), year, i);
            if (outSum > 0 || getSum > 0){
                BudgetInfo budgetInfo = new BudgetInfo();
                budgetInfo.setMonth(i);
                budgetInfo.setGetSum(getSum);
                budgetInfo.setOutSum(outSum);
                budgetInfo.setLast(getSum - outSum);
                budgetInfoList.add(budgetInfo);
            }
        }
        model.addAttribute("outSum", bookingOutSum);
        model.addAttribute("budget", num);
        model.addAttribute("percent", division(bookingOutSum, num));
        model.addAttribute("year", year);
        model.addAttribute("last", num - bookingOutSum);
        model.addAttribute("budgetInfoList", budgetInfoList);
        return "budget/budget";
    }

    @PostMapping("/budget")
    @ResponseBody
    public ResponseVo budget(Budget budgetForm){
        int num = 0;
        Account account = (Account) SecurityUtils.getSubject().getPrincipal();
        budgetForm.setUserId(account.getId());
        budgetForm.setYear(year);
        budgetForm.setMonth(month);
        Budget budget = budgetService.selectBudget(account.getId(), year, month);
        if (budget == null){
            num = budgetService.register(budgetForm);
        }else{
            budget.setPrice(budgetForm.getPrice());
            num = budgetService.updateUser(budget);
        }
        if (num > 0) {
            return ResultUtil.success("保存成功");
        } else {
            return ResultUtil.error("保存失败");
        }
    }

    public static String division(int a ,int b){
        String result = "";
        if (b == 0){
            result = "0";
        }else{
            float num =(float)a/b*100;
            if (num > 100){
                result = "Out";
            }else{
                DecimalFormat df = new DecimalFormat("0.0");
                result = df.format(num);
            }
        }
        return result;
    }
}
