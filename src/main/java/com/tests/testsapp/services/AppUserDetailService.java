package com.tests.testsapp.services;

import com.tests.testsapp.entities.User;
import com.tests.testsapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return new AppUserDetails(user);
    }
    public boolean makeNewUser(String username, String password){
        if(userRepository.findByUsername(username)!= null){
            return false;
        }
        User userToReg  = new User();
        userToReg.setUsername(username);
        userToReg.setPassword(new BCryptPasswordEncoder().encode(password));
        userToReg.setRole("user");
        userRepository.save(userToReg);
        return true;

    }

}
