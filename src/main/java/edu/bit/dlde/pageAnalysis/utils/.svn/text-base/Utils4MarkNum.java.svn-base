package edu.bit.dlde.pageAnalysis.utils;

import java.util.Arrays;
import java.util.List;

import org.jdom.Element;
import org.jdom.Text;

public class Utils4MarkNum {
	private static List<String> blk = Arrays.asList("div", "center", "p",
			"pre", "address", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote",
			"ul", "ol", "dir", "menu", "dl", "dt", "dd", "li");

	public static int mark(Element rootElement) {
		int i = 1;
		int j = 0;
		for (Object child : rootElement.getContent()) {
			if (child instanceof Element) {
				Element e = (Element) child;
				if (blk.contains(e.getName()))
					i += mark(e);
				else
					i++;
				j++;
			} else if (child instanceof Text) {
				if (!((Text) child).getText().trim().equals("")) {
					i++;
					j++;
				}
			}
		}
		rootElement.setAttribute("tnum4lins", Integer.toString(i));
		rootElement.setAttribute("dnum4lins", Integer.toString(j));
		return i;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
