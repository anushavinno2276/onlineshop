/**
 * CouponServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.coupon.service;

import javax.xml.namespace.QName;

public class CouponServiceServiceLocator extends org.apache.axis.client.Service implements org.coupon.service.CouponServiceService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponServiceServiceLocator() {
    }


    public CouponServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CouponServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CouponService
    private java.lang.String CouponService_address = "http://localhost:8080/CouponServiceProject/services/CouponService";

    public java.lang.String getCouponServiceAddress() {
        return CouponService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CouponServiceWSDDServiceName = "CouponService";

    public java.lang.String getCouponServiceWSDDServiceName() {
        return CouponServiceWSDDServiceName;
    }

    public void setCouponServiceWSDDServiceName(java.lang.String name) {
        CouponServiceWSDDServiceName = name;
    }

    public org.coupon.service.CouponService getCouponService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CouponService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCouponService(endpoint);
    }

    public org.coupon.service.CouponService getCouponService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.coupon.service.CouponServiceSoapBindingStub _stub = new org.coupon.service.CouponServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCouponServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCouponServiceEndpointAddress(java.lang.String address) {
        CouponService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(@SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.coupon.service.CouponService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.coupon.service.CouponServiceSoapBindingStub _stub = new org.coupon.service.CouponServiceSoapBindingStub(new java.net.URL(CouponService_address), this);
                _stub.setPortName(getCouponServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CouponService".equals(inputPortName)) {
            return getCouponService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.coupon.org", "CouponServiceService");
    }

    private java.util.HashSet<QName> ports = null;

    public java.util.Iterator<QName> getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet<QName>();
            ports.add(new javax.xml.namespace.QName("http://service.coupon.org", "CouponService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CouponService".equals(portName)) {
            setCouponServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
