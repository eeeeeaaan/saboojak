package com.example.individual.domain.auth.dto;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.individual.domain.common.enums.LoginType;
import com.example.individual.domain.common.enums.UserStatus;
import com.example.individual.domain.user.entity.Members;
import com.example.individual.domain.user.enums.UserRole;

public record SignUpRequestDTO(
	String name,
	String email,
	String nickname,
	String password
) {
	public static Members toMember(SignUpRequestDTO dto, PasswordEncoder passwordEncoder){

		return Members.builder()
			.name(dto.name())
			.email(dto.email())
			.nickname(dto.nickname())
			.password(passwordEncoder.encode(dto.password()))
			.status(UserStatus.ACTIVE)
			.levelId(1L).userRole(UserRole.USER)
			.point(0L)
			.isEmailVerified(true)
			.loginType(LoginType.COMMON)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}
}
