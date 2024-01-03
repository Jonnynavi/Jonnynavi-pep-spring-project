package com.example.controller;

import java.util.List;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@RequestMapping("")
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account){

        Account newAccount = accountService.registerAccount(account);

        if(newAccount != null){

            return ResponseEntity.status(200).body(newAccount);

        }else if(accountService.searchUsername(account) != null){
            
            return ResponseEntity.status(409).body("Username already exist");
        }
        return ResponseEntity.status(400).body("Something went wrong");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account){
        Account currentAccount = accountService.login(account);
        if(currentAccount != null){
            return ResponseEntity.status(200).body(currentAccount);
        }
        return ResponseEntity.status(401).body("failed");
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null){
            return ResponseEntity.status(400).body("Client error");
        }
        return ResponseEntity.status(200).body(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> retrieveAllMessages(){
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> retrieveMessageById(@PathVariable(value = "message_id") Integer id){
        Message message = messageService.getMessageById(id);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable(value = "message_id") Integer id){
        Integer rowsDel = messageService.deleteMessageById(id);
        return new ResponseEntity<>(rowsDel,HttpStatus.OK);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageById(@RequestBody Message newMessage, @PathVariable(value = "message_id") Integer id){
        int message = messageService.updateMessage(newMessage, id);
        System.out.println("ITS HERE -----------> " + message);
        if(message == 1){
            return new ResponseEntity<>(message, HttpStatus.OK);
        }        
        return ResponseEntity.status(400).body("Error");      
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<?> getMessagesByUserId(@PathVariable(value = "account_id") int id){
        List<Message> messages = messageService.findMessagesAllByUserId(id);
        return ResponseEntity.status(200).body(messages);
    }
}
