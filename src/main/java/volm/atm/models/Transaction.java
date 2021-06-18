package volm.atm.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "transaction")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "from_user_card_number")
    private Long fromUserCardNumber;

    @Column(name = "to_user_card_number")
    private Long toUserCardNumber;

    private Date when;

    private BigDecimal amount;
}
