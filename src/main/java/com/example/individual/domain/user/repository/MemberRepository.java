package com.example.individual.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.individual.domain.user.entity.Members;

public interface MemberRepository extends JpaRepository<Members, Long> {

	Optional<Members> findByEmail(String email);
}
