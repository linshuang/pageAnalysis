package edu.bit.dlde.pageAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

public class TestBlockExtractor extends TestCase {
	String html = "/home/lins/data/yahoo/page/yahoo_5";
	String yahooSrc = "/home/lins/data/yahoo/page/";
	String yahooDesN = "/home/lins/data/yahoo/rslt-none/";
	String yahooDesR = "/home/lins/data/yahoo/rslt-r/";
	String bbcSrc = "/home/lins/data/bbc/page/";
	String bbcDesN = "/home/lins/data/bbc/rslt-none/";
	String bbcDesR = "/home/lins/data/bbc/rslt-r/";
	String cnnSrc = "/home/lins/data/cnn/page/";
	String cnnDesN = "/home/lins/data/cnn/rslt-none/";
	String cnnDesR = "/home/lins/data/cnn/rslt-r/";
	String aolSrc = "/home/lins/data/aol/page/";
	String aolDesN = "/home/lins/data/aol/rslt-none/";
	String aolDesR = "/home/lins/data/aol/rslt-r/";
	String tmpCase = "/home/lins/data/tmp";

	 public void testRslt() {
	 BufferedReader br;
	 try {
	 br = new BufferedReader(new FileReader(new File(html)));
	
	 BlockExtractor be = new BlockExtractor();
	 be.setReader(br);
	 be.setUrl("test 2/1");
	 be.extract();
	
	 // System.out.println("TITLE:\n" + be.getTitle());
	 // System.out.println("CONTENT:\n" + be.getContent());
	 System.out.println("TOTAL:\n" + be.total);
	 } catch (FileNotFoundException e) {
	 e.printStackTrace();
	 }
	 }

//	public void testYSerialize() throws IOException {
//		File dir = new File(yahooSrc);
//		String[] fName = dir.list();
//		BufferedReader br = null;
//		BlockExtractor be_n = new BlockExtractor(false, false, false);
//		BlockExtractor be_r = new BlockExtractor(false, false, true);
//		for (int i = 0; i < fName.length; i++) {
//			try {
//				br = new BufferedReader(new FileReader(new File(yahooSrc
//						+ fName[i])));
//				be_n.setReader(br);
//				be_n.setUrl(yahooSrc + fName[i]);
//				be_n.extract();
//				be_n.serialize(yahooDesN + fName[i]);
//				
//				br = new BufferedReader(new FileReader(new File(yahooSrc
//						+ fName[i])));
//				be_r.setReader(br);
//				be_r.setUrl(yahooSrc + fName[i]);
//				be_r.extract();
//				be_r.serialize(yahooDesR + fName[i]);
//				br.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public void testBSerialize() throws IOException {
//		File dir = new File(bbcSrc);
//		String[] fName = dir.list();
//		BufferedReader br = null;
//		BlockExtractor be_n = new BlockExtractor(false, false, false);
//		BlockExtractor be_r = new BlockExtractor(false, false, true);
//		for (int i = 0; i < fName.length; i++) {
//			try {
//				br = new BufferedReader(new FileReader(new File(bbcSrc
//						+ fName[i])));
//				be_n.setReader(br);
//				be_n.setUrl(bbcSrc + fName[i]);
//				be_n.extract();
//				be_n.serialize(bbcDesN + fName[i]);
//				
//				br = new BufferedReader(new FileReader(new File(bbcSrc
//						+ fName[i])));
//				be_r.setReader(br);
//				be_r.setUrl(bbcSrc + fName[i]);
//				be_r.extract();
//				be_r.serialize(bbcDesR + fName[i]);
//				br.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public void testCNNSerialize() throws IOException {
//		File dir = new File(cnnSrc);
//		String[] fName = dir.list();
//		BufferedReader br = null;
//		BlockExtractor be_n = new BlockExtractor(false, false, false);
//		BlockExtractor be_r = new BlockExtractor(false, false, true);
//		for (int i = 0; i < fName.length; i++) {
//			try {
//				br = new BufferedReader(new FileReader(new File(cnnSrc
//						+ fName[i])));
//				be_n.setReader(br);
//				be_n.setUrl(cnnSrc + fName[i]);
//				be_n.extract();
//				be_n.serialize(cnnDesN + fName[i]);
//				
//				br = new BufferedReader(new FileReader(new File(cnnSrc
//						+ fName[i])));
//				be_r.setReader(br);
//				be_r.setUrl(cnnSrc + fName[i]);
//				be_r.extract();
//				be_r.serialize(cnnDesR + fName[i]);
//				br.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public void testAOLSerialize() throws IOException {
//		File dir = new File(aolSrc);
//		String[] fName = dir.list();
//		BufferedReader br = null;
//		BlockExtractor be_n = new BlockExtractor(false, false, false);
//		BlockExtractor be_r = new BlockExtractor(false, false, true);
//		for (int i = 0; i < fName.length; i++) {
//			try {
//				br = new BufferedReader(new FileReader(new File(aolSrc
//						+ fName[i])));
//				be_n.setReader(br);
//				be_n.setUrl(aolSrc + fName[i]);
//				be_n.extract();
//				be_n.serialize(aolDesN + fName[i]);
//				
//				br = new BufferedReader(new FileReader(new File(aolSrc
//						+ fName[i])));
//				be_r.setReader(br);
//				be_r.setUrl(aolSrc + fName[i]);
//				be_r.extract();
//				be_r.serialize(aolDesR + fName[i]);
//				br.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	// public void testReg(){
	// String str =
	// "[ [[&#39;Dekraai&#39;, 10]],&#39;http://news.yahoo.com/photos/mourners-remember-seal-beach-shooting-victims-1318620627-slideshow/&#39;,&#39;Click image to see more photos&#39;,&#39;http://l.yimg.com/a/p/us/news/editorial/3/2c/32c8e92d889f42edb719cb5257afdf4e.jpeg&#39;,&#39;461&#39;,&#39; &#39;, &#39;Reuters/Lori Shepler&#39;,],[    [[&#39;iPhone 4SXXXXXXX&#39;, 11]], &#39;http://news.yahoo.com/photos/thousands-line-up-for-apple-s-iphone-4s-1318602841-slideshow/&#39;,       &#39;Click image to see more photos&#39;,       &#39;http://l.yimg.com/a/p/us/news/editorial/f/4f/f4f15e8f6f323f5386dc9fdf9e15dca8.jpeg&#39;,       &#39;500&#39;,       &#39; &#39;,      &#39;AP/Kirsty Wigglesworth&#39;,]]";
	// System.out.println(str.matches("^\\[.*\\]$"));
	// }
}
