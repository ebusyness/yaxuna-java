/**
 * String utility class. May be extended in future versions.
 */
package yaxuna.util;

import java.lang.StringBuffer;

public class StringUtil {
	
	/**
	 * Convert a given string to an encoded xml string. (Replaces < > " ' & with their entities)
	 * @param str String to escape
	 * @return The encoded String
	 */
	public static String escapeXml( String str ) {
		if ( str == null ) {
			return str;
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

	/**
	 * Convert a given string to an encoded xml CDATA section string. This replaces ]]> with ]]]]><![CDATA[>. So we have to two CDData section.
	 * @param str String to escape
	 * @return The encoded String
	 */
	public static String escapeXmlCData( String str ) {
		if ( str == null ) {
			return str;
		}
		return str.replace("\\]\\]>", "]]]]><![CDATA[>");
	}
	
	/**
	 * Convert a given string to an encoded xml comment section string. If necessary, internal occurrences of "--" will be replaced with "- -" and a leading and/or trailing space will be added to the string.
	 * @param str String to escape
	 * @return The encoded String
	 */
	public static String escapeXmlComment( String str ) {
		if ( str == null ) {
			return str;
		}
		if( str.charAt( 0 ) == "-".charAt(0) ) {
			str += " "+str;
		}
		if( str.charAt( str.length() ) == "-".charAt(0) ) {
			str += str+" ";
		}
		return str.replace("--", "- -");
	}
}
