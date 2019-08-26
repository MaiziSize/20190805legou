package com.legou.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.legou.Utils.utils.CookieUtils;
import com.legou.Utils.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.TokenService;

public class LegouInterceptor implements HandlerInterceptor {
	
	@Autowired
	TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String tokenValue = CookieUtils.getCookieValue(request, "token");
		if(StringUtils.isBlank(tokenValue)) {
			return true;
		}
		
		LegouResult legouResult = tokenService.getUserByToken(tokenValue);
		if(legouResult.getStatus() != 200) {
			return true;
		}
		
		TbUser tbUser = (TbUser) legouResult.getData();
		request.setAttribute("legouuser", tbUser);
		System.out.println("preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		
	}

}
