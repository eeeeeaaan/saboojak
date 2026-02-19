package com.example.individual.infra.s3;

import com.example.individual.domain.common.BaseEntity;

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
public class PhotoUrl extends BaseEntity {
	private String url;
	private String fileName;

	@Enumerated(value = EnumType.STRING)
	private ImageType imageType;
}
