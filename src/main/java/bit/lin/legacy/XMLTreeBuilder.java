package bit.lin.legacy;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bit.lin.tree.ITreeNode;
import bit.lin.tree.NodeVisitor;
import bit.lin.tree.TreeNode;
/**
 * 被遗弃的builder,由于w3dom对于<a href="http://t.xinmin.cn/index.php?m=reg&a=mobile" target="_blank"></a> 
 * 类似标签无法解释。
 */
public class XMLTreeBuilder {

	public ITreeNode getTreeNodes(String fileName, ITreeNode root) {
		// 构建xml文件的树
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		try {
			System.out.println("top");
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(new File(fileName));
			traverseNode(doc, root);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		root = root.getChildren().get(0);
		root.setParent(root);
		return root;
	}

	private void traverseNode(Node node, ITreeNode tNode) throws InstantiationException, IllegalAccessException {
		NodeList nodes = node.getChildNodes();
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				ITreeNode child = tNode.getClass().newInstance();
				Node n = nodes.item(i);
				System.out.println("node name = " + n.getNodeName());
				
				switch (n.getNodeType()) {
				// ELEMENT_NODE
				case 1:
					child.setName(n.getNodeName());
					for (int j = 0; j < n.getAttributes().getLength(); j++) {
						Node attr = n.getAttributes().item(j);
						child.addAttr(attr.getNodeName(), attr.getTextContent());
					}
					tNode.addChild(child);
					traverseNode(n, child);
					break;

				// TEXT_NODE
				case 3:
					tNode.setValue(n.getTextContent());
					break;
				default:
					traverseNode(n, tNode);
					break;
				}
			}
		}

	}

	public static void main(String[] args) {
		XMLTreeBuilder builder = new XMLTreeBuilder();
		TreeNode root =new TreeNode(); 
		root = (TreeNode) builder.getTreeNodes("src/test/resources/test.xml",root);
		root.depthFirstVisit(new NodeVisitor() {
			public void visit(ITreeNode node) {
				System.out.println(node.getName() + " with value: "
						+ node.getValue());
			}
		});

	}
}
