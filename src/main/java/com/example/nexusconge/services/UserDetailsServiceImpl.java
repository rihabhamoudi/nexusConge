package com.example.nexusconge.services;



import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  userRepo userRepository;


  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    user user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    return (UserDetails) UserDetailsImpl.build(user);
  }
}
