package yaxuna;

import java.util.HashMap;

/**
 * @author Samuel
 *
 */
/**
 * @author Samuel
 *
 */
/**
 * @author Samuel
 *
 */
/**
 * @author Samuel
 *
 */
public class XMLNode extends AbstractXMLList<XMLNode> {
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
	 * string wrapper for start nodes
	 */
	private final static String wrapStartNode = "<%s%s>";                 // tagname + attributes
	private final static String wrapEmptyNode = "<%s%s/>";                // tagname + attributes
	private final static String wrapProcessingInstruction = "<?%s%s?>";   // tagname + attributes
	private final static String wrapCDATA = "<![CDATA[%s]]>";             // cdata content
	private final static String wrapComment = "<!--%s-->";                // the comments content
	
	/**
	 * @return Returns the start text o a tag or the whole node text content (empty tags, comments, CDDATA sections)
	 */
	public String startText () {
		String attributesString = "";
		for( String el : this.attributes ) {
			var attr = this.attributes.get( el );
			attributesString+= (' ' + attr.name + '="' + yaxuna.XML.encodeXMLAttribute( attr.value ) + '"');
		}
		if(	this.type == yaxuna.NODE_TYPE_TAG ) {
			if( this.childNodes.length ) {
				return wrapStartNode( this.name , attributesString );
			} else {
				return wrapEmptyNode( this.name , attributesString );
			}
		} else if ( this.type == yaxuna.NODE_TYPE_TEXT ) {
			return (this.text) ? yaxuna.XML.encodeXMLText( this.text ) : "";
		} else if ( this.type == yaxuna.NODE_TYPE_CDATASECTION ) {
			return wrapCDATA( (this.text) ? this.text : "" );
		} else if ( this.type == yaxuna.NODE_TYPE_COMMENT ) {
			return wrapComment( (this.text) ? this.text : "" );
		} else if ( this.type == yaxuna.NODE_TYPE_PROCESSINSTRUCTION ) {
			return wrapPI( this.name , attributesString );
		}
	},
	
	/**
	 * @return Returns the end text of a closing tag 
	 */
	public String endText () {
		var wrapEndNode = function( name ) { return '</'+name+'>' };
		if(	this.type == yaxuna.NODE_TYPE_TAG ) {
			if( this.childNodes.length ) {
				return wrapEndNode( this.name )
			}
		}
		return undefined;
	}
}
