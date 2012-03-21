package edu.bit.dlde.pageAnalysis.utils;

import java.util.Arrays;
import java.util.List;

import org.jdom.Element;
import org.jdom.Text;

public class TreeEditDistance {
	private List<String> blk = Arrays.asList("div", "center", "p", "pre",
			"address", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "ul",
			"ol", "dir", "menu", "dl", "dt", "dd", "li");
	private double total;
	private double ed;
	private boolean goon;

	public TreeEditDistance(Element e1, Element e2) {
		total = Integer.parseInt(e1.getAttributeValue("tnum4lins"));
		ed = 0;
		goon = true;
		ed = getED(e1, e2);
	}

	private int getED(Element e1, Element e2) {
		int i = Integer.parseInt(e1.getAttributeValue("dnum4lins"));
		int j = Integer.parseInt(e2.getAttributeValue("dnum4lins"));
		int[][] matrix = new int[i + 1][j + 1];
		matrix[0][0] = 0;
		int m = 1;
		// initial
		for (Object o : e1.getContent()) {
			if (o instanceof Element) {
				Element e = (Element) o;
				if (blk.contains(e.getName())) {
					matrix[0][m] = matrix[0][m - 1]
							+ Integer
									.parseInt(e.getAttributeValue("tnum4lins"));
					m++;
				} else {
					matrix[0][m] = matrix[0][m - 1] + 1;
					m++;
				}
			} else if (o instanceof Text) {
				if (!((Text) o).getText().trim().equals("")) {
					matrix[0][m] = matrix[0][m - 1] + 1;
					m++;
				}
			}
		}
		m = 1;
		for (Object o : e2.getContent()) {
			if (o instanceof Element) {
				Element e = (Element) o;
				if (blk.contains(e.getName())) {
					matrix[m][0] = matrix[m - 1][0]
							+ Integer
									.parseInt(e.getAttributeValue("tnum4lins"));
					m++;
				} else {
					matrix[m][0] = matrix[m - 1][0] + 1;
					m++;
				}
			} else if (o instanceof Text) {
				if (!((Text) o).getText().trim().equals("")) {
					matrix[m][0] = matrix[m - 1][0] + 1;
					m++;
				}
			}
		}
		return 0;
	}

	public boolean isSimilar() {
		return ed / total < 0.2 ? true : false;
	}
}
