package bit.lin.extractor.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import bit.lin.extractor.util.CustomizedCleaner;
import bit.lin.tree.ITreeNode;
import bit.lin.tree.NodeVisitor;
import bit.lin.tree.TreeNode4s;
import bit.lin.tree.XMLTreeBuilder;

public class SunfeiExtractor {
	public static TreeNode4s root = new TreeNode4s();
	public static double threshold = 0;
	static Logger logger = Logger.getLogger(SunfeiExtractor.class);
	static long LCb = 0;
	public String ttl = "";

	public SunfeiExtractor() {
		root = new TreeNode4s();
		LCb = 0;
	}

	public void getMainContent(String fileName) {
		ttl = "";
		XMLTreeBuilder builder = new XMLTreeBuilder();
		root = (TreeNode4s) builder.getTreeNodes(fileName, root);
		// 点各种长度，个数
		countNumber(root);
		// 使用上面得到的值计算各TextDensity CompositeTextDensity DensitySum
		root.depthFirstVisit(new NodeVisitor() {

			public void visit(ITreeNode n) {
				TreeNode4s n4s = (TreeNode4s) n;

				n4s.TD = ((long) n4s.charNumber) / zeroToOne(n4s.tagNumber);
				double a, b;
				a = ((double) n4s.charNumber) * n4s.tagNumber
						/ zeroToOne(n4s.linkCharNumber)
						/ zeroToOne(n4s.linkTagNumber);
				b = ((double) n4s.charNumber) * n4s.linkCharNumber
						/ zeroToOne(n4s.total - n4s.linkCharNumber)
						+ ((double) root.linkCharNumber) * n4s.charNumber
						/ zeroToOne(root.charNumber);

				// logger.info("a is " + a + ". b is " + b);
				n4s.CTD = n4s.TD * Math.log10(zeroToOne(a)) / zeroToOne(Math.log(zeroToOne(b)));
				TreeNode4s p = (TreeNode4s) n4s.getParent();
				if (p != null)
					p.DSum += n4s.CTD;
				if (n4s.tagNumber == 1)
					n4s.DSum = n4s.CTD;
			}

		});
		// root.depthFirstVisit(new NodeVisitor() {
		//
		// public void visit(ITreeNode n) {
		// TreeNode4s n4s = (TreeNode4s) n;
		// System.out.println(n4s.DSum);
		//
		// }
		// });

		// 寻找阈值
		TreeNode4s n = findTheMaxDS(root);
		threshold = getMinCTDAsThreshold(n);
		logger.info("Find the threshold is " + threshold + ". Root with CTD "
				+ root.CTD);
		// 开始抽取
		markContent(root);
		// 显示
		root.depthFirstVisit(new NodeVisitor() {

			public void visit(ITreeNode n) {
				TreeNode4s n4s = (TreeNode4s) n;
				if (((TreeNode4s) n).isContent) {
					System.out.println("#out# ||" + n4s.getName() + " "
							+ n4s.getValue() + "|| charNumber" + n4s.charNumber
							+ " tagNumber" + n4s.tagNumber + " linkNumber"
							+ n4s.linkCharNumber + " linktagNumber"
							+ n4s.linkTagNumber + " CTD " + n4s.CTD);
					ttl += n4s.getValue();
					ttl += "\n";
				}
			}

		});
	}

	private void markContent(TreeNode4s n) {
		if (n.CTD >= threshold) {
			logger.info("now visit " + n.getAbsolutePath());
			TreeNode4s a = findChildMaxDS(n);
			if (a != null && !StringUtils.trim(a.getValue()).equals("")
					&& a.CTD >= threshold) {
				a.setTrue();
//				setChildrenAllTrue(n);
				logger.info("mark node " + a.getAbsolutePath()
						+ " as content, because its parent "
						+ a.getParent().getAbsolutePath() + "'s CTD is "
						+ ((TreeNode4s) a.getParent()).CTD);
			} 
				for (ITreeNode child : n.getChildren()) {
					TreeNode4s c4s = (TreeNode4s) child;
					markContent(c4s);
				}
			
		}
	}

	private void setChildrenAllTrue(TreeNode4s n) {
		for (ITreeNode child : n.getChildren()) {
			TreeNode4s child4s = (TreeNode4s) child;
			child4s.setTrue();
			setChildrenAllTrue(child4s);
		}

	}

	private TreeNode4s findChildMaxDS(TreeNode4s n) {
		TreeNode4s result = n;
		double max = -Double.MAX_VALUE;
		ArrayList<ITreeNode> children = n.getChildren();
		for (ITreeNode child : children) {
			TreeNode4s child4s = (TreeNode4s) child;
			if (child4s.DSum > max) {
				result = child4s;
				max = child4s.DSum;
			}
		}
		return result;
	}

	private TreeNode4s findTheMaxDS(TreeNode4s n) {
		TreeNode4s result = n;
		double max = n.DSum;
		ArrayList<ITreeNode> children = n.getChildren();
		for (ITreeNode child : children) {
			TreeNode4s child4s = (TreeNode4s) child;
			TreeNode4s tmp = findTheMaxDS(child4s);
			if (tmp.DSum > max) {
				result = tmp;
				max = tmp.DSum;
			}
		}
		return result;
	}

	private double getMinCTDAsThreshold(TreeNode4s n) {
		double min = n.CTD;
		TreeNode4s p = (TreeNode4s) n.getParent();
		if (p == null)
			return min;
		double tmp = getMinCTDAsThreshold(p);
		if (min > tmp)
			return tmp;
		else
			return min;
	}

	private int[] countNumber(ITreeNode node) {
		int[] result = new int[5];

		TreeNode4s n4s = (TreeNode4s) node;
		ArrayList<ITreeNode> children = node.getChildren();
		n4s.initArgs();
		for (ITreeNode child : children) {
			result = countNumber(child);
			n4s.charNumber += result[0];
			n4s.tagNumber += result[1];
			n4s.linkCharNumber += result[2];
			n4s.linkTagNumber += result[3];
			n4s.total += result[4];
		}

		result[0] = n4s.charNumber;
		result[1] = n4s.tagNumber;
		result[2] = n4s.linkCharNumber;
		result[3] = n4s.linkTagNumber;
		result[4] = n4s.total;
		// {n4s.charNumber,n4s.tagNumber,n4s.linkCharNumber,n4s.linkTagNumber,n4s.total};

		return result;
	}

	private double zeroToOne(double i) {
		return i == 0 ? 1 : i;
	}

	public static void main(String[] args) {

		String[] all = { "yahoo", "cnn", "bbc", "nytimes" };
		String src;
		String desS;
		String tmp;
		for (String a : all) {
			src = "/home/lins/data/*/page/".replaceAll("\\*", a);
			desS = "/home/lins/data/*/rslt-s/".replaceAll("\\*", a);
			File dir = new File(src);
			String[] fName = dir.list();
			for (String n : fName) {
				try {
					SunfeiExtractor ext = new SunfeiExtractor();
					ext.getMainContent(src + n);

					BufferedWriter bw = new BufferedWriter(new FileWriter(
							new File(desS + n)));
					bw.append(ext.ttl);
					bw.flush();
					bw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
