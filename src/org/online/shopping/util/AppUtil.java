package org.online.shopping.util;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.Pagination.SortOrder;

/***
 * App Util class
 * 
 * @author Akshay
 *
 */
public class AppUtil {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^[A-Za-z0-9]{8,16}$");

	public static String generateUniqueId() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/***
	 * Checks whether Email is null or it match the pattern.
	 * 
	 * @param email
	 */
	public static boolean validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		} else {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
			return matcher.find();
		}
	}

	/***
	 * Checks whether Password is null or it match the pattern.
	 * 
	 * @param password
	 */
	public static boolean validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			return false;
		} else {
			Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
			return matcher.find();
		}
	}

	/***
	 * Checks whether Name is null or Empty.
	 * 
	 * @param name
	 */
	public static boolean validateName(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		return true;
	}

	/***
	 * It checks whether the difference between added time and notified time is
	 * not greater than interval in which mail has to be sent
	 * 
	 * @param hours
	 * @param milliseconds
	 */
	public static boolean differenceGreaterThan(int hours, long milliseconds) {
		double diffHours = (System.currentTimeMillis() - milliseconds) / (1000 * 60 * 60);
		return (diffHours >= hours);
	}

	/***
	 * Query for pagination.
	 * 
	 * @param pagination
	 * @return String
	 */

	public static String getRecordSortLimitQuery(Pagination pagination) {
		String query = " ";
		if (pagination != null) {
			String sortKey = null;
			SortOrder sortOrder = null;
			for (Map.Entry<String, SortOrder> entry : pagination.sort.entrySet()) {
				sortKey = entry.getKey();
				sortOrder = entry.getValue();
			}
			if (sortKey != null) {
				query += "ORDER BY " + sortKey;
			}
			if (sortOrder != null) {
				query += " " + sortOrder;
			}
			if (pagination.getSkip() == 0 && pagination.getLimit() != 0) {
				query += " LIMIT " + pagination.getLimit();
			}
			if (pagination.getSkip() != 0 && pagination.getLimit() != 0) {
				query += " LIMIT " + pagination.getSkip() + "," + pagination.getLimit();
			}
		}
		return query;
	}

}
