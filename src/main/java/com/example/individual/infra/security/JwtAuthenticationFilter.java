package com.example.individual.infra.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.individual.domain.auth.tokenManager.TokenManager;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final TokenManager tokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String jwt = request.getHeader("Authorization");

		if(jwt==null){
			filterChain.doFilter(request, response);
		} else {
			jwt = parseJwt(jwt);
			try {
				CustomAuthenticationToken token = tokenManager.readToken(jwt);
				SecurityContextHolder.getContext().setAuthentication(token);
				filterChain.doFilter(request, response);
			}
			catch (SignatureException | UnsupportedJwtException | MalformedJwtException| ExpiredJwtException e){
				filterChain.doFilter(request, response);
			}
		}
	}

	private String parseJwt(String jwt){
		return jwt.replace("Bearer ", "");

	}
}
