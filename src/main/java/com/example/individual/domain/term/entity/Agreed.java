package com.example.individual.domain.term.entity;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.user.entity.Members;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Agreed extends BaseEntity {
	private boolean isAgreed;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Members member;

	@ManyToOne
	@JoinColumn(name="term_id")
	private Terms term;
}
