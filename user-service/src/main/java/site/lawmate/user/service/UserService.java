package site.lawmate.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import site.lawmate.user.domain.dto.UserDto;
import site.lawmate.user.domain.model.UserEntity;

@Service
public interface UserService  extends UserDetailsService{
    UserDto createUser(UserDto userDto);

    UserDto getUserByUsername(String username);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String email);
    
}
