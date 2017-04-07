import java.util.Random;

import MyTreePackage.BinaryNode;

public class BinaryTreeGen {

	private Random random;
	private int count;
	private int genC;
	private boolean single;
	private int low, high;

	public BinaryTreeGen(int c, int l, int h) {
		// TODO Auto-generated constructor stub
		random = new Random();
		count = c;
		genC = count / 2;
		single = false;
		low = l;
		high = h;
	}

	public BinaryNode<Integer> aBinaryNode() {
		Integer integer = new Integer(random.nextInt(high - low + 1) + low);
		return aTreeNode(new BinaryNode<Integer>(integer), count - 1);
	}

	public BinaryNode<Integer> aSingleSidedTreeNode() {
		genC = count;
		single = true;
		BinaryNode<Integer> node = aBinaryNode();
		genC = count / 2;
		single = false;
		return node;
	}

	private BinaryNode<Integer> aTreeNode(BinaryNode<Integer> node, int count) {
		if (count < 1) {
			return null;
		}
		Integer i = new Integer(random.nextInt(high - low + 1) + low);
		if (i.intValue() % 2 == 0) {
			node.setLeftChild(new BinaryNode<Integer>(i));
			aTreeNode(node.getLeftChild(), count - 1);

			int j = random.nextInt(high - low + 1) + low;
			if (count > genC) {
				node.setRightChild(new BinaryNode<Integer>(random.nextInt(high
						- low + 1)
						+ low));
				aTreeNode(node.getRightChild(), count - 1);
			} else {
				if (!single && (j % 2 == 0)) {
					node.setRightChild(new BinaryNode<Integer>(random
							.nextInt(high - low + 1) + low));
					aTreeNode(node.getRightChild(), count - 1);
				}
			}
		} else {
			node.setRightChild(new BinaryNode<Integer>(i));
			aTreeNode(node.getRightChild(), count - 1);

			int j = random.nextInt(high - low + 1) + low;
			if (count > genC) {
				node.setLeftChild(new BinaryNode<Integer>(random.nextInt(high
						- low + 1)
						+ low));
				aTreeNode(node.getLeftChild(), count - 1);
			} else {
				if (!single && (j % 2 == 0)) {
					node.setLeftChild(new BinaryNode<Integer>(random
							.nextInt(high - low + 1) + low));
					aTreeNode(node.getLeftChild(), count - 1);
				}
			}
		}
		return node;
	}

	public static void main(String[] args) {
		// 5 levels, min value 3, max value 20
		BinaryTreeGen gen = new BinaryTreeGen(5, 3, 20);
		BinaryNode<Integer> node = gen.aBinaryNode();
		System.out.println(node);
		BinaryNode<Integer> node2 = gen.aSingleSidedTreeNode();
		System.out.println(node2);
	}
}
