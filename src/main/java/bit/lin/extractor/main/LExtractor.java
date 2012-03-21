package bit.lin.extractor.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class LExtractor {
	static Logger logger = Logger.getLogger(LExtractor.class);

	final List<String> rubbishTag = Arrays.asList("SCRIPT", "STYLE", "IMG",
			"INPUT", "BUTTON");
	final List<String> blockTag = Arrays.asList("DIV", "P");

	private ArrayList<String> format(String str) {
		ArrayList<String> strs = new ArrayList<String>();
		str = str.replaceAll("\n", "");
		str = str.replaceAll("<(script|style|input|img).*?>.*?</.*?>", "");
		str = str.replaceAll("<p.*?>", "\n@<p>\n@");
		str = str.replaceAll("</p>", "\n@</p>\n@");
		str = str.replaceAll("<div.*?>", "\n@<div>\n@");
		str = str.replaceAll("</div>", "\n@</div>\n@");
		str = str.replaceAll("<li.*?>", "\n@<li>\n@");
		str = str.replaceAll("</li>", "\n@</li>\n@");
		str = str.replaceAll("<(h1|h2|h3|h4).*?>", "\n@<h1>\n@");
		str = str.replaceAll("</(h1|h2|h3|h4)>", "\n@</h1>\n@");
		System.out.println(str);
		return strs;
	}

	public void getMainContent(String fileName) {
		StringBuilder total = new StringBuilder();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String str = br.readLine();
			while (str != null) {
				total.append(str);
				str = br.readLine();
			}
			ArrayList<String> strs = format(total.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBR(br);
		}
	}

	private void closeBR(BufferedReader br) {
		if (br != null)
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LExtractor etr = new LExtractor();
		etr.getMainContent("src/test/resources/yahoo");
	}

}
