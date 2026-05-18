package dsproject;

import java.util.ArrayList;

class StudentBST {

    StudentNode root;

    public void add(int number, String name) {

        root = addRecursive(root, number, name);
    }

    private StudentNode addRecursive(StudentNode root, int number, String name) {

        if (root == null) {
            return new StudentNode(number, name);
        }

        if (number < root.number) {
            root.left = addRecursive(root.left, number, name);
        } else if (number > root.number) {
            root.right = addRecursive(root.right, number, name);
        }

        return root;
    }

    public StudentNode searchByNumber(int number) {

        return searchRecursive(root, number);
    }

    private StudentNode searchRecursive(StudentNode root, int number) {

        if (root == null || root.number == number) {
            return root;
        }

        if (number < root.number) {
            return searchRecursive(root.left, number);
        }

        return searchRecursive(root.right, number);
    }

    public void searchByName(StudentNode root, String keyword, ArrayList<String> results) {

        if (root != null) {

            searchByName(root.left, keyword, results);

            if (root.name.toLowerCase().contains(keyword.toLowerCase())) {

                results.add("Number: " + root.number + " - Name: " + root.name);
            }

            searchByName(root.right, keyword, results);
        }
    }

    public void listAscending(StudentNode root, ArrayList<String> list) {

        if (root != null) {

            listAscending(root.left, list);

            list.add("Number: " + root.number + " - Name: " + root.name);

            listAscending(root.right, list);
        }
    }
}

class StudentNode {

    int number;
    String name;

    StudentNode left, right;

    public StudentNode(int number, String name) {

        this.number = number;
        this.name = name;

        this.left = this.right = null;
    }
}
