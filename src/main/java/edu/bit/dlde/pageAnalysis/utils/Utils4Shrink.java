package edu.bit.dlde.pageAnalysis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

public class Utils4Shrink {
	private static boolean isDynamicThreshold = true;
	private static boolean isUsingSmoothing = true;

	public static void setDynamicThreshold(boolean flag) {
		isDynamicThreshold = flag;
	}

	public static void setUsingSmoothing(boolean flag) {
		isUsingSmoothing = flag;
	}

	public static double threshold = 1.5;
	private static double total = 0;
	private static double nc = 0;
	// li是个列外
	private static List<String> blk = Arrays.asList("div", "center", "p",
			"pre", "address", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote",
			"ul", "ol", "dir", "menu", "dl", "dt", "dd");

	public static double[] shrink(Element e) {
//		if (e.getParent() == null) {
//			threshold = 1.0;
//		}
		// if (e.getAttribute("class") != null
		// && e.getAttribute("class").getValue().equals("item item0"))
		// System.out.println("");

		double[] p2 = new double[e.getContentSize()];
		ArrayList<Content> delete = new ArrayList<Content>();
		ArrayList<Content> add = new ArrayList<Content>();
		/*
		 * 先除掉无法显示的节点
		 */
		Attribute attr = e.getAttribute("style");
		if (attr != null && attr.getValue().equals("display:none;"))
			// 删除节点
			delete.add(e);

		/*
		 * 首先计算vl(value length) 和tl(tag length)最初始值
		 */
		double vl = 0, tl = 0;
		vl = getVL(e.getText().trim());
		tl = e.getName().length() * 2 + 5;
		Iterator itr1 = e.getAttributes().iterator();
		// while (itr1.hasNext()) {
		// Attribute a = (Attribute) itr1.next();
		// tl = tl + a.getName().length() + a.getValue().length() + 4;
		// }
		if (e.getAttribute("href") != null)
			tl = tl + e.getAttributeValue("href").length() + 8;
		if (e.getAttribute("src") != null)
			tl = tl + e.getAttributeValue("src").length() + 7;

		/*
		 * 接着，递归进入字节点进行shrink，返回vl tl， 并完成自身的vl，tl的初始化
		 */
		int i = 0;
		double[][] tmp = new double[e.getContentSize()][2];
		double[] p1 = new double[e.getContentSize()];
		Iterator itr2 = e.getContent().iterator();
		while (itr2.hasNext()) {
			Content c = (Content) itr2.next();
			if (c instanceof Element) {
				tmp[i] = shrink((Element) c);
				p1[i] = tmp[i][0] / Math.max(1, tmp[i][1]);
				vl += tmp[i][0];
				tl += tmp[i][1];
			}
			if (c instanceof Text) {
				p1[i] = c.getValue().trim().length();
			}
			i++;
		}

		/*
		 * 假如是根的话，则计算密度
		 */
		if (e.getParent() == null) {
			for (int l = 0; l < p1.length; l++) {
				if (p1[l] >= threshold) {
					String str = ((Element) e.getChildren().get(l)).getValue()
							.trim();
					if (!str.equals("")
							&& !str.replaceAll("\n", "").matches("^\\[.*\\]$")) {
						total += ((Element) e.getChildren().get(l)).getValue()
								.trim().length();
					}
				}
			}
			for (int l = 0; l < p1.length; l++) {
				if (p1[l] < threshold)
					delete.add((Content) e.getChildren().get(l));
				else if (isDynamicThreshold) {
					String str = ((Element) e.getChildren().get(l)).getValue()
							.trim();
					if (!str.equals("")
							&& !str.replaceAll("\n", "").matches("^\\[.*\\]$")) {
						nc = str.length();
						System.out.print("previous: " + threshold);
						threshold = threshold * (1 - nc / total) + p1[l]
								* (nc / total);
						System.out.println("; p1: " + p1[l] + "; now: "
								+ threshold);
					}
				}
			}
		} else if (blk.contains(e.getName())) {

			// if(e.getName().equals("h1"))
			// System.out.println();
			/*
			 * 然后，使用对密度进行smooth
			 */
			Iterator itr3 = e.getContent().iterator();
			if (isUsingSmoothing) {
				for (i--; i >= 0; i--) {
					double w1 = 0.15, w2 = 0.15;
					if (e.getContent(Math.min(i + 1, e.getContentSize() - 1)) instanceof Text
							&& e.getContent(
									Math.min(i + 1, e.getContentSize() - 1))
									.getValue().trim().equals("")) {
						w1 = 0;
						w2 = 0.3;
					}
					if (e.getContent(Math.max(0, i - 1)) instanceof Text
							&& e.getContent(Math.max(0, i - 1)).getValue()
									.trim().equals("")) {
						w1 = 0.3;
						w2 = 0;
					}
					p2[i] = w1 * p1[Math.min(i + 1, e.getContentSize() - 1)]
							+ (1 - w1 - w2) * p1[i] + w2
							* p1[Math.max(0, i - 1)];
				}
			}else{
				p2 = p1.clone();
			}
			/*
			 * 根据密度进行cut off
			 */
			i = 0;
			Iterator itr4 = e.getContent().iterator();
			while (itr4.hasNext()) {
				Content c = (Content) itr4.next();
				if (c instanceof Element
						&& blk.contains(((Element) c).getName())) {
					if (p2[i] < threshold) {
						// 删除节点
						delete.add(c);
					}
				}
				i++;
			}
		}

		for (Content c : delete) {
			e.removeContent(c);
		}

		/*
		 * 最后，返回节点的文本长度和标签长度
		 */
		double[] rslt = new double[2];
		rslt[0] = vl;
		rslt[1] = tl;
		return rslt;

	}

	public static double getVL(String string) {
		int total = string.length();
		Pattern p1 = Pattern.compile(".*[a-z0-9][A-Z].*");
		Pattern p2 = Pattern.compile(".*[0-9][a-z].*");

		String[] tokens = string.split(" ");
		for (String t : tokens) {
			if (p1.matcher(t).find()) {
				total -= t.length();
			} else if (p2.matcher(t).find()) {
				total -= t.length();
			}
		}
		return total;
	}

	public static void main(String[] args) {
		System.out.println(Utils4Shrink
				.getVL("!&%!& J2HFFC http://www.baidu.com is a good site"));
	}
}
