package bit.lin.extractor.test.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import bit.lin.extractor.util.CustomizedCleaner;

public class CleanerTest {
	public static void cleanToContent(){
		CustomizedCleaner cleaner = new CustomizedCleaner();
		List<String> list = cleaner
				.cleanHtml("/home/lins/document/2011-09-4th-week/data/sina_1");
		for(int i = 0; i < list.size(); i++){
			System.out.println("line "+i+": "+list.get(i));
		}
	}
	
	public static void cleanToHtml(){
		CustomizedCleaner cleaner = new CustomizedCleaner();
//		cleaner.formatHtml("/home/lins/document/2011-09-4th-week/data/sina_2");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CleanerTest.cleanToHtml();
	}

}
