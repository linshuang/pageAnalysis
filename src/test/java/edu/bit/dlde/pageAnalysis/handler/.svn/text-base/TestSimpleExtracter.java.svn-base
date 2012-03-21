package edu.bit.dlde.pageAnalysis.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.pageAnalysis.SimpleHtmlExtracter;

public class TestSimpleExtracter extends TestCase {

	@Test
	public void testExtracter() {
		// String html="/home/coder/workspace/rorworkspace/pa/1234.htm";
		// String html="/home/coder/temp/1234.txt";
		String html = "/home/coder/workspace/J2eeWorkspace/pageAnalysis/src/test/resources/ykp1.htm";

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(html)));

			SimpleHtmlExtracter she = new SimpleHtmlExtracter();
			she.setReader(br);
			she.extract();
			String content = she.getContent();
			System.out.println("title:" + she.getTitle());
			System.out.println(content);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
