package com.example.individual.domain.level.entity;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.common.enums.LevelCode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Level extends BaseEntity {
	private String name;
	private Long minPoint;
	private String description;

	@Enumerated(value = EnumType.STRING)
	private LevelCode code;
}
