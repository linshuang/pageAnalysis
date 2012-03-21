package edu.bit.dlde.pageAnalysis.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import edu.bit.dlde.pageAnalysis.handler.impl.FixHtmlErrorHandler;

public class TestFixHtmlErrorHandler extends TestCase{
	
	@Test
	public void testFixHtml(){
		FixHtmlErrorHandler preTreatHandler=new FixHtmlErrorHandler();
		String html="/home/coder/workspace/rorworkspace/pa/1234.htm";
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(html)));
			String totalHtml;
			totalHtml=preTreatHandler.readFromReader(br);
			totalHtml=preTreatHandler.removeTags(html);
			
			List<String> list=preTreatHandler.preTreat(totalHtml);
			
			for(String tmp:list){
				System.out.println(tmp);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRemoveTags(){
		FixHtmlErrorHandler fh=new FixHtmlErrorHandler();
		String html="/home/coder/workspace/rorworkspace/pa/1234.htm";
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(html)));
			String total=fh.removeTags(fh.readFromReader(br));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testStringUtils(){
		String line="<hello>world</hello>";
		int x=StringUtils.countMatches(line, "<");
		assertEquals(2,x);
	}

}
