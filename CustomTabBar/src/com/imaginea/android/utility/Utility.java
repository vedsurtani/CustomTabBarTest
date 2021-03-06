package com.imaginea.android.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Utility {

	private static final String LOG_TAG = "UTILITY";
	
	
	
	public static String StringFromResource(int resourceId,Context ctx)
	{
		try{
		return ctx.getResources().getString(resourceId);
		}
		catch(Exception ex)
		{
			Log.d(LOG_TAG, "String with id "+resourceId +" was not found");
		}
		return null;
	}
	
	public static Drawable  drawableFromResource(int resourceId,Context ctx)
	{
		try{
		return ctx.getResources().getDrawable(resourceId);
		}
		catch(Exception ex)
		{
			Log.d(LOG_TAG, "String with id "+resourceId +" was not found");
		}
		return null;
	}
}
