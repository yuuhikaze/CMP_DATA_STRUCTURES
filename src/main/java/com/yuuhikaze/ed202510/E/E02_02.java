package com.yuuhikaze.ed202510.E;

import com.yuuhikaze.ed202510.TDA.TreeMap;

class USFQController {
    private TreeMap<String, Student> tree;

    public USFQController() {
        this.tree = new TreeMap<>();
    }

    // c)
    // O(log(n))
    public void addStudent(String code, Student student) {
        tree.put(code, student);
    }

    // b)
    // O(log(n))
    public Student queryStudentByCode(String code) {
        return tree.treeSearch(code).getElement().getValue();
    }

    // d)
    // O(log(n))
    public void removeStudent(String code) {
        tree.remove(code);
    }

    // auxiliary method (don't grade)
    public void printStudents() {
        for (var node : tree.entrySet()) {
            System.out.println(node.getValue());
        }
    }
}


// a)
class Career {

    private String name;

    // ...
    // other attributes (we don't want to add horns to this guy)

    public Career(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Career [name=" + name + "]";
    }
}


// a)
class Student {

    private Career career;
    private String name; // I can add this much, right?

    // ...
    // other attributes

    public Student(String name, Career career) {
        this.name = name;
        this.career = career;
    }

    // Exercise mentions that careers must be able to change
    public void setCareer(Career career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", career=" + career + "]";
    }
}

public class E02_02 {

    public static void main(String[] args) {
        USFQController uc = new USFQController();
        // CAREERS
        var cs = new Career("Computer Science");
        var chem = new Career("Chemistry");
        var mech = new Career("Mechanics");
        // STUDENTS INSERTION c)
        uc.addStudent("00333621", new Student("John", cs));
        uc.addStudent("00333622", new Student("Marie", chem));
        uc.addStudent("00333623", new Student("Steven", mech));
        uc.addStudent("00333624", new Student("Mark", cs));
        uc.addStudent("00333625", new Student("Xiang", cs));
        // INDUCTED TREE
        // e)
        //               00333623
        //              /       \
        //          00333622  00333624
        //           /           \
        //       00333621      00333625
        // Tree is balanced despite keys being inserted in ascending order
        // since put has a rebalance callback
        // QUERY
        // b)
        System.out.println("QUERY STUDENT BY CODE");
        System.out.println(uc.queryStudentByCode("00333621"));
        // DELETION
        // d)
        System.out.println("REMOVE STUDENT, THEN PRINT TREE");
        uc.removeStudent("00333623");
        uc.printStudents();
        // inorder traversal confirms that the tree is induced as depicted in e)
    }
}
