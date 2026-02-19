package com.example.individual.infra.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.individual.domain.common.enums.LoginType;
import com.example.individual.domain.common.enums.UserStatus;
import com.example.individual.domain.user.entity.Members;
import com.example.individual.domain.user.enums.UserRole;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private final Members member;

	public CustomUserDetails(Members member){
		this.member = member;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_"+member.getUserRole().name()));
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// UserDetailsService에서 loadUserByUserName 함수에서 들어가는 userName 에서 사용될 고유한 id값을 불러오게끔 해야함.
		return member.getEmail();
	}


	public UserStatus getStatus(){
		return member.getStatus();
	}

	public Long getDatabaseId(){
		return member.getId();
	}

	public UserRole getUserRole(){
		return member.getUserRole();
	}

	public LoginType getUserLoginType(){
		return member.getLoginType();
	}

	public String getUserNickName(){
		return member.getNickname();
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
		return member.getStatus() == UserStatus.ACTIVE;
	}
}
