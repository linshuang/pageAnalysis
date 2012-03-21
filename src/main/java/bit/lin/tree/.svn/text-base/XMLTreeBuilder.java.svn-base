package bit.lin.tree;

import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import bit.lin.extractor.util.CustomizedCleaner;

public class XMLTreeBuilder {

	public ITreeNode getTreeNodes(String fileName, ITreeNode root) {
		// 构建xml文件的树
		CustomizedCleaner cleaner = new CustomizedCleaner();
		Document doc = cleaner.cleanHtml2Doc(fileName);

		try {
			root.setName(doc.getRootElement().getName());
			traverseNode(doc.getRootElement(), root);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		root.setParent(null);
		return root;
	}

	private void traverseNode(Element e, ITreeNode tNode)
			throws InstantiationException, IllegalAccessException {
		// System.out.println(e.getName()+" "+e.getText());
		List<Element> children = e.getChildren();
		for (Element child : children) {
			ITreeNode n = tNode.getClass().newInstance();
			n.setName(child.getName());
			n.setParent(tNode);
			n.setValue(child.getText());
			List<Attribute> list = child.getAttributes();
			for (Attribute l : list) {
				n.addAttr(l.getName(), l.getValue());
			}
			tNode.addChild(n);
			traverseNode(child, n);
		}

	}

	public static void main(String[] args) {
		XMLTreeBuilder builder = new XMLTreeBuilder();
		TreeNode root = new TreeNode();
		root = (TreeNode) builder.getTreeNodes("src/test/resources/test.xml",
				root);
		root.depthFirstVisit(new NodeVisitor() {
			public void visit(ITreeNode node) {
				System.out.println(node.getName() + " with value: "
						+ node.getValue());
			}
		});

	}
}
