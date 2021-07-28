package volm.atm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import volm.atm.controllers.dto.TransactionDto;
import volm.atm.models.UserTransactions;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTransactionsMapper {

    @Mapping(target = "cardNumberUserFrom", source = "userFrom.cardNumber")
    @Mapping(target = "cardNumberUserTo", source = "userTo.cardNumber")
    TransactionDto toTransactionDto(UserTransactions userTransactions);

    List<TransactionDto> toListTransactionDto(List<UserTransactions> userTransactions);
}
