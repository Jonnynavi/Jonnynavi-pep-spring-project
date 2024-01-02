package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

import com.example.entity.Account;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account newAccount){
        
        if((newAccount.getUsername().isEmpty()) || newAccount.getPassword().length() < 4){
            return null;
        }

        if(accountRepository.findByUsername(newAccount.getUsername()) == null){
            return null;
        }

        return accountRepository.save(newAccount);
    }
}
