package com.yuuhikaze.ed202510.CP;

import com.yuuhikaze.ed202510.TDA.LinkedBinaryTree;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;

class ExpressionNode {
    private char value;
    private boolean isOperator;

    public ExpressionNode(char value, boolean isOperator) {
        this.value = value;
        this.isOperator = isOperator;
    }

    public char getValue() {
        return value;
    }

    public boolean isOperator() {
        return isOperator;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}


class ExpressionController {
    // private Position<ExpressionNode> root;
    private LinkedBinaryTree<ExpressionNode> tree;
    private int x;

    public ExpressionController() {
        this.tree = new LinkedBinaryTree<>();
        buildExpression();
    }

    private void buildExpression() {
        // Add root node
        var rootPos = tree.addRoot(new ExpressionNode('+', true));
        // Add depth 2 nodes
        var depth2LeftPos = tree.addLeft(rootPos, new ExpressionNode('-', true));
        var depth2RightPos = tree.addRight(rootPos, new ExpressionNode('1', false));
        // Add depth 3 nodes
        var depth3LeftPos = tree.addLeft(depth2LeftPos, new ExpressionNode('·', true));
        var depth3RightPos = tree.addRight(depth2LeftPos, new ExpressionNode('·', true));
        // Add depth 4 nodes
        tree.addLeft(depth3LeftPos, new ExpressionNode('x', false));
        tree.addRight(depth3LeftPos, new ExpressionNode('x', false));
        tree.addLeft(depth3RightPos, new ExpressionNode('2', false));
        tree.addRight(depth3RightPos, new ExpressionNode('x', false));
    }

    public double compute(int x) {
        this.x = x;
        return evaluateExpression(tree.root());
    }

    private double evaluateExpression(Position<ExpressionNode> pos) {
        if (pos == null)
            return 0;
        var node = pos.getElement();
        if (!node.isOperator()) {
            switch (node.toString()) {
                case "x":
                    return x;
                default:
                    return Double.parseDouble(node.toString());
            }
        }
        double leftSubtree = evaluateExpression(tree.left(pos));
        double rightSubtree = evaluateExpression(tree.right(pos));
        // parse tokens
        switch (node.getValue()) {
            case '·':
                return leftSubtree * rightSubtree;
            case '-':
                return leftSubtree - rightSubtree;
            case '+':
                return leftSubtree + rightSubtree;
            default:
                return 0;
        }
    }

    public void tracePath() {
        for (Position<ExpressionNode> pos : tree.inorder()) {
            System.out.print(pos.getElement() + " ");
        }
        System.out.println();
    }
    
    public void tracePathPretty() {
        printInorderWithParentheses(tree.root());
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
}

public class CP05_EJ01 {
    public static void main(String[] args) {
        ExpressionController ec = new ExpressionController();
        System.out.println("YOUR RESULT");
        System.out.println(ec.compute(2));
        System.out.println("THE TRAVERSED TREE (RAW)");
        ec.tracePath();
        System.out.println("THE TRAVERSED TREE (PRETTY)");
        ec.tracePathPretty();
    }
}
