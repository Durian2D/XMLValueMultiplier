package com.plumagames.xmlvaluemultiplier.lib.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 
 * @author Jeffrey Sancebuche
 *
 */
public class Config {

	private static Config mInstance = null;
	private Properties mProperties = null;
	private String mSrcDirectory = null;
	private String mDstDirectory = null;
	private String mConfigFilePath = "config.properties";
	private float mMultiplier = 1;
	private String mKeySrcDirectory = "srcdirectory";
	private String mKeyDstDirectory = "dstdirectory";
	private String mKeyMultiplier = "multiplier";
	
	public static Config getInstance() {
		if (mInstance == null) {
			mInstance = new Config();
		}
		return mInstance;
	}
	
	public Config() {
		
	}
	
	public void load() {
		mProperties = new Properties();
		try {
			mProperties.load(new FileInputStream(mConfigFilePath));
			mSrcDirectory = mProperties.getProperty(mKeySrcDirectory, null);
			mDstDirectory = mProperties.getProperty(mKeyDstDirectory, null);
			mMultiplier = Float.parseFloat(mProperties.getProperty(mKeyMultiplier, "0"));
			
			// check directories
			File srcDir = new File(mSrcDirectory);
			if (!srcDir.exists()) {
				// create
			}
			
			File dstDir = new File(mDstDirectory);
			if (!dstDir.exists()) {
				// create
			}
		} catch (Exception e) {
			setup();
		}
	}
	
	public void reset() {
		mInstance = new Config();
	}
	
	public void setup() {
		mProperties.put(mKeySrcDirectory, "/Users/jepoy/Documents/xml/");
		mProperties.put(mKeyDstDirectory, "/Users/jepoy/Documents/xml_out/");
		mProperties.put(mKeyMultiplier, 1);
		sync();
	}
	
	public boolean sync() {
		try {
			mProperties.store(new FileOutputStream(mConfigFilePath), null);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String getSourceDirectory() {
		return mSrcDirectory;
	}
	
	public String getDestinationDirectory() {
		return mDstDirectory;
	}

	public float getMultiplier() {
		return mMultiplier;
	}
}
