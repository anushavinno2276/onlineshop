package org.online.shopping.pagination;

import java.util.Map;

/***
 * Pagination class .
 * 
 * @author Akshay
 *
 */
public class Pagination {

	private int limit;
	private int skip;

	public Map<String, SortOrder> sort;

	public enum SortOrder {
		desc, asc
	}

	public Pagination() {

	}

	public int getLimit() {
		return limit;
	}

	/***
	 * Limit gives how many records per page
	 * 
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getSkip() {
		return skip;
	}

	/***
	 * Skip gives how many records displayed.
	 * 
	 * @param skip
	 */

	public void setSkip(int skip) {
		this.skip = skip;
	}

}
