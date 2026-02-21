package com.example.individual.infra.redis.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.individual.infra.redis.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByEmail(String email);
}
