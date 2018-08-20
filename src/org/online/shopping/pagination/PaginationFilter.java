package org.online.shopping.pagination;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.online.shopping.pagination.Pagination.SortOrder;

/**
 * Servlet Filter implementation class PaginationFilter
 */
@WebFilter("/*")
public class PaginationFilter implements Filter {

	public static Pagination pagination;

	/**
	 * Default constructor.
	 */
	public PaginationFilter() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		pagination = getPaginatedData(request);
		chain.doFilter(request, response);
	}

	/***
	 * To get pagination object.
	 * 
	 * @param request
	 * @return pagination
	 */
	private Pagination getPaginatedData(ServletRequest request) {
		if (request.getParameter("skip") != null && request.getParameter("limit") != null
				&& request.getParameter("sort") != null && request.getParameter("sortorder") != null) {
			pagination = new Pagination();
			pagination.setLimit(Integer.parseInt(request.getParameter("limit")));
			pagination.setSkip(Integer.parseInt(request.getParameter("skip")));
			pagination.sort = new LinkedHashMap<String, SortOrder>();
			pagination.sort.put(request.getParameter("sort"), SortOrder.valueOf(request.getParameter("sortorder")));
			return pagination;
		}
		return null;
	}

}
