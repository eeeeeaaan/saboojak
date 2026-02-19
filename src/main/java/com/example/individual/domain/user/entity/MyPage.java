package com.example.individual.domain.user.entity;

import com.example.individual.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MyPage extends BaseEntity {

	@OneToOne
	@JoinColumn(name="member_id")
	private Members member;

	private String description;
	private boolean isPhotoIncluded;

}
