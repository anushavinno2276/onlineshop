package org.online.shopping.controller.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.online.shopping.entity.User.UserType;
import org.online.shopping.service.executor.TaskExecutor;

/**
 * 
 * @author Akshay
 *
 */
@WebFilter("/*")
public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		TaskExecutor.cartScheduler();
		TaskExecutor.productScheduler();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String loginURI = request.getContextPath();
		String requestURI = request.getRequestURI();
		if (requestURI.equals("/OnlineShop/login.jsp") || requestURI.equals("/OnlineShop/login")
				|| requestURI.equals("/OnlineShop/Register.jsp") || requestURI.equals("/OnlineShop/user")) {
			chain.doFilter(request, response);
		} else if (!validate(session)) {
			response.sendRedirect(loginURI + "/login.jsp");
		} else {
			UserType userType = SessionUtility.getUsertype(session);
			if (userType == UserType.BUYER && requestURI.equals(loginURI + "/seller")) {
				response.setStatus(403);
			} else if (userType == UserType.SELLER && (requestURI.equals(loginURI + "/Password.jsp")
					|| requestURI.equals(loginURI + "/Account.jsp"))) {
				response.setStatus(403);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * Checks User is logged in or not
	 * 
	 * @param httpSession
	 * @return
	 */
	private boolean validate(HttpSession httpSession) {
		String name = SessionUtility.getLoggedInUser(httpSession);
		return !name.equals("null");
	}

	@Override
	public void destroy() {
		TaskExecutor.shutddownExecutor();
	}
}
