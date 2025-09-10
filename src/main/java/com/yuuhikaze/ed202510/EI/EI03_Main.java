package com.yuuhikaze.ed202510.EI;

public class EI03_Main {
    public static void main(String[] args) {
        Tab tab1 = new Tab("arXiv", "https://arxiv.org/");
        Tab tab2 = new Tab("StackOverflow", "https://stackoverflow.com/");
        Tab tab3 = new Tab("YouTube", "https://youtube.com/");
        Tab tab4 = new Tab("GitHub", "https://github.com/");

        BrowserController<Tab> browser = new BrowserController<>();
        browser.openTab(tab1);
        browser.openTab(tab2);
        browser.openTab(tab3);
        browser.openTab(tab4);

        System.out.println("All open tabs:");
        browser.printTabs();

        System.out.println("\nClosing last tab...\n");
        browser.closeLastTab();

        System.out.println("Reversed tab view (most recent first):");
        browser.printTabsReversed();
    }
}
