package com.example.individual.domain.project.entity;

import com.example.individual.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Project extends BaseEntity {
	private String name;

	private String content;
	private boolean isPhotoIncluded;

}
