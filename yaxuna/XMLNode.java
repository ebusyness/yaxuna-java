package yaxuna;

import java.util.HashMap;

import yaxuna.util.StringUtil;

/**
 * @author Samuel
 *
 */
public class XMLNode extends AbstractXMLList<XMLNode> {
	
	// TODO may later this class could be extended by differnt node types 

	/**
	 * The node name (tag name)
	 */
	public String name;
	
	/**
	 * The node text content
	 */
	public String text;
	
	/**
	 * The node type
	 */
	public int type;
	
	/**
	 * Array of XMLAttribute instances
	 */
	public HashMap<String, XMLAttribute> attributes;
	
	/**
	 * Adds an {@link yaxuna.XMLAttribute} instance
	 * @param {yaxuna.XMLAttribute} attr The attribute to add
	 */
	public void addAttribute ( XMLAttribute attr ) {
		attributes.put( attr.name, attr );
	}

	/**
	 * Get value of attribute by name
	 * @param name Name of Attribute
	 * @return value of Attribute
	 */
	public String getAttribute ( String name ) {
		XMLAttribute attr = this.attributes.get( name );
		return attr.value;
	}
	
	/**
	 * The elements parent node
	 */
	public XMLNode parentNode;
	
	/** 
	 * XMLNode of type tag
	 */
	private final short NODE_TYPE_TAG = 0;

	/** 
	 * XMLNode of type text
	 */
	private final short NODE_TYPE_TEXT = 1;

	/** 
	 * XMLNode of type CDATA-section
	 */
	private final short NODE_TYPE_CDATASECTION = 2;
	
	/** 
	 * XMLNode of type comment
	 */
	private final short NODE_TYPE_COMMENT = 3;

	/** 
	 * XMLNode of type comment
	 */
	private final short NODE_TYPE_PROCESSINSTRUCTION = 4;	
	
	/**
	 * string wrapper for start nodes
	 */
	private final String wrapStartNode = "<%s%s>";                 // tagname + attributes
	
	/**
	 * string wrapper for empty nodes 
	 */
	private final String wrapEmptyNode = "<%s%s/>";                // tagname + attributes

	/**
	 * string wrapper for end nodes 
	 */
	private final String wrapEndNode = "</%s>";                    // tagname

	/**
	 * string wrapper for processing instructions
	 */
	private final String wrapProcessingInstruction = "<?%s%s?>";   // tagname + attributes

	/**
	 * string wrapper for cdata sections
	 */
	private final String wrapCDATA = "<![CDATA[%s]]>";             // cdata content

	/**
	 * string wrapper for comments
	 */
	private final String wrapComment = "<!--%s-->";                // the comments content

	/**
	 * @return Returns the start text o a tag or the whole node text content (empty tags, comments, CDDATA sections)
	 */
	public String startText () {
		String attributesString = "";
		
		for( XMLAttribute attribute : this.attributes.values() ) {
			attributesString += " "+attribute.toXMLString();
		}
		if(	this.type == this.NODE_TYPE_TAG ) {
			if( this.childNodes.size() > 0 ) {
				return String.format( this.wrapStartNode, this.name , attributesString );
			} else {
				return String.format( this.wrapEmptyNode, this.name , attributesString );
			}
		} else if ( this.type == this.NODE_TYPE_TEXT ) {
			return ( this.text != null ) ? StringUtil.escapeXml( this.text ) : "";
		} else if ( this.type == this.NODE_TYPE_CDATASECTION ) {
			String cddataText = StringUtil.escapeXmlCData( this.text != null  ? this.text : "" );
			return String.format( this.wrapCDATA, cddataText );
		} else if ( this.type == this.NODE_TYPE_COMMENT ) {
			String commentText = StringUtil.escapeXmlComment( this.text != null  ? this.text : "" );
			return String.format( wrapComment, commentText );
		} else if ( this.type == this.NODE_TYPE_PROCESSINSTRUCTION ) {
			return String.format( this.wrapProcessingInstruction, this.name , attributesString );
		}
		return null;
	}
	
	/**
	 * @return Returns the end text of a closing tag 
	 */
	public String endText () {
		if(	this.type == this.NODE_TYPE_TAG ) {
			if( this.childNodes.size() > 0 ) {
				return String.format( wrapEndNode, this.name );
			}
		}
		return null;
	}
}
