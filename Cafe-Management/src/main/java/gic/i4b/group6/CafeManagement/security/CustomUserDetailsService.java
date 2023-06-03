package gic.i4b.group6.CafeManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import gic.i4b.group6.CafeManagement.models.Users;
import gic.i4b.group6.CafeManagement.repositories.UserRepository;


public class CustomUserDetailsService implements UserDetailsService {
  
  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users = userRepository.findByUsername(username);
    if (users == null) {
      throw new UsernameNotFoundException("No user found for the given username");
    }
    return new CustomUserDetails(users);
  }
}
