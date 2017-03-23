package be.ae.services.mapper;

import be.ae.rest.model.MoneyAmount;
import be.ae.services.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        MoneyAmount moneyAmount = new MoneyAmount();
        moneyAmount.setAmount( account.getBalance().getAmount().floatValue() );
        moneyAmount.setCurrency( account.getBalance().getCurrency().toString() );
        resource.setMoneyAmount( moneyAmount );

        return resource;
    }
}
