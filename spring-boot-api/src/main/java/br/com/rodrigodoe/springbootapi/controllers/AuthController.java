package br.com.rodrigodoe.springbootapi.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigodoe.springbootapi.repository.UserRepository;
import br.com.rodrigodoe.springbootapi.security.AccountCredentialsVO;
import br.com.rodrigodoe.springbootapi.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository repository;
	
	@ApiOperation(value = "Antenticated a user by credentials")
	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsVO data){
		try { 
			var username  = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			var user = repository.findByUsername(username);
			var token = "";
			if(user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			}else {
				throw new UsernameNotFoundException("Username "+username+" not found");
			}
			
			Map<Object, Object> model = new HashMap<>();
			model.put("token", token);
			return ResponseEntity.ok(model);
		}catch(Exception e) {
			throw new BadCredentialsException("Invalid username/password");
		}
	}
 	

}
