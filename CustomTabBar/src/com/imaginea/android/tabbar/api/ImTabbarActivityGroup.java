package com.imaginea.android.tabbar.api;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.imaginea.android.tabbar.R;
import com.imaginea.android.tabbar.api.ImTabHost.ITabSpec;
import com.imaginea.android.tabbar.api.ImTabHost.IndicatorStrategy;
import com.imaginea.android.tabbar.api.ImTabHost.LabelAndIconIndicatorStrategy;
import com.imaginea.android.tabbar.api.ImTabHost.LabelIndicatorStrategy;
import com.imaginea.android.utility.Utility;

public class ImTabbarActivityGroup extends ActivityGroup{
	public ImTabHost mTabHost;
	public String mDefaultTab = null;
	private int mDefaultTabIndex = -1;
	public static ImTabbarActivityGroup AppContext;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_tabbar);
		AppContext = this;
	//	Map<String,Object> myData =  (Map<String,Object>)getLastNonConfigurationInstance();
	//	if(myData == null){
			mTabHost = (ImTabHost)findViewById(R.id.tabhost);
			//addTestTabs();
	//	}
		/*else
		{
			Map<Intent,Object> savedinstancesData = (Map<Intent,Object>)myData.get("savedinstancedata");
			ImTabHost oldTabHost = (ImTabHost)myData.get("tabhost");
			mTabHost = (ImTabHost)findViewById(R.id.tabhost);
			mTabHost.setup();
			for(ITabSpec oldSpec: oldTabHost.mTabSpecs)
			{
				ITabSpec newSpec = mTabHost.newTabSpec("");
				IndicatorStrategy indicatorStrategy = oldSpec.getIndicatorStrategy();
				if(indicatorStrategy instanceof LabelIndicatorStrategy)
				{
					newSpec.setIndicator(((LabelIndicatorStrategy) indicatorStrategy).getLabel());
				}
				else if(indicatorStrategy instanceof LabelAndIconIndicatorStrategy){
					newSpec.setIndicator(((LabelAndIconIndicatorStrategy) indicatorStrategy).getLabel(),
							((LabelAndIconIndicatorStrategy) indicatorStrategy).getIcon());
				}
				int stackSize = oldSpec.numberOfActivities();
				for(int i=0;i<stackSize;i++)
				{
					Activity a = oldSpec.activityAtIndex(i);
					Intent intent = a.getIntent();
					newSpec.pushActivity(intent);

					provide savedData from previous activity
					if(a instanceof OnOrientationChangeListener)
					{
						Object savedData = savedinstancesData.get(intent);
						((OnOrientationChangeListener)a).activityDidRestartWithNewOrientationChange(savedData);

					}
				}
				mTabHost.addTab(newSpec);
			}
		}*/

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Map<Intent,Object> activityData = new HashMap<Intent, Object>();
		for(ITabSpec spec: mTabHost.mTabSpecs)
		{
			int stackSize = spec.numberOfActivities();
			for(int i=0;i<stackSize;i++)
			{
				Activity a = spec.activityAtIndex(i);
				if(a instanceof OnOrientationChangeListener)
				{
					Object savedData = ((OnOrientationChangeListener)a).dataToSaveBeforeOrientationChange();
					if(savedData != null)
						activityData.put(a.getIntent(), savedData);
				}
			}
		}

		Map<String,Object> myData = new HashMap<String,Object>();
		myData.put("tabhost", mTabHost);
		myData.put("savedinstancedata", activityData);
		return myData;
	}


	@Override
	protected void onResume() {
		super.onResume();
	}	
	public ImTabHost getImTabHost()
	{
		return mTabHost;
	}


	/*private void addTestTabs()
	{
		mTabHost.setup();

		{ITabSpec spec1 = mTabHost.newTabSpec("");
		spec1.setIndicator("Tab 0",Utility.drawableFromResource(R.drawable.icon, this));
		Intent intent = new Intent(this,Home.class);
		spec1.pushActivity(intent);
		mTabHost.addTab(spec1);}

		{ITabSpec spec1 = mTabHost.newTabSpec("");
		spec1.setIndicator("Tab 1");
		Intent intent = new Intent(this,Main.class);
		spec1.pushActivity(intent);

		mTabHost.addTab(spec1);}

		{ITabSpec spec1 = mTabHost.newTabSpec("");
		spec1.setIndicator("Tab 2");
		Intent intent = new Intent(this,Main.class);
		spec1.pushActivity(intent);

		mTabHost.addTab(spec1);}

	}*/

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ITabSpec currentTab =  mTabHost.mTabSpecs.get(mTabHost.currentTabNumber());
			currentTab.popActivity();
			Log.d("AppContext", "Key down event");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	protected void handleOrientationChange(Map<String,Object> lastNonConfigurationInstance)
	{
		if(lastNonConfigurationInstance == null)
			throw new NullPointerException("lastNonConfigurationInstance is null, make sure you pass" +
			" object retrieved using getLastNonConfigurationInstance() on your ImTabbarActivityGroup instance ");

		ImTabHost oldTabHost = (ImTabHost)lastNonConfigurationInstance.get("tabhost");
		if(oldTabHost == null)
			throw new NullPointerException("tabhost cannot be found in lastNonConfigurationInstance. Refer onRetainNonConfigurationInstance() method");
		Map<Intent,Object> savedinstancesData = (Map<Intent,Object>)lastNonConfigurationInstance.get("savedinstancedata");
		if(savedinstancesData == null)
			Log.w("ITabBarActivity", "savedInstancesData is null. Activities implementing OnOrientationChangeListeners will receive callbacks with no saved data");


		mTabHost = (ImTabHost)findViewById(R.id.tabhost);
		mTabHost.setup();
		for(ITabSpec oldSpec: oldTabHost.mTabSpecs)
		{
			ITabSpec newSpec = mTabHost.newTabSpec("");
			IndicatorStrategy indicatorStrategy = oldSpec.getIndicatorStrategy();
			if(indicatorStrategy instanceof LabelIndicatorStrategy)
			{
				newSpec.setIndicator(((LabelIndicatorStrategy) indicatorStrategy).getLabel());
			}
			else if(indicatorStrategy instanceof LabelAndIconIndicatorStrategy){
				newSpec.setIndicator(((LabelAndIconIndicatorStrategy) indicatorStrategy).getLabel(),
						((LabelAndIconIndicatorStrategy) indicatorStrategy).getIcon());
			}
			int stackSize = oldSpec.numberOfActivities();
			for(int i=0;i<stackSize;i++)
			{
				Activity a = oldSpec.activityAtIndex(i);
				Intent intent = a.getIntent();
				newSpec.pushActivity(intent);

				/*provide savedData from previous activity*/

				if(a instanceof OnOrientationChangeListener)
				{
					Object savedData  = null;
					if(savedinstancesData != null)
						savedData = savedinstancesData.get(intent);
					((OnOrientationChangeListener)a).activityDidRestartWithNewOrientationChange(savedData);

				}
			}
			mTabHost.addTab(newSpec);
		}
	}




}