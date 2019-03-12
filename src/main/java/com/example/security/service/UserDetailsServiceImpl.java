package com.example.security.service;

import com.example.security.entity.User;
import com.example.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = usersRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            System.out.println("nome "+ user.getUsername()+ "password "+ user.getPassword()+" id "+ user.getId());
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = user.getRoles();
            for(String role : roles){
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
        else throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
    }
}
