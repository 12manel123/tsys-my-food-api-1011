package com.myfood.controllers;
/**
 * @author David Maza
 *
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfood.dto.User;
import com.myfood.dto.auth.JwtResponse;
import com.myfood.dto.auth.LoginRequest;
import com.myfood.dto.auth.SignupRequest;
import com.myfood.security.jwt.JwtUtils;
import com.myfood.security.service.UserDetailsImpl;
import com.myfood.services.RolServiceImpl;
import com.myfood.services.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserServiceImpl userServ;
	@Autowired
	private RolServiceImpl roleServ;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtUtils jwtUtils;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    String jwt = jwtUtils.generateAccesToken(authentication.getName());
	    
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getUsername(), userDetails.getId(), roles));
	}


	@GetMapping("/validate-jwt")
	public ResponseEntity<Boolean> validateJwt(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization").replace("Bearer", "");
		return ResponseEntity.ok(this.jwtUtils.isTokenValid(jwt));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest){
		   String name = signUpRequest.getUsername();
	
		if(userServ.getUserByUsername(name) != null) {
		return ResponseEntity.badRequest().body("Error: Username is already taken!");
		}

		User user = new User();
		
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(this.encoder.encode(signUpRequest.getPassword()));
		user.setRole(this.roleServ.findByName("USER").orElseThrow(() -> new RuntimeException("Not found")));
		user.setEmail(signUpRequest.getEmail());
		this.userServ.createUser(user);
		
		return ResponseEntity.ok("User created!");
	}
	
	
	
}
