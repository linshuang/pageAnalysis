package bit.lin.extractor.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The easy way to extract text from arbitrary html 算法：内容/描述内容所需要的字节数
 */
public class LineDensityBasedExtractor {
	private final double THRESHOLD = 0.5;
	public String ttl = "";

	public void getMainContent(String fileName) {
		String total = "";

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String str = br.readLine();
			while (str != null) {
				total = total + str + "\n";
				str = br.readLine();
			}

			total = doClean(total);
			doCaculate(total);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBR(br);
		}
	}

	private void doCaculate(String total) {
		String[] lines = total.split("\n");
		for (String line : lines) {
			double i = line.trim().length();
			double j = getText(line).trim().length();

			if (i != 0 && j / i >= THRESHOLD) {
				ttl += getText(line);
				ttl += "\n";
			}
		}
	}

	private String getText(String line) {
		return Pattern.compile("<.*?>").matcher(line).replaceAll("");
	}

	private String doClean(String input) {
		return Pattern.compile("<(script|style|input).*?>.*?</.*?>")
				.matcher(input.toLowerCase()).replaceAll("");
	}

	private void closeBR(BufferedReader br) {
		if (br != null)
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
