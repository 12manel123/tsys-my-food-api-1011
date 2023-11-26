package com.myfood.security.jwt;
/**
 * @author Davi Maza
 *
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Authentication entry point for handling unauthorized access.
 * This class implements the {@link org.springframework.security.web.AuthenticationEntryPoint} interface.
 * It is responsible for handling authentication errors and providing a customized response to the client.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	/**
    * Called when an unauthorized request is detected.
    * Generates a customized JSON response and sets the appropriate status code in the HttpServletResponse.
    *
    * @param request        The HttpServletRequest representing the incoming request.
    * @param response       The HttpServletResponse representing the outgoing response.
    * @param authException  The AuthenticationException that triggered the unauthorized access.
    * @throws IOException      If an I/O error occurs during the writing of the response.
    * @throws ServletException If a servlet-specific error occurs.
    */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "Unauthorized");
		body.put("message", authException.getMessage());
		body.put("path", request.getServletPath());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

}
