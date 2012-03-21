package bit.lin.extractor.test.main;

import org.apache.commons.lang3.StringUtils;

import bit.lin.tree.ITreeNode;
import bit.lin.tree.NodeVisitor;
import bit.lin.tree.TreeNode4s;
import bit.lin.tree.XMLTreeBuilder;

public class Display {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "src/test/resources/163_2.xml";
		XMLTreeBuilder builder = new XMLTreeBuilder();
		TreeNode4s n4s = new TreeNode4s();
		n4s = (TreeNode4s) builder.getTreeNodes(str, n4s);
		n4s.depthFirstVisit(new NodeVisitor(){

			public void visit(ITreeNode n) {
				
				System.out.println(StringUtils.repeat("-", n.getAbsolutePath().split("/").length));
			}
			
		});
	}

}
