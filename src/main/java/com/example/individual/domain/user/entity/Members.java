package com.example.individual.domain.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.individual.domain.common.BaseEntity;
import com.example.individual.domain.common.enums.LoginType;
import com.example.individual.domain.common.enums.UserStatus;
import com.example.individual.domain.post.entity.Comment;
import com.example.individual.domain.post.entity.Post;
import com.example.individual.domain.user.enums.UserRole;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Members extends BaseEntity {
	private String name;

	private String nickname;

	private String email;

	@Nullable
	private String password;

	@Enumerated(EnumType.STRING)
	private LoginType loginType;

	private boolean isEmailVerified;

	private Long point;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Column(name="level", nullable = false)
	private Long levelId;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "member", orphanRemoval = true, fetch=FetchType.LAZY)
	private List<Post> posts;

	@OneToMany(mappedBy = "member", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> commentList;

}
