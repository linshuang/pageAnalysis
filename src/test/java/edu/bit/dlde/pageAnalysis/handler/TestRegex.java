package edu.bit.dlde.pageAnalysis.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

import edu.bit.dlde.pageAnalysis.handler.impl.FixHtmlErrorHandler;

public class TestRegex extends TestCase{

	@Test
	public void testRegex() throws FileNotFoundException{
		Pattern pattern=null;
		Matcher matcher=null;
		
		String html="/home/coder/workspace/rorworkspace/pa/1234.htm";
		
		BufferedReader br;
		br = new BufferedReader(new FileReader(new File(html)));
		
		FixHtmlErrorHandler fh=new FixHtmlErrorHandler();
		String total=fh.readFromReader(br);
		
		System.out.println(total);
		
		String titleRegex="<(title|TITLE)>.*?</(title|TITLE)>";
//		String titleRegex="<.*?>";
		
		pattern=Pattern.compile(titleRegex);
		matcher=pattern.matcher(total);
		int count=matcher.groupCount();
		
		if(count!=0){
			System.out.println(count);
			if(matcher.find()){
				String title=matcher.group();
				System.out.println("Title:"+title);
			}
		}else{
			System.out.println("no title");
		}
	}
	
	
}
