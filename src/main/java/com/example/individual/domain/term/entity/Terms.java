package com.example.individual.domain.term.entity;

import com.example.individual.domain.common.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Terms extends BaseEntity {
	private String name;
	private String content;
}
