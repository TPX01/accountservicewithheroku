package be.ae.services.mapper;

import be.ae.services.model.MoneyAmount;
import be.ae.services.model.Account;
import be.ae.services.model.AccountType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Component
public class AccountMapper {

    public AccountMapper() {
    }

    public List<be.ae.rest.model.Account> map(List<Account> accounts) {
        List<be.ae.rest.model.Account> resources = new ArrayList<>();

        for (Account account : accounts) {
            resources.add(map(account));
        }
        return resources;
    }

    public be.ae.rest.model.Account map(Account account) {
        be.ae.rest.model.Account resource = new be.ae.rest.model.Account();
        resource.setLabel(account.getLabel());
        resource.setType( be.ae.rest.model.Account.Type.fromValue( account.getType().name().toLowerCase() ) );
        resource.setOwners(account.getOwnerIds());
        resource.setIban(account.getIban());

        be.ae.rest.model.MoneyAmount moneyAmount = new be.ae.rest.model.MoneyAmount();
        moneyAmount.setAmount( account.getBalance().getAmount().floatValue() );
        moneyAmount.setCurrency( account.getBalance().getCurrency().toString() );
        resource.setMoneyAmount( moneyAmount );

        return resource;
    }

    public Account map(be.ae.rest.model.Account account) {
        AccountType accountType = AccountType.fromValue( account.getType().name().toLowerCase() );

        Account model = new Account();
        if (StringUtils.isEmpty(account.getLabel())) {
            model.generateLabel(accountType);
        } else {
            model.setLabel(account.getLabel());
        }
        model.setType( accountType );
        model.setOwnerIds(account.getOwners());
        model.setIban(account.getIban());

        MoneyAmount moneyAmount = new MoneyAmount();
        if ( account.getMoneyAmount() != null ) {
            moneyAmount.setAmount( BigDecimal.valueOf( account.getMoneyAmount().getAmount() ) );
            moneyAmount.setCurrency( Currency.getInstance( account.getMoneyAmount().getCurrency().toString() ) );
        } else {
            moneyAmount.setAmount(BigDecimal.ZERO);
            moneyAmount.setCurrency(Currency.getInstance("EUR"));
        }
        model.setBalance( moneyAmount );

        return model;
    }
}
