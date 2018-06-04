package com.mytaxi.android_demo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValue {
	//get property value
	public String getPropertyValue(String key, Context context) throws IOException {
		Properties prop = new Properties();
		String Value;
		InputStream input = null;

			AssetManager assetManager = context.getAssets();
			input = assetManager.open("demo.properties");
			// load a properties file
			prop.load(input);
			Value = prop.getProperty(key);
			// return property value
			return Value;

	}

}
