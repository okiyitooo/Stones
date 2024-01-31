package com.SecondPersonalProject.Stones.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.jsonwebtoken.Jwts;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5648901128332558434L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long personId;
	@Column(unique=true)
	String email;
	String firstName;
	String lastName;
	String password;
	
	@OneToMany(mappedBy="person")
	List<Stone> stones;
	
	@ManyToMany
	List<Role> roles;

	public String generateToken() {
		Date expirationTime = new Date(System.currentTimeMillis() + 3600000);
		SecretKey key = Jwts.SIG.HS256.key().build();
		
		String token = Jwts.builder()
				.subject(this.getEmail())
				.claim("id", this.getPersonId())
				.claim("roles", this.getRoles())
				.expiration(expirationTime)
				.signWith(key, Jwts.SIG.HS256)
				.compact();
		
		return token;
	}

	 @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        List<Role> roles = new ArrayList<>(); 
        roles.addAll(this.getRoles());
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return list;
    }

	@Override
	public String getUsername() {
		return email;
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
