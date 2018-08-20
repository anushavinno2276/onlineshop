/**
 * CouponServiceProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.coupon.service;

import org.coupon.service.model.CouponResult;

public class CouponServiceProxy implements org.coupon.service.CouponService {
	private String _endpoint = null;
	private org.coupon.service.CouponService couponService = null;

	public CouponServiceProxy() {
		_initCouponServiceProxy();
	}

	public CouponServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initCouponServiceProxy();
	}

	private void _initCouponServiceProxy() {
		try {
			couponService = (new org.coupon.service.CouponServiceServiceLocator()).getCouponService();
			if (couponService != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) couponService)._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) couponService)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (couponService != null)
			((javax.xml.rpc.Stub) couponService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.coupon.service.CouponService getCouponService() {
		if (couponService == null)
			_initCouponServiceProxy();
		return couponService;
	}

	public CouponResult applyCoupon(java.lang.String coupon) throws java.rmi.RemoteException {
		if (couponService == null)
			_initCouponServiceProxy();
		return couponService.applyCoupon(coupon);
	}

}