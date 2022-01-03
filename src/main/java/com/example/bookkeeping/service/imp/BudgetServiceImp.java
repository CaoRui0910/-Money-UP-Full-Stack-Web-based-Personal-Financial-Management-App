package com.example.bookkeeping.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bookkeeping.mapper.BudgetMapper;
import com.example.bookkeeping.model.Budget;
import com.example.bookkeeping.service.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class BudgetServiceImp extends ServiceImpl<BudgetMapper, Budget> implements BudgetService {
    private final BudgetMapper budgetMapper;

    public int register(Budget budget) {
        return budgetMapper.insert(budget);
    }

    @Override
    public Budget selectBudget(Integer userId, Integer year, Integer month) {
        return budgetMapper.selectOne(Wrappers.<Budget>lambdaQuery().eq(Budget::getUserId, userId).eq(Budget::getYear, year).eq(Budget::getMonth, month));
    }

    @Override
    public int updateUser(Budget budget){
        return budgetMapper.updateById(budget);
    }

    @Override
    public int sumBudgetPrice(int userId, int year, int month){
        QueryWrapper<Budget> budgetQueryWrapper = new QueryWrapper<Budget>();
        if (userId > 0)budgetQueryWrapper.eq("user_id", userId);
        if (year > 0)budgetQueryWrapper.eq("year", year);
        if (month > 0)budgetQueryWrapper.eq("month", month);
        budgetQueryWrapper.select("sum(price) as total");
        Map<String, Object> map = getMap(budgetQueryWrapper);
        if (map == null)return 0;
        return Integer.parseInt(map.get("total").toString());
    }
}
