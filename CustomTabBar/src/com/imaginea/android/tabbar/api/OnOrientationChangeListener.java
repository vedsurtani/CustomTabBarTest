package com.imaginea.android.tabbar.api;

public interface OnOrientationChangeListener {
	
	public Object dataToSaveBeforeOrientationChange();
	public void activityDidRestartWithNewOrientationChange(Object savedData);

}
