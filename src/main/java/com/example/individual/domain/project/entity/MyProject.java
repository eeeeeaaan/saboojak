package com.example.individual.domain.project.entity;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.user.entity.MyPage;

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
public class MyProject extends BaseEntity {
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name="page_id")
	private MyPage myPage;
}
