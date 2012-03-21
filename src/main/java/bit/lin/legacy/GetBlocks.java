package bit.lin.legacy;

import java.util.ArrayList;
import java.util.Iterator;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;

import bit.lin.extractor.util.CustomizedCleaner;
import bit.lin.tree.TreeNode;

// 采用vips算法获得一颗新的树,除根节点外每个节点包含一个block
public class GetBlocks {
	private Element root = new Element("root");

	public GetBlocks(String fileName) {
		// 构建xml文件的树
		CustomizedCleaner cleaner = new CustomizedCleaner();
		Document doc = cleaner.cleanHtml2Doc(fileName);
		divideDom(doc.getRootElement());
		// cutOff(root);
		shrink(root);
	}

	private double[] shrink(Element e) {
		int i = 0;
		Iterator itr = e.getContent().iterator();
		double[] rslt = new double[2];
		double[][] tmp = new double[e.getContentSize()][2];

		// 递归进入字节点
		// System.out.println(e.getContentSize());
		while (itr.hasNext()) {
			Content c = (Content) itr.next();
			if (c instanceof Element) {
				tmp[i] = shrink((Element) c);
			}
			i++;
		}
		if (e == root)
			return null;

		// 使用密度,缩进所有子节点
		double text = 0, tag = 0;
		text = e.getText().trim().length();
		tag = e.getName().length() * 2 + 5;
		Iterator itr2 = e.getAttributes().iterator();
		while (itr2.hasNext()) {
			Attribute a = (Attribute) itr2.next();
			tag = tag + a.getName().length() + a.getValue().length() + 4;
		}
		ArrayList<Content> delete = new ArrayList<Content>();
		ArrayList<Content> add = new ArrayList<Content>();
		double[] p1 = new double[e.getContentSize()];
		double[] p2 = new double[e.getContentSize()];
		// smooth
		i = 0;
		Iterator itr3 = e.getContent().iterator();
		while (itr3.hasNext()) {
			Content c = (Content) itr3.next();
			if (c instanceof Element) {
				p1[i] = tmp[i][0] / Math.max(1, tmp[i][1]);
			} else {
				p1[i] = c.getValue().trim().length();
			}
			i++;
		}
		double w1 = 0.15, w2 = 0.15;
		for (i--; i >= 0; i--) {
			p2[i] = w1 * p1[Math.min(i + 1, e.getContentSize() - 1)]
					+ (1 - w1 - w2) * p1[i] + w2 * p1[Math.max(0, i - 1)];
		}
		// find the tag
		i = 0;
		Iterator itr4 = e.getContent().iterator();
		while (itr4.hasNext()) {
			Content c = (Content) itr4.next();
			if (c instanceof Element) {
				text += tmp[i][0];
				tag += tmp[i][1];
				if (p2[i] < 1) {
					// 删除节点
					delete.add(c);
				}
				// else {
				// // 缩进节点
				// //add.add(c);
				// //delete.add(c);
				// }
			}
			i++;
		}
		// 删除要删除的子节点
		// for (Content c : add) {
		// e.addContent(((Element) c).getText());
		// }
		for (Content c : delete) {
			e.removeContent(c);
		}
		// 返回节点的文本长度和标签长度
		rslt[0] = text;
		rslt[1] = tag;
		return rslt;

	}

	private Content cutOff(Element e) {
		ArrayList<Content> cnts = new ArrayList<Content>();
		// 迭代进入子节点
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			Content element = (Content) itr.next();
			if (element instanceof Element) {
				Content c = cutOff((Element) element);
				if (c != null) {
					cnts.add(c);
				}
			}
			if (element instanceof Text) {
				if (e.getTextTrim().trim().equals(""))
					cnts.add(element);
			}
		}
		// 删除要删除的子节点
		for (Content c : cnts) {
			e.removeContent(c);
		}

		// 判断自身是否要被删除,并返回
		if (e.getContent().size() == 0) {
			return e;
		}
		return null;
	}

	public void divideDom(Element e) {
		if (dividable(e)) {
			Iterator itr = e.getChildren().iterator();
			while (itr.hasNext()) {
				divideDom((Element) itr.next());
			}
		} else {
			root.addContent((Element) e.clone());
		}
	}

	public boolean dividable(Element e) {
		boolean rslt = true;
		// rule_2
		if (e.getText().trim().equals("") && e.getChildren().size() == 1)
			return true;

		// rule_1
		if (e.getName().equals("div")) {
			Iterator itr = e.getChildren().iterator();
			while (itr.hasNext()) {
				if (!((Element) itr.next()).getName().equals("div")
						&& e.getChildren().size() != 1) {
					rslt = false;
				}
			}
		}
		return rslt;
	}

	public Element getSgmtRslt() {
		return root;
	}

	public void displayResult() {
		displayResult(root);
	}

	private void displayResult(Element e) {
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			Content c = (Content) itr.next();
			String str;
			if (c instanceof Text) {
				str = c.getValue();
				if (!str.trim().equals(""))
					System.out.println(str);
			}
			if(c instanceof Element){
				displayResult(e);
			}

		}
	}

	public static void main(String[] args) {
		GetBlocks getB = new GetBlocks("src/test/resources/tests.xml");
		getB.displayResult();
	}
}
