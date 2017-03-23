package be.ae.services.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
            name = Account.FIND_ALL,
            query = "select account from Account account"
        )
    })
public class Account {

    public static final String FIND_ALL = "Account.findAll";

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String label;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private String iban;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "BALANCE_EUR"))
    private MoneyAmount balance;

    @ElementCollection
    private List<String> ownerIds;

    public Account() {
    }

    public Account(AccountType type, List<String> ownerIds) {
        this.label = generateLabel(type);
        this.type = type;
        this.iban = generateIban();
        this.balance = new MoneyAmount(BigDecimal.ZERO, Currency.getInstance("EUR"));
        this.ownerIds = ownerIds;
    }

    private String generateLabel(AccountType type) {
        switch (type) {
            case CHECKING:
                return "mijn zichtrekening";
            case SAVINGS:
                return "mijn spaarrekening";
            default:
                return "mijn rekening";
        }
    }

    private String generateIban() {
        return "new iban generated";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MoneyAmount getBalance() {
        return balance;
    }

    public void setBalance(MoneyAmount balance) {
        this.balance = balance;
    }

    public List<String> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<String> ownerIds) {
        this.ownerIds = ownerIds;
    }
}
