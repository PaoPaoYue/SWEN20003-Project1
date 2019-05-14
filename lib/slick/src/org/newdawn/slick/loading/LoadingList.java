package org.newdawn.slick.loading;

import java.util.ArrayList;

import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;

/**
 * A central list where all deferred loading resoures end up when deferred loading is in use. Each
 * texture and sound loaded will be put in this list and can be loaded in one by one 
 *
 * @author kevin
 */
public class LoadingList {
	/** The single instance of this list */
	private static LoadingList single = new LoadingList();
	
	/**
	 * Get the single global loading list
	 * 
	 * @return The single global loading list
	 */
	public static LoadingList get() {
		return single;
	}
	
	/**
	 * Indicate if we're going to use deferred loading. (Also clears the current list)
	 * 
	 * @param loading True if we should use deferred loading
	 */
	public static void setDeferredLoading(boolean loading) {
		single = new LoadingList();
		
		InternalTextureLoader.get().setDeferredLoading(loading);
		SoundStore.get().setDeferredLoading(loading);
	}
	
	/**
	 * Check if we're using deferred loading 
	 * 
	 * @return True if we're using deferred loading
	 */
	public static boolean isDeferredLoading() {
		return InternalTextureLoader.get().isDeferredLoading();
	}
	
	/** The list of deferred resources to load */
	private ArrayList deferred = new ArrayList();
	/** The total number of elements that have been added - does not go down as elements are removed */
	private int total;
	
	/**
	 * Create a new list
	 */
	private LoadingList() {
	}
	
	/**
	 * Add a Resource to be loaded at some later date
	 * 
	 * @param resource The Resource to be added
	 */
	public void add(DeferredResource resource) {
		total++;
		deferred.add(resource);
	}
	
	/**
	 * Remove a Resource from the list that has been loaded for
	 * other reasons.
	 * 
	 * @param resource The Resource to remove
	 */
	public void remove(DeferredResource resource) {
		Log.info("Early loading of deferred Resource due to req: "+resource.getDescription());
		total--;
		deferred.remove(resource);
	}
	
	/**
	 * Get the total number of resources that were in the list originally
	 * 
	 * @return The total number of resources that were in the list originally
	 */
	public int getTotalResources() {
		return total;
	}
	
	/**
	 * Get the number of remaining resources
	 * 
	 * @return The number of resources that still need to be loaded
	 */
	public int getRemainingResources() {
		return deferred.size();
	}
	
	/**
	 * Get the next Resource that requries loading
	 * 
	 * @return The next Resource to load or null if there are no more remaining
	 */
	public DeferredResource getNext() {
		if (deferred.size() == 0) {
			return null;
		}
		
		return (DeferredResource) deferred.remove(0);
	}
}
