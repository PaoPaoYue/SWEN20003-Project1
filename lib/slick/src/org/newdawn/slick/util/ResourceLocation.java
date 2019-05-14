package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;

/**
 * A location from which resources can be loaded
 * 
 * @author kevin
 */
public interface ResourceLocation {

	/**
	 * Get a Resource as an input stream
	 * 
	 * @param ref The reference to the Resource to retrieve
	 * @return A stream from which the Resource can be read or
	 * null if the Resource can't be found in this location
	 */
	public InputStream getResourceAsStream(String ref);

	/**
	 * Get a Resource as a URL
	 * 
	 * @param ref The reference to the Resource to retrieve
	 * @return A URL from which the Resource can be read
	 */
	public URL getResource(String ref);
}
