package gic.i4b.group6.CafeManagement.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import gic.i4b.group6.CafeManagement.models.Users;


public class CustomUserDetails implements UserDetails {
  private Users users;

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  public CustomUserDetails(Users users) {
    this.users = users;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Set<Role> roles = user.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(users.getRoles().getRole()));
    return authorities;
  }

  public boolean hasRole(String roleName) {
    return this.users.hasRole(roleName);
  }

  @Override
  public String getPassword() {
    return users.getPassword();
  }

  @Override
  public String getUsername() {
    return users.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  

}
