package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.DoublyLinkedList;
import java.util.Iterator;

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

    public void printTabs() {
        for (var elem : this.tabs) {
            System.out.print(elem);
        }
    }

    public void printTabsReversed() {
        for (Iterator<E> it = this.tabs.reverseIterator(); it.hasNext(); ) {
            System.out.print(it.next());
        }
    }
}
