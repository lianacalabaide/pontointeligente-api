package com.liana.pontointeligente.api.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liana.pontointeligente.api.response.Response;
import com.liana.pontointeligente.api.security.dto.JwtAuthenticationDto;
import com.liana.pontointeligente.api.security.dto.TokenDto;
import com.liana.pontointeligente.api.utils.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Gera e retorna um novo token Jwt
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto dto,
			BindingResult result) throws AuthenticationException {

		log.info("Gerando token jwt");
		Response<TokenDto> response = new Response<TokenDto>();
		if (result.hasErrors()) {
			log.info("Erros");
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		log.info("Gerando token para email {}", dto.getEmail());
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDto(token));
		return ResponseEntity.ok(response);

	}

	/**
	 * Gera um novo token com uma nova data de expiração
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
		log.info("Gerando refresh token JWT");
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.of(request.getHeader(TOKEN_HEADER));
		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX))
			token = Optional.of(token.get().substring(7));

		if (!token.isPresent())
			response.getErrors().add("Token não informado");
		if (!jwtTokenUtil.tokenValido(token.get()))
			response.getErrors().add("Token inválido ou expirado");

		if (!response.getErrors().isEmpty())
			return ResponseEntity.badRequest().body(response);

		String refreshToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenDto(refreshToken));
		return ResponseEntity.ok(response);
	}
}
