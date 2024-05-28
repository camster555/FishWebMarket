package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.model.Account;
import com.qifuxing.fishingwebsite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages account-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-10
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //need to return a ResponseEntity as it will hold the answer to the request made from the customer end, and respond
    //with a http response and the data as well if there is one.
    //ResponseEntity is a class from spring framework that represent http response and can include status codes,headers,
    //and a body and in this case it means the specify type of the response body which is a http response will be 'Account' object.
    //this is the long version
    /*
    public ResponseEntity<Account> createAccount(Account account){
        Account newAcc = accountService.createAcc(account);
        //so here responseBuilder will be filled with the status of 201 created which means we are telling the customer
        //end that creation of new account is successful.
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(HttpStatus.CREATED);
        //now the responseBuilder instance will have the status 201 and the new account info as the body in a new
        //instance.
        ResponseEntity<Account> responseEntity = responseBuilder.body(newAcc);
        return responseEntity;
    }
     */
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account accountCreated = accountService.createAcc(account);
        //in short version you are immediately returning the http response of status 201 and new account as body.
        return ResponseEntity.status(HttpStatus.CREATED).body(accountCreated);
    }

    /*
    public ResponseEntity<Account> getAccountById(Long id){
        //use Optional class in case id not found in table
        Optional<Account> account = accountService.getAccByID(id);
        if (account.isPresent()){
            //the get method here is from the Optional class
            Account accountValue = account.get();
            //here ok method from ResponseEntity class will send back a 200 http repose saying successfully updated
            //as well as the newly updated account info.
            ResponseEntity<Account> accountResponseEntity = ResponseEntity.ok(accountValue);
            return accountResponseEntity;
        } else {
            //not found will return http response 404
            ResponseEntity<Account> failUpdate = ResponseEntity.notFound().build();
            return  failUpdate;
        }
    }
     */

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        Optional<Account> account = accountService.getAccByID(id);
        //map here is like the 'if' statement of lambda expressions, first line is what happens if value is found
        //and second gives a response type if value is empty.
        //map here is from Optional class as a way to handle null values to prevent NullPointerException.
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //here we don't use optional class because the service layer already handles a null scenario and so this method already
    //expects the account to exist.
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account){
        //here the accountService updateAcc method already handles null exception
        Account updatedAcc = accountService.updateAcc(id,account);
        //long version
        /*
        if (updatedAcc == null){
            ResponseEntity<Account> responseEntity = ResponseEntity.notFound().build();
            return responseEntity;
        } else  {
            ResponseEntity<Account> responseEntity = ResponseEntity.ok(updatedAcc);
            return responseEntity;
        }
         */
        if (updatedAcc == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedAcc);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAcc(id);
        //here returning void as it is returning nothing but just need to send a http response of 204(No content) saying delete successful.
        /*
        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
        return responseEntity;
         */
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accountList = accountService.findList();
        /*
        ResponseEntity<List<Account>> responseEntity = ResponseEntity.ok(accountList);
        return responseEntity;
         */
        return ResponseEntity.ok(accountList);
    }
}
