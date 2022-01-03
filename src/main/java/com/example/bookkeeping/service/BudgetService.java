package com.example.bookkeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bookkeeping.model.Booking;
import com.example.bookkeeping.model.Budget;

public interface BudgetService extends IService<Budget> {
    int register(Budget budget);

    Budget selectBudget(Integer userId, Integer year, Integer month);

    int updateUser(Budget budget);

    int sumBudgetPrice(int userId, int year, int month);
}
