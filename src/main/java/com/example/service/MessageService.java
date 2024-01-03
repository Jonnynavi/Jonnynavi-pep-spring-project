package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){

        Account account = accountRepository.findById(message.getPosted_by()).orElse(null);
        if(message.getMessage_text().length() < 255 && !message.getMessage_text().isEmpty() && account != null){
            return messageRepository.save(message);
        }

        return null;
    }
}
