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
        
        if((newAccount.getUsername().isBlank()) || newAccount.getPassword().length() < 4){
            return null;
        }

        if(accountRepository.findByUsername(newAccount.getUsername()) != null){
            return null;
        }

        return accountRepository.save(newAccount);
    }

    public Account searchUsername(Account account){
        Account searchedAccount = accountRepository.findByUsername(account.getUsername());
        return searchedAccount != null ? searchedAccount : null; 
    }

    public Account login(Account account){
        Account searchedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        
        return searchedAccount;
    }
}
