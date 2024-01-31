package com.SecondPersonalProject.Stones.filters;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SecondPersonalProject.Stones.exceptions.PersonNotFoundException;
import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.repositories.PersonRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	PersonRepository personRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
        		SecretKey key = Jwts.SIG.HS256.key().build();
                Claims claims = Jwts.parser()
        				.decryptWith(key) 
        				.build() 
        				.parseSignedClaims(jwtToken) 
        				.getPayload(); 
                Long userId = Long.parseLong(claims.getSubject());
                Person person = personRepository.findById(userId)
                        .orElseThrow(() -> new PersonNotFoundException("Person not found with ID: " + userId));

                // Create Authentication object with Person details
                List<GrantedAuthority> authorities = person.getAuthorities();
                Authentication authentication = new UsernamePasswordAuthenticationToken(person, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (PersonNotFoundException p) {
				p.printStackTrace();
			} 
        }

        filterChain.doFilter(request, response);
	}

}
