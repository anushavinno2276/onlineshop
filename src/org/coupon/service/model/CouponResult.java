/**
 * CouponResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.coupon.service.model;

public class CouponResult implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String type;

	private double value;

	public CouponResult() {
	}

	public CouponResult(java.lang.String type, double value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the type value for this CouponResult.
	 * 
	 * @return type
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Sets the type value for this CouponResult.
	 * 
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * Gets the value value for this CouponResult.
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value value for this CouponResult.
	 * 
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof CouponResult))
			return false;
		CouponResult other = (CouponResult) obj;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.type == null && other.getType() == null)
				|| (this.type != null && this.type.equals(other.getType()))) && this.value == other.getValue();
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getType() != null) {
			_hashCode += getType().hashCode();
		}
		_hashCode += new Double(getValue()).hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			CouponResult.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.service.coupon.org", "CouponResult"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("type");
		elemField.setXmlName(new javax.xml.namespace.QName("http://model.service.coupon.org", "type"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("value");
		elemField.setXmlName(new javax.xml.namespace.QName("http://model.service.coupon.org", "value"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	@SuppressWarnings("rawtypes")
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	@SuppressWarnings("rawtypes")
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
