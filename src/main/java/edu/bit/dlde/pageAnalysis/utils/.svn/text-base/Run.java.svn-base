package edu.bit.dlde.pageAnalysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.bit.dlde.pageAnalysis.BlockExtractor;

public class Run {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String[] all = { "yahoo", "cnn", "bbc", "nytimes" };
		String src = "/home/lins/data/*/page/";
		String desC = "/home/lins/data/*/rslt-c/";
		String desR = "/home/lins/data/*/rslt-r/";
		String desA = "/home/lins/data/*/rslt-a/";
		String desS = "/home/lins/data/*/rslt-s/";
		String base = "/home/lins/data/*/base/";
		if (args.length != 2)
			return;

		src = src.replaceAll("\\*", args[1]);
		desC = desC.replaceAll("\\*", args[1]);
		desR = desR.replaceAll("\\*", args[1]);
		desA = desA.replaceAll("\\*", args[1]);
		desS = desS.replaceAll("\\*", args[1]);
		base = base.replaceAll("\\*", args[1]);

		if (args[0].equals("-e")) {
			File dir = new File(src);
			String[] fName = dir.list();
			BufferedReader br = null;
			BlockExtractor be_r = new BlockExtractor(false, false, true);
			for (int i = 0; i < fName.length; i++) {

				br = new BufferedReader(
						new FileReader(new File(src + fName[i])));
				be_r.setReader(br);
				be_r.setUrl(src + fName[i]);
				be_r.extract();
				be_r.serialize(desR + fName[i]);
				br.close();
			}
			System.out.println("Extraction complete!");
		} else if (args[0].equals("-m")) {
			FileSimilarityComparor m = new FileSimilarityComparor();
			File file = new File(base);
			int i = 0;
			double[] rsltP = new double[100];
			double[] rsltR = new double[100];
			// double[] rsltRP = new double[100];
			// double[] rsltRR = new double[100];
			for (File f : file.listFiles()) {
				// m.compare(base + f.getName(), desR + f.getName());
				// rsltRP[i] = m.getPrecision();
				// rsltRR[i] = m.getRecall();
				m.compare(base + f.getName(), desS + f.getName());
				rsltP[i] = m.getPrecision();
				rsltR[i] = m.getRecall();
				System.out.println("p of " + f.getName() + " is " + rsltP[i]);
				System.out.println("r of " + f.getName() + " is " + rsltR[i]);
				i++;
			}
			// double p = getAverage(rsltRP);
			// double r = getAverage(rsltRR);
			double p = getAverage(rsltP);
			double r = getAverage(rsltR);
			System.out.println("p average is " + p);
			System.out.println("r average is " + r);
			System.out.println("f average is " + 2 * p * r / (p + r));
		} else if (args[0].equals("-a")) {
			Double t = new Double(args[1]);
			double p = 0, r = 0, fm = 0;
			for (String argv : all) {
				src = "/home/lins/data/*/page/";
				desC = "/home/lins/data/*/rslt-none/";
				desR = "/home/lins/data/*/rslt-r";
				base = "/home/lins/data/*/base/";

				src = src.replaceAll("\\*", argv);
				desC = desC.replaceAll("\\*", argv);
				desR = desR.replaceAll("\\*", argv);
				base = base.replaceAll("\\*", argv);
				File dir = new File(src);
				String[] fName = dir.list();
				BufferedReader br = null;
				BlockExtractor be_r = new BlockExtractor(false, false, true);
				be_r.setT(t);
				for (int i = 0; i < fName.length; i++) {
					br = new BufferedReader(new FileReader(new File(src
							+ fName[i])));
					be_r.setReader(br);
					be_r.setUrl(src + fName[i]);
					be_r.extract();
					be_r.serialize(desR + t + "/" + fName[i]);
					
					br.close();
				} System.out.println(desR + t + "/");
				System.out.println("Extraction complete!");
//				FileSimilarityComparor m = new FileSimilarityComparor();
//				File file = new File(base);
//				int i = 0;
//				double[] rsltRP = new double[100];
//				double[] rsltRR = new double[100];
//				for (File f : file.listFiles()) {
//					m.compare(base + f.getName(), desR+ t + "/" + f.getName());
//					rsltRP[i] = m.getPrecision();
//					rsltRR[i] = m.getRecall();
//					i++;
//				}
//				p += getAverage(rsltRP);
//				r += getAverage(rsltRR);
//				fm += 2 * getAverage(rsltRP) * getAverage(rsltRR)
//						/ (getAverage(rsltRP) + getAverage(rsltRR));
			}
			System.out.format("T %f p average is %f.\n", t, p / all.length);
			System.out.format("T %f r average is %f.\n", t, r / all.length);
			System.out.format("T %f f average is %f.\n", t, fm / all.length);
		}

	}

	private static double getAverage(double[] numb) {
		double sum = 0;
		for (double d : numb) {
			sum += d;
		}
		return sum / numb.length;
	}

}
