package org.online.shopping.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coupon.service.CouponServiceProxy;
import org.coupon.service.model.CouponResult;
import org.online.shopping.entity.Cart;
import org.online.shopping.entity.User;

/**
 * Servlet implementation class CouponController
 */
@WebServlet("/coupon")
public class CouponController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CouponController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(User.ACTION);
		switch (action) {
		case "apply":
			couponService(request, response);
			break;
		default:
			break;
		}
	}

	/***
	 * For coupon sent by user it returns the value of coupon.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void couponService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CouponServiceProxy couponServiceProxy = new CouponServiceProxy();
		double percentage = 0;
		double discount = 0;
		String coupon = request.getParameter(Cart.COUPON);
		double amount = Double.parseDouble(request.getParameter(Cart.AMOUNT));
		try {
			CouponResult result = couponServiceProxy.applyCoupon(coupon);
			if (result.getType().equalsIgnoreCase("percentage")) {
				percentage = result.getValue();
				discount = amount * (percentage / 100);
			} else {
				percentage = result.getValue();
				amount -= percentage;
			}
		} catch (Exception exception) {
			request.setAttribute("errormessage", "Invalid Coupon");
		}

		request.setAttribute(Cart.DISCOUNT, discount);
		request.setAttribute(Cart.AMOUNT, amount);
		requestDispacter.dispatchToUserCartPage(request, response);
	}

}
