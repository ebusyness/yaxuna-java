package yaxuna;

import yaxuna.util.StringUtil;

public class XMLAttribute {
	
	/**
	 * The Attribute name
	 */
	public String name;
	
	/**
	 * The Attribute value
	 */	
	public String value;
	
	/**
	 * Constructs an XMLAttribute
	 * @param inputName The attribute name
	 * @param inputValue String value of the attribute
	 */
	public XMLAttribute( String name, String value ) {
		// this framework is a lightwight & lazy tool - so attributes names are not validated
		this.name = name;
		this.value = value;
	}
	
	/**
	 * converts the attributes name and value to an XML Attribute String e.g. name="myName" 
	 * @return
	 */
	public String toXMLString() {
		String xmlValue = StringUtil.escapeXml( this.value );
		String xmlName = StringUtil.escapeXml( this.name );
		return xmlName + "=\"" + xmlValue + "\"";
	}
	
}

