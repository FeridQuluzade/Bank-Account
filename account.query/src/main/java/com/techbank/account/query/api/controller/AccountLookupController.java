package com.techbank.account.query.api.controller;

import com.techbank.account.query.api.dto.AccountLookUpResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAccountByIdQuery;
import com.techbank.account.query.api.query.FindAccountWithBalanceQuery;
import com.techbank.account.query.api.query.FindAllAccountQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bank-account-lookup")
@RequiredArgsConstructor
public class AccountLookupController {

    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookUpResponse> getAll() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = new AccountLookUpResponse();
            response.setMessage(MessageFormat.format(
                    "Successfully returned {0} bank accounts", accounts.size()));
            response.setAccounts(accounts);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to complete get all accounts request";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookUpResponse> getById(@PathVariable String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = new AccountLookUpResponse();
            response.setMessage("Successfully returned bank account!");
            response.setAccounts(accounts);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to complete get all accounts request";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account-holder/{accountHolder}")
    public ResponseEntity<AccountLookUpResponse> getByAccountHolder(
            @PathVariable String accountHolder) {
        try {
            List<BankAccount> accounts =
                    queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = new AccountLookUpResponse();
            response.setMessage("Successfully returned bank account!");
            response.setAccounts(accounts);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to complete get all accounts request";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{equalityType}/{balance}")
    public ResponseEntity<AccountLookUpResponse> getByAccountWithBalance(
            @PathVariable EqualityType equalityType,
            @PathVariable Double balance
    ) {
        try {
            List<BankAccount> accounts =
                    queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
            if (accounts.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = new AccountLookUpResponse();
            response.setMessage("Successfully returned bank account!");
            response.setAccounts(accounts);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            var safeErrorMessage = "Failed to complete get all accounts request";
            logger.log(Level.SEVERE, safeErrorMessage, ex);
            return new ResponseEntity<>(new AccountLookUpResponse(safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
