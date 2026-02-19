package com.example.individual.domain.request.entity;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.common.enums.RequestStatus;
import com.example.individual.domain.post.entity.Post;
import com.example.individual.domain.user.entity.Members;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Request extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="member_id")
	private Members member;

	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;
}
