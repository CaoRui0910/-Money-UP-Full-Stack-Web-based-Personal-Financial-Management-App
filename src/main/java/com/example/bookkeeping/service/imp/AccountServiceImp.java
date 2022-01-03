package com.example.bookkeeping.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bookkeeping.mapper.AccountMapper;
import com.example.bookkeeping.model.Account;
import com.example.bookkeeping.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AccountServiceImp extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final AccountMapper accountMapper;

    @Override
    public Account selectByUsername(String username) {
        return accountMapper.selectOne(Wrappers.<Account>lambdaQuery().eq(Account::getMobile, username));
    }

    public List<Account> selectByRole(String role, Integer accountId) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        if (accountId != null){
            queryWrapper.eq("id", accountId);
        }
        return accountMapper.selectList(queryWrapper.eq("role", role));
    }

    public int updateUser(Account account){
        return accountMapper.updateById(account);
    }

    @Override
    public int register(Account account) {
        return accountMapper.insert(account);
    }

    public int deleteById(int accountId){
        return accountMapper.deleteById(accountId);
    }
}
