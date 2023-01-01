package com.techbank.account.cmd.domain;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public double getBalance() {
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command) {
        AccountOpenedEvent accountOpenedEvent = new AccountOpenedEvent();
        accountOpenedEvent.setId(command.getId());
        accountOpenedEvent.setAccountHolder(command.getAccountHolder());
        accountOpenedEvent.setCreatedDate(new Date());
        accountOpenedEvent.setAccountType(command.getAccountType());
        accountOpenedEvent.setOpeningBalance(command.getBalance());
        raiseEvent(accountOpenedEvent);
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalArgumentException("Funds cannot be deposited into a closed account!");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("The deposit amount must be greater than 0!");
        }

        FundsDepositedEvent fundsDepositedEvent = new FundsDepositedEvent();
        fundsDepositedEvent.setId(this.id);
        fundsDepositedEvent.setAmount(amount);
        raiseEvent(fundsDepositedEvent);
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawnFunds(double amount) {
        if (!this.active) {
            throw new IllegalArgumentException("Funds cannot be withdrawn into a closed account!");
        }

        FundsWithdrawnEvent fundsWithdrawnEvent = new FundsWithdrawnEvent();
        fundsWithdrawnEvent.setId(this.id);
        fundsWithdrawnEvent.setAmount(amount);
        raiseEvent(fundsWithdrawnEvent);
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalArgumentException("The bank account has already been closed!");
        }
        AccountClosedEvent accountClosedEvent = new AccountClosedEvent();
        accountClosedEvent.setId(this.id);
        raiseEvent(accountClosedEvent);
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

}
