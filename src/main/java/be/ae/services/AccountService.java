package be.ae.services;

import be.ae.services.exceptions.BusinessException;
import be.ae.services.exceptions.ErrorCode;
import be.ae.services.mapper.AccountMapper;
import be.ae.services.model.Account;
import be.ae.services.model.AccountType;
import be.ae.services.repositories.AccountRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public AccountService() {
    }

    @Transactional(readOnly = true)
    public List<be.ae.rest.model.Account> getAccounts() {
        final List<Account> accountModels = accountRepository.getAccounts();
        return accountMapper.map(accountModels);
    }

    public be.ae.rest.model.Account get(String id) {
        Account accountModel = accountRepository.getById(id);
        return accountMapper.map(accountModel);
    }

    public String create(be.ae.rest.model.Account account) {
        if (!isValidCreateAccountCommand(account)) {
            throw new BusinessException(ErrorCode.MISSING_CREATE_ACCOUNT_INFORMATION);
        }

        final Account accountModel = new Account(AccountType.fromValue(account.getType().value()), account.getOwners());
        accountRepository.save(accountModel);
        return accountModel.getId();
    }

    private boolean isValidCreateAccountCommand(@RequestBody be.ae.rest.model.Account account) {
        return account.getType() != null && !CollectionUtils.isEmpty(account.getOwners());
    }

    public void delete(String id) {
        Account account = accountRepository.getById(id);
        accountRepository.delete(account);
    }
}