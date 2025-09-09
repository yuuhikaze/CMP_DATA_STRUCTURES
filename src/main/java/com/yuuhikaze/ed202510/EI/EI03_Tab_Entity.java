package com.yuuhikaze.ed202510.EI;

class Tab {
    private String title;
    private String url;

    public Tab(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return title + " → " + url + "\n";
    }
}
