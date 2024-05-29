package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.model.Account;
import com.qifuxing.fishingwebsite.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setId(1L);
        account.setName("Test Account");
    }

    @Test
    void testCreateAcc() {
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Account createdAccount = accountService.createAcc(account);
        Assertions.assertNotNull(createdAccount);
        Assertions.assertEquals("Test Account", createdAccount.getName());
    }

    @Test
    void testGetAccByID() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Optional<Account> foundAccount = accountService.getAccByID(1L);
        Assertions.assertTrue(foundAccount.isPresent());
        Assertions.assertEquals("Test Account", foundAccount.get().getName());
    }

    @Test
    void testUpdateAcc() {
        Account updatedInfo = new Account();
        updatedInfo.setName("Updated Account");
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(updatedInfo);

        Account updatedAccount = accountService.updateAcc(1L, updatedInfo);
        Assertions.assertNotNull(updatedAccount);
        Assertions.assertEquals("Updated Account", updatedAccount.getName());
    }

    @Test
    void testDeleteAcc() {
        Mockito.doNothing().when(accountRepository).deleteById(1L);
        accountService.deleteAcc(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testFindList() {
        List<Account> accounts = Arrays.asList(account, new Account());
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> accountList = accountService.findList();
        Assertions.assertNotNull(accountList);
        Assertions.assertEquals(2, accountList.size());
    }
}
