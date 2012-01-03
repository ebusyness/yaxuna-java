package yaxuna;

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
	public XMLAttribute( String inputName, String inputValue ) {
		this.name = inputName;
		this.value = inputValue;
	}
	
	public String toString() {
		return this.name + "\"" + this.value + "\"";
	}
	
	public String toXMLString() {
		String xmlValue = this.value;
		xmlValue = xmlValue.replaceAll( "/<|>|&|\"/g" , this.getReplacement ) 
		return this.name + "\"" + xmlValue + "\"";
	}
	
	private String getReplacement() {
		return "";
	}
}
