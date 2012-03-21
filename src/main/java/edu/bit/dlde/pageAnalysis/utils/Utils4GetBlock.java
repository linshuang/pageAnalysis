package edu.bit.dlde.pageAnalysis.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

public class Utils4GetBlock {
	private static Element blocks;
	private static List<String> ignore = Arrays.asList("header", "footer",
			"head", "script", "style", "noscript");
	// li是个列外
	private static List<String> blk = Arrays.asList("div", "center", "p",
			"pre", "address", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote",
			"ul", "ol", "dir", "menu", "dl", "dt", "dd");

	public static Element getBlock(Element e) {
		blocks = new Element("blocks");
		Iterator itr = e.getChildren().iterator();
		while (itr.hasNext()) {
			findBlock((Element) itr.next());
		}
		return blocks;
	}

	private static void findBlock(Element e) {
		boolean isPrevQ = true;
		int j = 0;
		/*
		 * 遍历e的所有子content
		 */
		for (int i = 0; i < e.getContentSize(); i++) {
			Content c = e.getContent(i);
			//
			if (c instanceof Element) {
				Element elt = ((Element) c);
				String name = elt.getName();

				if ((blk.contains(name))) {
					// int k = isRepeated(e, i);
					// if (k != -1) {
					// add2Block(j, k, e);
					// j = k + 1;
					// i = k + 1;
					// isPrevQ = false;
					// }
					if (!isQ(elt, isPrevQ)) {
						// add to blocks
						add2Block(j, i, e);
						// iterate
						findBlock(elt);
						// reset j
						j = i + 1;
					} else {
						isPrevQ = true;
					}

				} else if (!ignore.contains(name) && isPrevQ) {
					add2Block(j, i, e);
					j = i;
					isPrevQ = false;
				}

			} else if (c instanceof Text && !c.getValue().trim().equals("")
					&& isPrevQ) {
				add2Block(j, i, e);
				j = i;
				isPrevQ = false;
			}
		}
		add2Block(j, e.getContentSize(), e);
	}

	private static void add2Block(int j, int i, Element e) {
		if (j == e.getContentSize() || j > i)
			return;

		Element tmp = new Element(e.getName());
		Iterator itr = e.getAttributes().iterator();
		while (itr.hasNext()) {
			tmp.setAttribute((Attribute) ((Attribute) itr.next()).clone());
		}
		boolean flag = false;
		for (int k = j; k < i; k++) {
			Content ctt = e.getContent(k);
			if (ctt instanceof Text && "".equals(((Text) ctt).getTextTrim()))
				continue;
			tmp.addContent((Content) ctt.clone());
			flag = true;
		}
		if (flag)
			blocks.addContent(tmp);
	}

	private static int isRepeated(Element elt, int i) {
		boolean flag = false;
		int j = i + 1, index = -1;
		for (; j < elt.getContentSize(); j++) {
			if (isSimilar(elt.getContent(i), elt.getContent(j))) {
				flag = true;
				for (int k = 1; k <= j - i && j + k < elt.getContentSize(); k++) {
					if (!isSimilar(elt.getContent(i + k), elt.getContent(j + k)))
						flag = false;
				}
				if (flag)
					index = 2 * j - i - 1;
			}
		}
		return index;
	}

	private static boolean isSimilar(Content content1, Content content2) {
		if (!(content1 instanceof Element && content2 instanceof Element))
			return false;
		Element e1 = (Element) content1;
		Element e2 = (Element) content2;
		String className1 = e1.getAttributeValue("class");
		String id1 = e1.getAttributeValue("id");
		String className2 = e2.getAttributeValue("class");
		String id2 = e2.getAttributeValue("id");
		if (!e1.getName().equals(e2.getName()))
			return false;
		if (className1 != null && !className1.equals(className2))
			return false;
		if (className2 != null && !className2.equals(className1))
			return false;
		if (id1 != null && !id1.equals(id2))
			return false;
		if (id2 != null && !id2.equals(id1))
			return false;

		TreeEditDistance ted = new TreeEditDistance(e1, e2);
		return ted.isSimilar();
	}

	private static boolean isQ(Element elt, boolean isPrevQ) {
		boolean f1 = false;
		int i = 0;
		if (isPrevQ) {
			for (i = 0; i < elt.getContentSize(); i++) {
				Content c = elt.getContent(i);
				if (c instanceof Element) {
					break;
				} else if (c instanceof Text && !c.getValue().equals(""))
					f1 = true;
			}
		} else {
			for (i = 0; i < elt.getContentSize(); i++) {
				Content c = elt.getContent(i);
				if (c instanceof Element) {
					Element e = (Element) c;
					if (blk.contains(e.getName()))
						break;
					else if (!e.getValue().equals(""))
						f1 = true;
				} else if (c instanceof Text && !c.getValue().equals(""))
					f1 = true;
			}
		}
		return i == elt.getContentSize() && f1 ? true : false;
	}

	// private static boolean hasNoBLE(Element elt) {
	// boolean f1 = false;
	// int i;
	// for (i = 0; i < elt.getContentSize(); i++) {
	// Content c = elt.getContent(i);
	// if (c instanceof Element) {
	// Element e = (Element) c;
	// if (blk.contains(e.getName()))
	// break;
	// else if (!e.getValue().equals(""))
	// f1 = true;
	// } else if (c instanceof Text && !c.getValue().equals(""))
	// f1 = true;
	// }
	// return i == elt.getContentSize() && f1 ? true : false;
	// }
}
