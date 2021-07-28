package volm.atm.mappers;

import org.mapstruct.Mapper;
import volm.atm.dto.UserBalanceResponseDto;
import volm.atm.models.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserBalanceResponseDto toUserBalanceResponseDto(User user);
}
