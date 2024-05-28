package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.model.Account;
import com.qifuxing.fishingwebsite.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing accounts.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-10
 * @version 1.0.0
 */


//business logic of application
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /*
    @PostConstruct
    public void init(){
        System.out.println("Nums of accs in db: " + accountRepository.count());
    }
     */

    public Account createAcc(Account account) {
        //returning the save entity here instead of void is useful as there is auto generated ID for the save entity.
        return accountRepository.save(account);
    }

    public Optional<Account> getAccByID(long id){
        return accountRepository.findById(id);
    }

    public Account updateAcc(Long id, Account accountInfo){
        //using Optional<Account> instead of Account since ID might be null and to avoid null error.
        Optional<Account> optionalAccount = getAccByID(id);
        if (optionalAccount.isPresent()){
            //then here we don't 'Account account = new Account()' because this new create new instance of acc but
            //if we have already found the old acc we want to update, then optionalAccount.get() will retrieve it.
            Account account = optionalAccount.get();
            account.setName(accountInfo.getName());
            return accountRepository.save(account);
        }
        return null;
    }

    public void deleteAcc(Long id){
        accountRepository.deleteById(id);
    }

    public List<Account> findList(){
        return accountRepository.findAll();
    }

}
