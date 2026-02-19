package com.example.individual.domain.study.entity;

import java.util.List;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.user.entity.GroupMember;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Study extends BaseEntity {
	private String name;
	private String description;
	private Long people;
	private String leaderEmail;

	@OneToMany(orphanRemoval = true, mappedBy="study", fetch = FetchType.LAZY)
	private List<GroupMember> groupMembers;
}
