package bit.lin.tree;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeNode implements ITreeNode {
	private ITreeNode _parent = null;
	private ArrayList<ITreeNode> _children = new ArrayList<ITreeNode>();
	private HashMap _attr = new HashMap();
	private String _name = null;
	private String _value = "";

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		this._value += value;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public void addAttr(Object key, Object value) {
		_attr.put(key, value);
	}

	public void addAttr(HashMap map) {
		_attr.putAll(map);
	}

	public HashMap getAttr() {
		return _attr;
	}

	public Object getAttr(Object o) {
		return _attr.get(o);
	}

	public void addChild(ITreeNode child) {
		_children.add(child);
		child.setParent(this);
	}

	public void addChildren(ArrayList<ITreeNode> children) {
		_children.addAll(children);
	}

	public ITreeNode getParent() {
		return _parent;
	}

	public void setParent(ITreeNode node) {
		_parent = node;
	}

	public ArrayList<ITreeNode> getChildren() {
		return _children;
	}

	public void depthFirstVisit(NodeVisitor visitor) {
		visitor.visit(this);
		depthFirstVisit(this, visitor);
	}

	private void depthFirstVisit(ITreeNode node, NodeVisitor visitor) {
		if (node.getChildren() != null) {
			ArrayList<ITreeNode> nodes = node.getChildren();
			for (ITreeNode n : nodes) {
				visitor.visit(n);
				depthFirstVisit(n, visitor);
			}
		}
	}

	public void widthFirstVisit(NodeVisitor visitor) {
		visitor.visit(this);
		widthFirstVisit(this, visitor);
	}

	private void widthFirstVisit(ITreeNode node, NodeVisitor visitor) {
		if (node.getChildren() != null) {
			ArrayList<ITreeNode> nodes = node.getChildren();
			for (ITreeNode n : nodes) {
				visitor.visit(n);
			}
			for (ITreeNode n : nodes) {
				widthFirstVisit(n, visitor);
			}
		}
	}

	public String getAbsolutePath() {
		StringBuilder path = new StringBuilder();
		ITreeNode p = this;
		do {
			path.insert(0, "/" + p.getName());
			p = p.getParent();
		} while (p != null&&p.getParent() != p);
		return path.toString();
	}
}
