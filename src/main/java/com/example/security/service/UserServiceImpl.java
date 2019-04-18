package com.example.security.service;

import com.example.security.entity.User;
import com.example.security.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public void checkSniffer(String username, String password, HttpServletResponse response) {
        logger.info(String.format("Received authentication request from sniffer %s", username));
        Optional<User> optionalUser = usersRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            //check if also the password is correct
            CharSequence charSequence = password;
            if(passwordEncoder.matches(charSequence, optionalUser.get().getPassword())){
                logger.info(String.format("Sniffer %s logged in correctly!", username));
                response.setStatus(200);
            }else{
                logger.error("Wrong password!");
                response.setStatus(400);
            }
        }else{
            logger.error(String.format("Sniffer %s doesn't exist!", username));
            response.setStatus(400);
        }
    }
}
