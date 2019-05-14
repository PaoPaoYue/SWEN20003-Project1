package org.newdawn.slick.loading;

import java.io.IOException;

/**
 * A description of any class providing a Resource handle that be loaded
 * at a later date (i.e. deferrred)
 *
 * @author kevin
 */
public interface DeferredResource {

	/**
	 * Load the actual Resource
	 * 
	 * @throws IOException Indicates a failure to load the Resource
	 */
	public void load() throws IOException;
	
	/**
	 * Get a description of the Resource to be loaded
	 * 
	 * @return The description of the Resource to be loaded
	 */
	public String getDescription();
}
