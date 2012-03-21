package bit.lin.tree;

public interface NodeVisitor {
	/**
	 * 访问者模式，用来访问{@link TreeNode}
	 */
	public void visit(ITreeNode n);

}
