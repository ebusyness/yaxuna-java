/**
 * 
 */
package yaxuna.unittest;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yaxuna.XMLAttribute;

/**
 * @author Samuel
 *
 */
public class TestXMLAttribute {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link yaxuna.XMLAttribute#XMLAttribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testXMLAttribute() {
		XMLAttribute attribute = new XMLAttribute( "name" , "myName" );
		// the basic test
		assertEquals( attribute.name, "name" );
		
		assertEquals( attribute.value, "myName" );
	}

	/**
	 * Test method for {@link yaxuna.XMLAttribute#toXMLString()}.
	 */
	@Test
	public final void testToXMLString() {

		XMLAttribute attribute = new XMLAttribute( "name" , "myName" );
		// the basic test
		assertEquals( attribute.toXMLString() , "name=\"myName\"" );

		// test xml encoding of value
		attribute.value="<div>";
		assertEquals( attribute.toXMLString() , "name=\"&lt;div&gt;\"" );

		attribute.value="&'\"";
		assertEquals( attribute.toXMLString() , "name=\"&amp;&apos;&quot;\"" );
		
		attribute.value="&amp;&apos;&quot;";

		assertEquals( attribute.toXMLString() , "name=\"&amp;amp;&amp;apos;&amp;quot;\"" );		

		// test xml encoding of name
		attribute.value="- empty -";
		attribute.name="<div>";
//		fail("todo - thats not finished");
		// assertEquals( attribute.toXMLString() , "&lt;div&gt;=\"- empty -" );
		
		// System.out.print( attribute.toXMLString() );
	}

}

