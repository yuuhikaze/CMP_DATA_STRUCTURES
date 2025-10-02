package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.LinkedBinaryTree;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.utils.ANSICodes;

class ExpressionNode {
    private String value;
    private boolean isOperator;

    public ExpressionNode(String value, boolean isOperator) {
        this.value = value;
        this.isOperator = isOperator;
    }

    public String getValue() {
        return value;
    }

    public boolean isOperator() {
        return isOperator;
    }

    @Override
    public String toString() {
        return value;
    }
}


class ExpressionTreeController {
    private LinkedBinaryTree<ExpressionNode> tree;
    private Position<ExpressionNode> root;

    public ExpressionTreeController() {
        this.tree = new LinkedBinaryTree<>();
    }

    public void buildExpression() {
        // Build expression: ((3 + 5) * 2) - (8 / 4)
        root = tree.addRoot(new ExpressionNode("-", true));

        Position<ExpressionNode> leftSubtree = tree.addLeft(root, new ExpressionNode("*", true));
        Position<ExpressionNode> rightSubtree = tree.addRight(root, new ExpressionNode("/", true));

        Position<ExpressionNode> leftLeft =
                tree.addLeft(leftSubtree, new ExpressionNode("+", true));
        tree.addRight(leftSubtree, new ExpressionNode("2", false));

        tree.addLeft(leftLeft, new ExpressionNode("3", false));
        tree.addRight(leftLeft, new ExpressionNode("5", false));

        tree.addLeft(rightSubtree, new ExpressionNode("8", false));
        tree.addRight(rightSubtree, new ExpressionNode("4", false));
    }

    public void displayPreorder() {
        System.out.println(
                ANSICodes.BOLD + "\n=== PREORDER TRAVERSAL (Prefix Notation) ===" + ANSICodes.RESET);
        System.out.print("Expression: ");
        for (Position<ExpressionNode> pos : tree.preorder()) {
            System.out.print(pos.getElement() + " ");
        }
        System.out.println("\n");
    }

    public void displayInorder() {
        System.out.println(
                ANSICodes.BOLD + "\n=== INORDER TRAVERSAL (Infix Notation) ===" + ANSICodes.RESET);
        System.out.print("Expression: ");
        printInorderWithParentheses(root);
        System.out.println("\n");
    }

    private void printInorderWithParentheses(Position<ExpressionNode> pos) {
        if (pos == null)
            return;

        ExpressionNode node = pos.getElement();

        if (node.isOperator()) {
            System.out.print("(");
            printInorderWithParentheses(tree.left(pos));
            System.out.print(" " + node.getValue() + " ");
            printInorderWithParentheses(tree.right(pos));
            System.out.print(")");
        } else {
            System.out.print(node.getValue());
        }
    }

    public void displayPostorder() {
        System.out.println(
                ANSICodes.BOLD + "\n=== POSTORDER TRAVERSAL (Postfix/RPN Notation) ===" + ANSICodes.RESET);
        System.out.print("Expression: ");
        for (Position<ExpressionNode> pos : tree.postorder()) {
            System.out.print(pos.getElement() + " ");
        }
        System.out.println("\n");
    }

    public void evaluate() {
        System.out.println(ANSICodes.BOLD + "\n=== EVALUATION ===" + ANSICodes.RESET);
        double result = evaluateSubtree(root);
        System.out.println("Result: " + result);
    }

    private double evaluateSubtree(Position<ExpressionNode> pos) {
        if (pos == null)
            return 0;

        ExpressionNode node = pos.getElement();

        if (!node.isOperator()) {
            return Double.parseDouble(node.getValue());
        }

        double leftVal = evaluateSubtree(tree.left(pos));
        double rightVal = evaluateSubtree(tree.right(pos));

        switch (node.getValue()) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                return leftVal / rightVal;
            default:
                return 0;
        }
    }

    public void displayTreeStructure() {
        System.out.println(ANSICodes.BOLD + "\n=== TREE STRUCTURE ===" + ANSICodes.RESET);
        printTree(root, "", true);
    }

    private void printTree(Position<ExpressionNode> pos, String prefix, boolean isTail) {
        if (pos == null)
            return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + pos.getElement());

        Position<ExpressionNode> left = tree.left(pos);
        Position<ExpressionNode> right = tree.right(pos);

        if (left != null) {
            printTree(left, prefix + (isTail ? "    " : "│   "), right == null);
        }
        if (right != null) {
            printTree(right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public void printStatistics() {
        System.out.println(ANSICodes.BOLD + "\n=== TREE STATISTICS ===" + ANSICodes.RESET);
        System.out.println("Total nodes: " + tree.size());
        System.out.println("Tree height: " + tree.height(root));

        int operators = 0;
        int operands = 0;
        for (Position<ExpressionNode> pos : tree.preorder()) {
            if (pos.getElement().isOperator()) {
                operators++;
            } else {
                operands++;
            }
        }
        System.out.println("Operators: " + operators);
        System.out.println("Operands: " + operands);
    }
}


public class EI04_ExpressionTree {
    public static void main(String[] args) {
        System.out.println(
                ANSICodes.BOLD + "╔════════════════════════════════════════╗" + ANSICodes.RESET);
        System.out.println(
                ANSICodes.BOLD + "║   ARITHMETIC EXPRESSION TREE EXAMPLE   ║" + ANSICodes.RESET);
        System.out.println(
                ANSICodes.BOLD + "╚════════════════════════════════════════╝" + ANSICodes.RESET);

        ExpressionTreeController controller = new ExpressionTreeController();
        controller.buildExpression();

        controller.displayTreeStructure();
        controller.displayPreorder();
        controller.displayInorder();
        controller.displayPostorder();
        controller.evaluate();
        controller.printStatistics();
    }
}
