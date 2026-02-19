package com.example.individual.infra.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.individual.domain.user.enums.UserRole;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private final Long id;
	private final UserRole userRole;

	public CustomAuthenticationToken(Object principal, Object credentials){
		super(principal, credentials);
		id = null;
		userRole = null;
	}

	public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
		Long id, UserRole userRole){
		super(principal, credentials, authorities);
		this.id = id;
		this.userRole = userRole;
	}

	@Override
	public Long getDetails(){ return id;}

	public UserRole getRole(){ return userRole;}
}
