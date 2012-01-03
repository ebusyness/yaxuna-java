/**
 * String utility class. May be extended in future versions.
 */
package yaxuna.util;

import java.lang.StringBuffer;

public final class StringUtil {
	
	/**
	 * String encoding type XML.
	 */
	public static final short ENCODE_TYPE_XML = 1;
	
	
	/**
	 * Convert a given string to an encoded string according to the selected encoding type.
	 * @param str String To encode
	 * @param encType Encoding type  
	 * @return The encoded String
	 */
	public static String encode( String str, short encType ) {
		if( encType == StringUtil.ENCODE_TYPE_XML ) {
			return StringUtil.escapeXml( str );
		}
		return str;
	}

	/**
	 * Convert a given string to an encoded xml string. (Replaces < > " ' & with their entities)
	 * @param str String To encode
	 * @param encType Encoding type  
	 * @return The encoded String
	 */
	public static String escapeXml( String str ) {
		if ( str == null ) {
			return null;
		}

		int len = str.length();
		if ( len == 0 ) {
			return str;
		}

		StringBuffer encodedBuffer = new StringBuffer();
		
		// parse string and encode characters
		for (int i = 0; i < len; i++) {
			char chr= str.charAt(i);
			if ( chr== '<' ) {
				encodedBuffer.append( "&lt;" );
			} else if ( chr == '>' ) {
				encodedBuffer.append( "&gt;" ); 
			} else if ( chr == '\"' ) {
				encodedBuffer.append( "&quot;" );
			} else if ( chr == '\'' ) {
				encodedBuffer.append( "&apos;" );
			} else if ( chr == '&' ) {
				encodedBuffer.append( "&amp;" );
			} else {
				encodedBuffer.append( chr );
			}
		}

		return encodedBuffer.toString();
	}

	// escapeXmlCData
}
