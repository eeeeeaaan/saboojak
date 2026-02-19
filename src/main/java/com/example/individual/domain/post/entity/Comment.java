package com.example.individual.domain.post.entity;

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
public class Comment extends BaseEntity {
	private String content;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Members member;
}
