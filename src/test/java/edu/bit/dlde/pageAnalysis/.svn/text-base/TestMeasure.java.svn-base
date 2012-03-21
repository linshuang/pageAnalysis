package edu.bit.dlde.pageAnalysis;

import java.io.File;
import java.io.IOException;

import edu.bit.dlde.pageAnalysis.utils.Measure;
import edu.bit.dlde.pageAnalysis.utils.MeasureLegacy;
import junit.framework.TestCase;

public class TestMeasure extends TestCase {
	String yahooBase = "/home/lins/data/yahoo/base/";
	String yahooRsltN = "/home/lins/data/yahoo/rslt-none/";
	String yahooRsltR = "/home/lins/data/yahoo/rslt-r/";
	String bbcBase = "/home/lins/data/bbc/base/";
	String bbcRsltN = "/home/lins/data/bbc/rslt-none/";
	String bbcRsltR = "/home/lins/data/bbc/rslt-r/";
	String cnnBase = "/home/lins/data/cnn/base/";
	String cnnRsltN = "/home/lins/data/cnn/rslt-none/";
	String cnnRsltR = "/home/lins/data/cnn/rslt-r/";
	String aolBase = "/home/lins/data/aol/base/";
	String aolRsltN = "/home/lins/data/aol/rslt-none/";
	String aolRsltR = "/home/lins/data/aol/rslt-r/";

	public void testBlockExtractor() throws IOException {
		Measure m = new Measure();
		File y = new File(yahooBase);
		File b = new File(bbcBase);
		File c = new File(cnnBase);
		File a = new File(aolBase);
		int i = 0;
		double[] rsltNP = new double[100];
		double[] rsltNR = new double[100];
		double[] rsltRP = new double[100];
		double[] rsltRR = new double[100];
//		for (File f : y.listFiles()) {
//			m.compare(yahooBase + f.getName(), yahooRsltR + f.getName());
//			rsltRP[i] = m.getPrecision();
//			rsltRR[i] = m.getRecall();
//			m.compare(yahooBase + f.getName(), yahooRsltN + f.getName());
//			rsltNP[i] = m.getPrecision();
//			rsltNR[i] = m.getRecall();
//			// System.out.println("p of " + f.getName() + " is " + rsltRP[i]);
//			// System.out.println("r of "+f.getName()+" is "+rsltR[i]);
//			i++;
//		}
//		for (File f : c.listFiles()) {
//			m.compare(cnnBase + f.getName(), cnnRsltR + f.getName());
//			rsltRP[i] = m.getPrecision();
//			rsltRR[i] = m.getRecall();
//			m.compare(cnnBase + f.getName(), cnnRsltN + f.getName());
//			rsltNP[i] = m.getPrecision();
//			rsltNR[i] = m.getRecall();
//			 System.out.println("p of " + f.getName() + " is " + rsltRP[i]);
//			// System.out.println("r of "+f.getName()+" is "+rsltR[i]);
//			i++;
//		}
		for (File f : a.listFiles()) {
			m.compare(aolBase + f.getName(), aolRsltR + f.getName());
			rsltRP[i] = m.getPrecision();
			rsltRR[i] = m.getRecall();
			m.compare(aolBase + f.getName(), aolRsltN + f.getName());
			rsltNP[i] = m.getPrecision();
			rsltNR[i] = m.getRecall();
			 System.out.println("p of " + f.getName() + " is " + rsltRP[i]);
			// System.out.println("r of "+f.getName()+" is "+rsltR[i]);
			i++;
		}
		// for (File f : b.listFiles()) {
		// m.compare(bbcBase + f.getName(), bbcRsltR + f.getName());
		// rsltRP[i] = m.getPrecision();
		// rsltRR[i] = m.getRecall();
		// m.compare(bbcBase + f.getName(), bbcRsltN + f.getName());
		// rsltNP[i] = m.getPrecision();
		// rsltNR[i] = m.getRecall();
		// // System.out.println("p of " + f.getName() + " is " + rsltRP[i]);
		// // System.out.println("r of "+f.getName()+" is "+rsltR[i]);
		// i++;
		// }
		System.out.println("R p average is " + getAverage(rsltRP));
		System.out.println("R r average is " + getAverage(rsltRR));
		System.out.println("N p average is " + getAverage(rsltNP));
		System.out.println("N r average is " + getAverage(rsltNR));
	}

	private double getAverage(double[] num) {
		double sum = 0;
		for (double d : num) {
			sum += d;
		}
		return sum / num.length;
	}
}
