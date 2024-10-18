package com.example.mercadinho.service;

import com.example.mercadinho.repository.UserRepository;
import com.example.mercadinho.repository.model.UserEntity;
import com.example.mercadinho.security.UserAuthenticated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserFacade, UserDetailsService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity request){
        return userRepository.save(request);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(UserAuthenticated::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

}
