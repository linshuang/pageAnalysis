package bit.lin.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

public class Utils4Shrink {
	static double threshold = 1;

	public static double[] shrink(Element e) {
		/*
		 * 首先计算vl 和tl最初始值
		 */
		double vl = 0, tl = 0;
		vl = getVL(e.getText().trim());
		tl = e.getName().length() * 2 + 5;
		Iterator itr1 = e.getAttributes().iterator();
		while (itr1.hasNext()) {
			Attribute a = (Attribute) itr1.next();
			tl = tl + a.getName().length() + a.getValue().length() + 4;
		}

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

		// if (e.isRootElement())
		// return null;

		/*
		 * 然后，使用对密度进行smooth
		 */
		double[] p2 = new double[e.getContentSize()];
		ArrayList<Content> delete = new ArrayList<Content>();
		ArrayList<Content> add = new ArrayList<Content>();

		Iterator itr3 = e.getContent().iterator();
		for (i--; i >= 0; i--) {
			double w1 = 0.15, w2 = 0.15;
			if (e.getContent(Math.min(i + 1, e.getContentSize() - 1)) instanceof Text
					&& e.getContent(Math.min(i + 1, e.getContentSize() - 1))
							.getValue().trim().equals("")) {
				w1 = 0;
			}
			if (e.getContent(Math.max(0, i - 1)) instanceof Text
					&& e.getContent(Math.max(0, i - 1)).getValue().trim()
							.equals("")) {
				w2 = 0;
			}
			p2[i] = w1 * p1[Math.min(i + 1, e.getContentSize() - 1)]
					+ (1 - w1 - w2) * p1[i] + w2 * p1[Math.max(0, i - 1)];
		}

		/*
		 * 根据密度进行cut off
		 */
		i = 0;
		Iterator itr4 = e.getContent().iterator();
		while (itr4.hasNext()) {
			Content c = (Content) itr4.next();
			if (c instanceof Element) {
				if (p2[i] < threshold) {
					// 删除节点
					delete.add(c);
				}
			}
			i++;
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
		System.out.println(Utils4Shrink.getVL("!&%!& J2HFFC http://www.baidu.com is a good site"));
	}
}
