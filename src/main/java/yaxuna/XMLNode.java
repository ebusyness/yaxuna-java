package yaxuna;

import java.util.ArrayList;
import java.util.HashMap;

import yaxuna.util.StringUtil;

/**
 * @author Samuel
 * 
 * http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core.html
 */
public class XMLNode {

	//
	// private constants
	// TODO may be later this class could be extended by differnt node types
	//

	/**
	 * XMLNode of type tag
	 */
	private final short ELEMENT_NODE = 1;

	/**
	 * XMLNode of type text
	 */
	private final short TEXT_NODE = 3;

	/**
	 * XMLNode of type CDATA-section
	 */
	private final short CDATA_SECTION_NODE = 4;

	/**
	 * XMLNode of type comment
	 */
	private final short NODETYPE_PROCESSINSTRUCTION = 7;
	
	/**
	 * XMLNode of type comment
	 */
	private final short COMMENT_NODE = 8;

	/**
	 * string wrapper for start nodes
	 */
	private final String WRAP_STARTNODE = "<%s%s>"; // tagname + attributes

	/**
	 * string wrapper for empty nodes
	 */
	private final String WRAP_EMPTYNODE = "<%s%s/>"; // tagname + attributes

	/**
	 * string wrapper for end nodes
	 */
	private final String WRAP_ENDNODE = "</%s>"; // tagname

	/**
	 * string wrapper for processing instructions
	 */
	private final String WRAP_PROCESSINGINSTRUCTION = "<?%s%s?>"; // tagname + attributes

	/**
	 * string wrapper for cdata sections
	 */
	private final String WRAP_CDATA = "<![CDATA[%s]]>"; // cdata content

	/**
	 * string wrapper for comments
	 */
	private final String WRAP_COMMENT = "<!--%s-->"; // the comments content

	//
	// public propertiess
	//

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
	 * ArrayList of XMLNode instances (childs)
	 */
	public ArrayList<XMLNode> childNodes;

	/**
	 * Adds an {@link yaxuna.XMLAttribute} instance
	 * 
	 * @param {yaxuna.XMLAttribute} attr The attribute to add
	 */
	public void addAttribute(XMLAttribute attr) {
		attributes.put(attr.name, attr);
	}

	/**
	 * Get value of attribute by name
	 * 
	 * @param name
	 *            Name of Attribute
	 * @return value of Attribute
	 */
	public String getAttribute(String name) {
		XMLAttribute attr = this.attributes.get(name);
		return attr.value;
	}

	/**
	 * The elements parent node
	 */
	public XMLNode parentNode;

	/**
	 * Adds an yaxuna.XMLNodeType
	 * 
	 * @param node
	 *            The Node to add
	 */
	public void appendChild(XMLNode node) {
		// TODO hierarchy check -> it shouldn't be possible to add a parent to a child

		// detach from old parentNode
		XMLNode oldParent = node.parentNode;
		if (oldParent != null) {
			oldParent.removeChild(node);
		}
		this.childNodes.add(node);
		node.parentNode = this;
	}

	/**
	 * @return The first child node
	 */
	public XMLNode firstChild() {
		if (this.childNodes != null && this.childNodes.size() > 0) {
			return this.childNodes.get(0);
		}
		return null;
	}

	/**
	 * @return The last child node
	 */
	public XMLNode lastChild() {
		if (this.childNodes != null && this.childNodes.size() > 0) {
			return this.childNodes.get(this.childNodes.size() - 1);
		}
		return null;
	}

	/**
	 * Remove an yaxuna.XMLNode
	 * 
	 * @return The Node to remove
	 */
	public XMLNode removeChild(XMLNode node) {
		int index = this.childNodes.indexOf(node);
		if (index != -1) {
			return this.childNodes.remove(index);
		}
		return node;
	}

	/**
	 * @return Returns the start text o a tag or the whole node text content (empty tags, comments, CDDATA sections)
	 */
	protected String startText() {
		String attributesString = "";

		for (XMLAttribute attribute : this.attributes.values()) {
			attributesString += " " + attribute.toXMLString();
		}
		if (this.type == this.ELEMENT_NODE) {
			if (this.childNodes.size() > 0) {
				return String.format(this.WRAP_STARTNODE, this.name, attributesString);
			} else {
				return String.format(this.WRAP_EMPTYNODE, this.name, attributesString);
			}
		} else if (this.type == this.TEXT_NODE) {
			return (this.text != null) ? StringUtil.escapeXml(this.text) : "";
		} else if (this.type == this.CDATA_SECTION_NODE) {
			String cddataText = StringUtil.escapeXmlCData(this.text != null ? this.text : "");
			return String.format(this.WRAP_CDATA, cddataText);
		} else if (this.type == this.COMMENT_NODE) {
			String commentText = StringUtil.escapeXmlComment(this.text != null ? this.text : "");
			return String.format(WRAP_COMMENT, commentText);
		} else if (this.type == this.NODETYPE_PROCESSINSTRUCTION) {
			return String.format(this.WRAP_PROCESSINGINSTRUCTION, this.name, attributesString);
		}
		return null;
	}

	/**
	 * @return Returns the end text of a closing tag
	 */
	protected String endText() {
		if (this.type == this.ELEMENT_NODE) {
			if (this.childNodes.size() > 0) {
				return String.format(WRAP_ENDNODE, this.name);
			}
		}
		return null;
	}
}
