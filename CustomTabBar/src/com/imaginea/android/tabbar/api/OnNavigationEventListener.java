package com.imaginea.android.tabbar.api;

import android.widget.TabHost.TabSpec;

/**
 * @author ved
 *
 * Activities that will be used in TabbedActivity context can chose to implement
 * this delegate to receive callbacks when tabEvents happen
 * 
 * This interface is only for events that result in hiding/appearing or re-appearing of activity views
 * 
 * Callbacks will happen ONLY when the 2 activities involved (1 about to appear and 1 about to disappear)
 * are in TabbedActivity.</b></b> Example-  startActivity(intent) will start and push activity which will not be in
 * tabbedActivity context. None of the callbacks will happen in this case. Where as 
 * pushActivity() of {@link TabSpec}  will push the activity in TabbedActivity itself and callbacks will be received.
 *  
 * 
 */
public interface OnNavigationEventListener {
	
	/**
	 * Called when activityView is about to appear in TabbedActivity
	 * Not called when an Activity is not in Tabbed Context
	 * Called AFTER the activity has been started
	 */
	public void activityViewWillAppear();
	
	
	/**
	 * Called when activityView is about to be removed from TabbedActivity
	 * 
	 * isCalled when a new activity is pushed in same tab
	 * isCalled when tab is switched
	 * <b>isNOTCalled when back is pressed instead activity is destroyed and regular
	 *  activity lifecycle event onDestroy is called</b>
	 *  isNOTCalled when activity is not part of TabbedActivity
	 */
	public void activityViewWillDisAppear();
	

}
