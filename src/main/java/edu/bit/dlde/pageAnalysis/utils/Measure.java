package edu.bit.dlde.pageAnalysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Measure {
	private double baseLength;
	private double comparedLength;
	private double cMatchedLength;
	private double bMatchedLength;

	public void compare(String fName1, String fName2) throws IOException {
		baseLength = 0;
		comparedLength = 0;
		cMatchedLength = 0;
		bMatchedLength = 0;
		BufferedReader br1 = new BufferedReader(
				new FileReader(new File(fName1)));
		BufferedReader br2 = new BufferedReader(
				new FileReader(new File(fName2)));

		String base = "";
		ArrayList<String> compared = new ArrayList<String>();
		String tmp;
		do {
			tmp = br1.readLine();
			if (tmp == null)
				break;
			tmp = tmp.trim().replaceAll("\n", "");
			if (!tmp.equals("\n") && !tmp.equals("")) {
				base += tmp;
				baseLength += tmp.length();
			}
		} while (true);
		// System.out.println("length of base file is " + baseLength);
		do {
			tmp = br2.readLine();
			if (tmp == null)
				break;
			tmp = tmp.trim();
			tmp = tmp.replaceAll("&(#[0-9]*|(a-z)*)?;", "'");
			if (!tmp.equals("\n") && !tmp.equals("")) {
				compared.add(tmp);
				comparedLength += tmp.length();
			}
		} while (true);
		br1.close();
		br2.close();
		// System.out.println("length of compared file is " + comparedLength);

		int i = 0;
		String baseModified = base.replaceAll("[^0-9a-z-A-Z]", "");
		for (i = 0; i < compared.size(); i++) {
			String str = compared.get(i);
			String strModified = str.replaceAll("[^0-9a-z-A-Z]", "");
			if (baseModified.toLowerCase().contains(strModified.toLowerCase())) {
//				System.out.print(str);
				cMatchedLength += str.length();
				bMatchedLength += str.length();
			}

		}
//		System.out.println("\n"+base);
		// System.out.println("length of b matched length is " + bMatchLength);
		// System.out.println("length of c matched length is " + cMatchLength);
	}

	private boolean isEqual(String str1, String str2) {
		// System.out.println("B---" + str1.replaceAll("[^0-9a-z-A-Z]", ""));
		// System.out.println("C---" + str2.replaceAll("[^0-9a-z-A-Z]", ""));
		return str1.replaceAll("[^0-9a-z-A-Z]", "").equals(
				str2.replaceAll("[^0-9a-z-A-Z]", ""));
	}

	public double getRecall() {
		return Math.min(1, bMatchedLength / Math.max(baseLength, 1));
	}

	public double getPrecision() {
		return cMatchedLength / Math.max(comparedLength, 1);
	}

	public static void main(String[] args) throws IOException {
		String f1 = "/home/lins/data/yahoo/base/yahoo_47";
		String f2 = "/home/lins/data/yahoo/rslt-a/yahoo_47";
		Measure m = new Measure();
		m.compare(f1, f2);
		System.out.println("precision: " + m.getPrecision());
		System.out.println("recall: " + m.getRecall());
	}
}
