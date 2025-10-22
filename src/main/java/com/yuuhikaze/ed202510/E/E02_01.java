package com.yuuhikaze.ed202510.E;

import com.yuuhikaze.ed202510.TDA.ArrayList;
import com.yuuhikaze.ed202510.TDA.HashMap;

/*
 * class ControllerMollie { private TreeMap<Long, Product> tree;
 *
 * public void addProduct(long ID, Product product) { tree.put(ID, product); }
 *
 * public void searchProductByID(long ID) { tree.treeSearch(ID); } }
 */

class ControllerMollie {
    private HashMap<Long, Product> map;

    public ControllerMollie() {
        this.map = new HashMap<>();
    }

    // b)
    // O(1) amortized - vector (underlying data structure) does not grow
    public void addProduct(long ID, Product product) {
        map.put(ID, product);
    }

    // e)
    // O(1)
    public Product searchProductByID(long ID) {
        return map.get(ID);
    }

    // d)
    // Iterative (more idiomatic, performant, safe)
    /* public int countSameKindProducts(String kind) {
        int result = 0;
        for (var product : map.values()) {
            if (product.getCategory().equals(kind))
                result += 1;
        }
        return result;
    } */

    // d)
    // Recursive (fancy)
    public int countSameKindProducts(String kind) {
        ArrayList<Product> snapshot = new ArrayList<>(map.size());
        int i = 0;
        for (var product : map.values()) {
            snapshot.insert(i, product);
            i++;
        }
        return countSameKindProducts(snapshot, snapshot.size(), kind, 0);
    }

    private int countSameKindProducts(
            ArrayList<Product> slice, int counter, String kind, int result) {
        if (counter == -1)
            return result;
        return countSameKindProducts(
                slice,
                counter--,
                kind,
                slice.get(counter).getCategory().equals(kind) ? ++result : result);
    }

    // c) would be similar to d)
    // there is no point in doing it tho, you got e) after all
    // it is already giving me cancer looking at the *fancy* d)
}


// a)
class Product {
    private String category; // kind
    private String description;

    public Product(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }
}


public class E02_01 {
}
