package com.example.nexusconge.services;


import com.example.nexusconge.entities.activite;
import com.example.nexusconge.entities.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl  implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;
  private Long matricule ;
  private com.example.nexusconge.entities.activite activite ;
  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password, Long matricule, com.example.nexusconge.entities.activite activite,
                         Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.matricule=matricule;
    this.activite=activite;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(user user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
    		.map(role -> new SimpleGrantedAuthority(role.getNameRole().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getIdUser(),
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(),
        user.getMatricule(),
        user.getActivite(),
        authorities);
  }

  public activite getActivite() {
    return activite;
  }

  public void setActivite(activite activite) {
    this.activite = activite;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
  public  Long getMatricule(){return  matricule;}


  public String getPassword() {
    return password;
  }


  public String getUsername() {
    return username;
  }


  public boolean isAccountNonExpired() {
    return true;
  }


  public boolean isAccountNonLocked() {
    return true;
  }


  public boolean isCredentialsNonExpired() {
    return true;
  }


  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
