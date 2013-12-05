package com.plumagames.xmlvaluemultiplier.lib.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Jeffrey Sancebuche
 *
 */
public class XMLValueMultiplier {
	
	/**
	 * 
	 * @param srcDir
	 * @param dstDir
	 * @param multiplier
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void multiplyAll(File srcDir, File dstDir, float multiplier) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		File[] srcFiles = srcDir.listFiles();
		for (File srcFile : srcFiles) {
			String fileName = srcFile.getName();
			if (fileName.contains(".xml")) {
				File dstFile = new File(dstDir.toString() + File.separatorChar + fileName);
				multiply(srcFile, dstFile, multiplier);
			}
		}
	}
	
	/**
	 * 
	 * @param 
	 * @param multiplier
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void multiply(File file, float multiplier) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		multiply(file, file, multiplier);
	}
	
	/**
	 * 
	 * @param srcFile
	 * @param multiplier
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void multiply(File srcFile, File dstFile, float multiplier) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(srcFile);
		
		// get all nodes
		NodeList childNodes = doc.getElementsByTagName("dimen");
		
		// iterate to all of the nodes
		int nodesCount = childNodes.getLength();
		for (int i = 0; i < nodesCount; i++) {
			Node childNode = childNodes.item(i);

			// get the value of the node then multiply
			String childValue = childNode.getTextContent();
			String realChildValue = getRealValue(childValue, multiplier);
			
			if (!realChildValue.isEmpty()) {
				childNode.setTextContent(realChildValue);
			}
		}
		
		// write
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(dstFile);
		transformer.transform(source, result);
	}
	
	private static String[] mUnits = {"dp", "sp", "px"};
	
	private static String getRealValue(String stringValue, float multiplier) {
		String numberString = stringValue;
		for (String unit : mUnits) {
			numberString = numberString.replaceAll(unit, "");
		}
		String unit = stringValue.replace(numberString, "");
		
		int value = Math.round(getValue(numberString) * multiplier);
		
		String newValue = String.format("%d%s", value, unit);
		return newValue;
	}
	
	private static boolean isInteger(String numberString) {
		try {
			Integer.parseInt(numberString);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
	
	private static boolean isFloat(String numberString) {
		try {
			Float.parseFloat(numberString);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
	
	private static int getValue(String numberString) {
		int value = 0;
		try {
			boolean isInteger = isInteger(numberString);
			boolean isFloat = isFloat(numberString);

			if (isInteger) {
				value = Integer.parseInt(numberString);
			} else if (isFloat) {
				float floatValue = Float.parseFloat(numberString);
				value = Math.round(floatValue);
			}
		} catch (Exception e) {
			
		}
		return value;
	}
	
}
