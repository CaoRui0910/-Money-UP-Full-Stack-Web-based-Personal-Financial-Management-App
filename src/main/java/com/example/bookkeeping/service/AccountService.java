package com.example.bookkeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bookkeeping.model.Account;

import java.util.List;

public interface AccountService extends IService<Account> {
    Account selectByUsername(String username);

    int register(Account account);

    int updateUser(Account account);

    int deleteById(int accountId);

    List<Account> selectByRole(String role, Integer accountId);
}
