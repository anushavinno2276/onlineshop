package org.online.shopping.repository.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.online.shopping.repository.Database;
import org.online.shopping.repository.DatabaseFactory;

/**
 * Servlet Filter implementation class DatabaseFilter
 */
@WebFilter("/*")
public class DatabaseIntializationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public DatabaseIntializationFilter() {

	}

	/****
	 * To initialize the database object
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		DatabaseFactory.initiateDatabase(new Database());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

}
