package site.lawmate.user.service.impl;


import site.lawmate.user.domain.dto.UserDto;
import site.lawmate.user.domain.model.UserEntity;
import site.lawmate.user.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.lawmate.user.service.UserService;

import site.lawmate.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    // private final RestTemplate restTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username + ": not found");

        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

  

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUsername(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        // userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setPassword(null);

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        log.info("Before call orders microservice");
       
        try {
//            ResponseEntity<List<ResponseOrder>> _ordersList = orderServiceClient.getOrders(userId);
//            ordersList = _ordersList.getBody();
           
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }

        /* #3-1 ErrorDecoder */
//        ordersList = orderServiceClient.getOrders(userId);
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker1");
//        CircuitBreaker circuitBreaker2 = circuitBreakerFactory.create("circuitBreaker2");
//        ordersList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
//                throwable -> new ArrayList<>());
        /* #3-2 ErrorDecoder for catalog-service */
//        List<ResponseCatalog> catalogList = catalogServiceClient.getCatalogs();

       

        log.info("After called orders microservice");

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userEntity, UserDto.class);
        return userDto;
    }
    
}
