package edu.bit.dlde.pageAnalysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 以单词为最小单元求取longest common sequence
 */
public class FileSimilarityComparor {
	private double f1Length;
	private double f2Length;
	private double matchedLength;

	/**
	 * @param fName1
	 *            基准文件名
	 * @param fName2
	 *            待比较文件名
	 */
	public void compare(String fName1, String fName2) throws IOException {
		f1Length = 0;
		f2Length = 0;
		matchedLength = 0;

		BufferedReader br1 = new BufferedReader(
				new FileReader(new File(fName1)));
		BufferedReader br2 = new BufferedReader(
				new FileReader(new File(fName2)));

		// get the length
		ArrayList<String> f1 = new ArrayList<String>();
		ArrayList<String> f2 = new ArrayList<String>();
		String tmp;
		do {
			tmp = br1.readLine();
			if (tmp == null)
				break;
			tmp = tmp.trim().replaceAll("\n", "");
			if (!tmp.equals("\n") && !tmp.equals("")) {
				f1Length += tmp.length();
				f1.addAll(Arrays.asList(tmp.toLowerCase().split(" ")));
			}
		} while (true);
		// System.out.println("length of base file is " + baseLength);
		do {
			tmp = br2.readLine();
			if (tmp == null)
				break;
			tmp = tmp.trim().replaceAll("&(#[0-9]*|(a-z)*)?;", "'");
			if (!tmp.equals("\n") && !tmp.equals("")) {
				f2Length += tmp.length();
				f2.addAll(Arrays.asList(tmp.toLowerCase().split(" |\\s")));
			}
		} while (true);
		br1.close();
		br2.close();
		// System.out.println("length of compared file is " + comparedLength);
		// System.out.format("%d %d", f1.size(), f2.size());
		int[][] m = new int[f1.size() + 1][f2.size() + 1];
		for (int i = 0; i < f1.size() + 1; i++) {
			m[i][0] = 0;
		}
		for (int j = 0; j < f2.size() + 1; j++) {
			m[0][j] = 0;
		}

		// i 对于数组里面的i-1位置，同理j
		for (int i = 1; i < f1.size() + 1; i++) {
			for (int j = 1; j < f2.size() + 1; j++) {
				m[i][j] = max(
						m[i - 1][j - 1] + isEqual(f1.get(i - 1), f2.get(j - 1)),
						m[i - 1][j], m[i][j - 1]);
			}
		}
		matchedLength = m[f1.size()][f2.size()];
	}

	private int max(int i, int j, int k) {
		return Math.max(Math.max(i, j), k);
	}

	private int isEqual(String str1, String str2) {
		return str1.replaceAll("[^a-z1-9]", "").equals(
				str2.replaceAll("[^a-z1-9]", "")) ? Math.max(str1.length(),
				str2.length()) + 1 : 0;
	}

	public double getRecall() {
		return Math.min(1, matchedLength / Math.max(f1Length, 1));
	}

	public double getPrecision() {
		return Math.min(1, matchedLength / Math.max(f2Length, 1));
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileSimilarityComparor fsc = new FileSimilarityComparor();
		fsc.compare("/home/lins/data/yahoo/base/yahoo_54",
				"/home/lins/data/yahoo/rslt-r/yahoo_54");
		System.out
				.format("p: %f; r: %f\n", fsc.getPrecision(), fsc.getRecall());
	}

}
