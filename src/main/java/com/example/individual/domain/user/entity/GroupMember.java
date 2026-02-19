package com.example.individual.domain.user.entity;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.study.entity.Study;

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
public class GroupMember extends BaseEntity {
	@ManyToOne
	@JoinColumn(name="study_id")
	private Study study;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Members member;

}
