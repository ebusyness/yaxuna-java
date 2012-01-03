package yaxuna;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractXMLList<XMLNodeType extends AbstractXMLList> {
	
	/** 
	 * ArrayList of <XMLNodeType> instances (childs)
	 * @type Array
	 */
	public ArrayList<XMLNodeType> childNodes;
	
	public XMLNodeType parentNode;
	
	/**
	 * Abstract method to add an yaxuna.XMLAttribute instance. Implement this where needed.
	 * @param attr The attribute to add
	 */
	public abstract void addAttribute( XMLAttribute attr );
	
	/** 
	 * Adds an yaxuna.XMLNodeType
	 * @param {yaxuna.XMLNodeType} node The Node to add
	 */
	public void appendChild ( XMLNodeType node ) {
		// TODO hierarchy check -> it shouldn't be possible to add a parent to a child
		
		// detach from old parentNode
		XMLNodeType oldParent = node.parentNode;
		if( oldParent != null ) {
			oldParent.removeChild( node );
		}
		this.childNodes.add( node );
		node.parentNode = this;
	}

	/**
	 * @return The first child node
	 */
	public XMLNodeType firstChild () {
		if( this.childNodes != null && this.childNodes.size() > 0 ) {	
			return this.childNodes.get(0);
		}
		return null;		
	}

	/**
	 * @return The last child node
	 */
	public XMLNodeType lastChild () {
		if( this.childNodes != null && this.childNodes.size() > 0 ) {
			return this.childNodes.get( this.childNodes.size()-1 );
		}
		return null;
	}

	/** 
	 * Remove an yaxuna.XMLNode
	 * @return The Node to remove
	 */		
	public XMLNodeType removeChild ( XMLNodeType node ) {
		int index = this.childNodes.indexOf( node );
		if( index != -1 ) {
			return this.childNodes.remove( index );
		}
		return node;
	}	

}
