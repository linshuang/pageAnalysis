package bit.lin.block;

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
			"head", "script");
	private static List<String> blk = Arrays.asList("div", "p", "h1", "h2",
			"h3", "h4");

	public static Element findBlock(Element e) {
		blocks = new Element("blocks");
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			findBlock((Content) itr.next());
		}
		return blocks;
	}

	private static void findBlock(Content c) {
		if (c instanceof Element) {
			Element e = ((Element) c);
			String name = e.getName();
			if (ignore.contains(name))
				return;
			else {
				if (blk.contains(name)) {
					if (matchDef(e)) {
						blocks.addContent((Content) c.clone());
					} else {
						Iterator itr = e.getContent().iterator();
						while (itr.hasNext()) {
							findBlock((Content) itr.next());
						}
					}
				} else {
					if (!e.getText().trim().equals(""))
						blocks.addContent(dressup(c));
				}
			}
		} else if (c instanceof Text) {
			if (!c.getValue().trim().equals(""))
				blocks.addContent(dressup(c));
		}
	}

	private static Content dressup(Content c) {
		Element p = (Element) c.getParent();
		Element e = new Element(p.getName());

		Iterator itr = p.getAttributes().iterator();
		while (itr.hasNext()) {
			e.setAttribute((Attribute) ((Attribute) itr.next()).clone());
		}

		e.addContent((Content) c.clone());
		return e;
	}

	private static boolean matchDef(Element c) {
		boolean rslt = false;
		int i = 0, j = 0, n = 0;
		String style = c.getAttributeValue("class");

		Iterator itr1 = c.getChildren().iterator();
		while (itr1.hasNext()) {
			n++;
			Element e = (Element) itr1.next();
			if (blk.contains(e.getName())) {
				i++;
				String tmp = e.getAttributeValue("class");
				if (tmp == null || tmp.equals(style))
					j++;
			}
		}

		if (n == 0) {
			Iterator itr2 = c.getContent().iterator();
			while (itr2.hasNext()) {
				Content cnt = (Content) itr2.next();
				if (!cnt.getValue().trim().equals("")) {
					n++;
					break;
				}
			}
		}

		return n != 0 && i == j ? true : false;
	}
}
