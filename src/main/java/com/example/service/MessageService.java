package com.example.service;
import java.util.List;

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

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        return messageRepository.findById(id).orElse(null);
    }

    public Integer deleteMessageById(int id){
        Message message = messageRepository.findById(id).orElse(null);
        if(message != null){
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
    }

    public int updateMessage(Message message, int id){
        String messageTxt = message.getMessage_text();
        boolean itExist = messageRepository.findById(id).orElse(null) != null;
        if(messageTxt.isEmpty() || messageTxt.length() > 255 || !itExist){
            return 0;
        }
        int col = messageRepository.updateText(messageTxt, id);
        System.out.println("ITS HERE -----------> " + col);
        return 1;
    }

    public List<Message> findMessagesAllByUserId(int id){
        List<Message> messages = messageRepository.findMessagesByPostedBy(id);
        return messages;
    }

}
