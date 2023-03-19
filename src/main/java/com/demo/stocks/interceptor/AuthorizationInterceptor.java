package com.demo.stocks.interceptor;

import com.demo.stocks.domain.Constants;
import com.demo.stocks.exception.ErrorCode;
import com.demo.stocks.exception.InvalidTokenException;
import com.demo.stocks.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// parse token from HTTP header
		String token = request.getHeader(Constants.TOKEN);
		log.debug("Getting authorization token ={}", token);

		if(!authenticationService.isTokenValid(token)){
			throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
		}

		return true;
	}
	
	
}
