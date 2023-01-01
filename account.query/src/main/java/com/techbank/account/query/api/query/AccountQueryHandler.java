package com.techbank.account.query.api.query;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.domain.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountsList = new ArrayList<>();
        bankAccounts.forEach(bankAccountsList::add);
        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        List<BaseEntity> bankAccounts = new ArrayList<>(1);
        var bankAccount = accountRepository.findById(query.getId());
        bankAccount.ifPresent(bankAccounts::add);
        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        List<BaseEntity> bankAccounts = new ArrayList<>(1);
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        bankAccount.ifPresent(bankAccounts::add);
        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        if (EqualityType.GREATER_THAN.equals(query.getEqualityType())) {
            return accountRepository.findByBalanceGreaterThan(query.getBalance());
        } else {
            return accountRepository.findByBalanceLessThan(query.getBalance());
        }
    }

}
