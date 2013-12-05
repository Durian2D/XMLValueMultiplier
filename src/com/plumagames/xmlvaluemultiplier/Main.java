package com.plumagames.xmlvaluemultiplier;

import java.io.File;

import com.plumagames.xmlvaluemultiplier.lib.xml.Config;
import com.plumagames.xmlvaluemultiplier.lib.xml.XMLValueMultiplier;

public class Main {

	public static void main(String args[]) {
		System.out.println("asdasd");
		Config.getInstance().load();
		try {
			String filepath = "/Users/jepoy/Documents/xml/toolbar_dimens.xml";
			File file = new File(filepath);
			boolean isFileExist = file.exists();
			System.out.println("exist = " + isFileExist);
			
			String srcDir = Config.getInstance().getSourceDirectory();
			String dstDir = Config.getInstance().getDestinationDirectory();
			float multiplier = Config.getInstance().getMultiplier();
			XMLValueMultiplier.multiplyAll(new File(srcDir), new File(dstDir), multiplier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Done!!!");
	}
}
