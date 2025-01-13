package ru.maslenikov.springjwttoken.mappers;

import ru.maslenikov.springjwttoken.dto.UserDTO;
import ru.maslenikov.springjwttoken.models.User;

public class UserMapper extends BaseMapper{

    public static User userDtoToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
