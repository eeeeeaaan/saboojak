package com.example.individual.infra.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 생성
@NoArgsConstructor
@Getter
@RedisHash(value = "refreshToken", timeToLive = 1209600) // 2주 (단위: 초)
public class RefreshToken {
	@Id
	private String email;
	private String refreshToken;
}