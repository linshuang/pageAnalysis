package bit.lin.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class TreeNode4s extends TreeNode {
	public int charNumber = 0;
	public int tagNumber = 0;
	public int linkCharNumber = 0;
	public int linkTagNumber = 0;
	public int total = 0;
	public double TD = 0;
	public double CTD = 0;
	public double DSum = 0;
	public boolean isContent = false;

	public void setTrue() {
		isContent = true;
	}

	public static TreeNode4s as4S(TreeNode node) {
		TreeNode4s node4s = new TreeNode4s();

		node4s.addAttr(node.getAttr());
		node4s.addChildren(node.getChildren());
		node4s.setParent(node.getParent());
		node4s.setName(node.getName());
		node4s.setValue(node.getValue());

		return node4s;
	}

	public void initArgs() {
		tagNumber = 1;
		charNumber = 0;
		total = 0;
		HashMap attr = getAttr();
		Iterator it = attr.entrySet().iterator();
		while (it.hasNext()) {
			Entry e = (Entry) it.next();
			String key = (String) e.getKey();
			total = total + e.getKey().toString().length()
					+ e.getValue().toString().length() + 4;
			if (key.toLowerCase().equals("href")) {
				linkCharNumber = linkCharNumber
						+ e.getValue().toString().length() + 7;
				linkTagNumber++;
			}
		}
		charNumber = charNumber + StringUtils.trim(getValue()).replaceAll(" ", "").length();
		total = total + getName().length() + charNumber + 5;

	}

	public void adjustParentArgs() {
		TreeNode4s p = (TreeNode4s) this.getParent();
		p.charNumber += charNumber;
		p.tagNumber++;
		p.linkCharNumber += linkCharNumber;
		p.linkTagNumber += linkTagNumber;
	}
}
