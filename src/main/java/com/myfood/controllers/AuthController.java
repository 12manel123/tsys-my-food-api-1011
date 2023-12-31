package com.myfood.controllers;
/**
 * @author David Maza
 *
 */
import java.time.LocalDateTime;
import java.time.ZoneId;
/**
 * @author David Maza
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	@PostMapping("/login")
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
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest){
		Map<String, Object> responseData = new HashMap<String, Object>();
		   String name = signUpRequest.getUsername();
	
		if(userServ.getUserByUsername(name) != null) {
	    responseData.put("Error", "Error: Username is already taken!");
		return ResponseEntity.badRequest().body(responseData);
		}

		ZoneId madridZone = ZoneId.of("Europe/Madrid");
		User user = new User();		
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(this.encoder.encode(signUpRequest.getPassword()));
		user.setRole(this.roleServ.findByName("USER").orElseThrow(() -> new RuntimeException("Not found"))); 
		user.setEmail(signUpRequest.getEmail());
		user.setCreatedAt(LocalDateTime.now(madridZone));
		this.userServ.createUser(user);
		
		return ResponseEntity.ok(Map.of("Message", "User created!"));
	}
		
}
