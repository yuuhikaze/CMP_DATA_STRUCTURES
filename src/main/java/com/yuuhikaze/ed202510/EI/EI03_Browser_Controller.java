package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.DoublyLinkedList;
import java.util.Stack;

class BrowserController<E> {
    private DoublyLinkedList<E> tabs;

    public BrowserController() {
        this.tabs = new DoublyLinkedList<>();
    }

    public void openTab(E tab) {
        tabs.addLast(tab);
    }

    public void closeFirstTab() {
        tabs.removeFirst();
    }

    public void closeLastTab() {
        tabs.removeLast();
    }

    public void printTabsReversed() {
        Stack<E> stack = new Stack<>();
        for (E tab : tabs) {
            stack.push(tab);
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (E tab : tabs) {
            result += tab.toString();
        }
        return result;
    }
}
