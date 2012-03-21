package edu.bit.dlde.pageAnalysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MeasureLegacy {
	private double baseLength;
	private double comparedLength;
	private double bMatchLength;
	private double cMatchLength;

	public void compare(String fName1, String fName2) throws IOException {
		baseLength = 0;
		comparedLength = 0;
		bMatchLength = 0;
		cMatchLength = 0;
		BufferedReader br1 = new BufferedReader(
				new FileReader(new File(fName1)));
		BufferedReader br2 = new BufferedReader(
				new FileReader(new File(fName2)));

		ArrayList<String> base = new ArrayList<String>();
		ArrayList<String> compared = new ArrayList<String>();
		String tmp;
		do {
			tmp = br1.readLine();
			if (tmp == null)
				break;
			tmp = tmp.trim();
			if (!tmp.equals("\n") && !tmp.equals("")) {
				base.add(tmp);
				baseLength += tmp.length();
			}
		} while (true);
//		System.out.println("length of base file is " + baseLength);
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
//		System.out.println("length of compared file is " + comparedLength);

		int i = 0, j = 0, k = 0;
		String current = "", previous = "";
		for (i = 0; i < compared.size(); i++) {
			String str = compared.get(i);
			for (j = k; j < base.size(); j++) {
				current = base.get(j);
				if (isEqual(current, str)) {
					cMatchLength += str.length();
					bMatchLength += current.length();
					k = j + 1;
					break;
				} else if (isEqual(current + previous, str)) {
					cMatchLength += str.length();
					bMatchLength += (current + previous).length();
					k = j + 1;
					break;
				}
				previous = current;
			}
		}
//		System.out.println("length of b matched length is " + bMatchLength);
//		System.out.println("length of c matched length is " + cMatchLength);
	}

	private boolean isEqual(String str1, String str2) {
//		System.out.println("B---" + str1.replaceAll("[^0-9a-z-A-Z]", ""));
//		System.out.println("C---" + str2.replaceAll("[^0-9a-z-A-Z]", ""));
		return str1.replaceAll("[^0-9a-z-A-Z]", "").equals(
				str2.replaceAll("[^0-9a-z-A-Z]", ""));
	}

	public double getPrecision() {
		return bMatchLength / Math.max(baseLength, 1);
	}

	public double getRecall() {
		return cMatchLength / Math.max(comparedLength, 1);
	}

	public static void main(String[] args) throws IOException {
		String f1 = "/home/lins/data/yahoo/base/yahoo_98";
		String f2 = "/home/lins/data/yahoo/result/yahoo_98";
		MeasureLegacy m = new MeasureLegacy();
		m.compare(f1, f2);
		System.out.println("precision: " + m.getPrecision());
		System.out.println("recall: " + m.getRecall());
	}
}
