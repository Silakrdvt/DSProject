package dsproject;

import java.util.ArrayList;

enum AddStatus {
    ADDED,
    EXISTS,
    INVALID
}

enum UpdateStatus {
    UPDATED,
    NOT_FOUND,
    INVALID
}

class StudentBST {

    StudentNode root;

    public AddStatus add(int number, String name) {
        if (number <= 0) {
            return AddStatus.INVALID;
        }

        if (name == null || name.trim().isEmpty()) {
            return AddStatus.INVALID;
        }

        AddOutcome outcome = addRecursive(root, number, name.trim());
        root = outcome.node;
        return outcome.status;
    }

    private AddOutcome addRecursive(StudentNode root, int number, String name) {

        if (root == null) {
            return new AddOutcome(new StudentNode(number, name), AddStatus.ADDED);
        }

        if (number < root.number) {
            AddOutcome outcome = addRecursive(root.left, number, name);
            root.left = outcome.node;
            root = rebalance(root);
            return new AddOutcome(root, outcome.status);
        }

        if (number > root.number) {
            AddOutcome outcome = addRecursive(root.right, number, name);
            root.right = outcome.node;
            root = rebalance(root);
            return new AddOutcome(root, outcome.status);
        }

        return new AddOutcome(root, AddStatus.EXISTS);
    }

    public UpdateStatus update(int number, String name) {
        if (number <= 0) {
            return UpdateStatus.INVALID;
        }

        if (name == null || name.trim().isEmpty()) {
            return UpdateStatus.INVALID;
        }

        StudentNode node = searchRecursive(root, number);
        if (node == null) {
            return UpdateStatus.NOT_FOUND;
        }

        node.name = name.trim();
        return UpdateStatus.UPDATED;
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

        if (root == null || keyword == null || results == null) {
            return;
        }

        String normalizedKeyword = keyword.trim().toLowerCase();
        if (normalizedKeyword.isEmpty()) {
            return;
        }

        searchByName(root.left, normalizedKeyword, results);

        if (root.name != null && root.name.toLowerCase().contains(normalizedKeyword)) {

            results.add("Number: " + root.number + " - Name: " + root.name);
        }

        searchByName(root.right, normalizedKeyword, results);
    }

    public void listAscending(StudentNode root, ArrayList<String> list) {

        if (root == null || list == null) {
            return;
        }

        listAscending(root.left, list);

        list.add("Number: " + root.number + " - Name: " + root.name);

        listAscending(root.right, list);
    }

    private StudentNode rebalance(StudentNode node) {

        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private StudentNode rotateRight(StudentNode y) {

        StudentNode x = y.left;
        StudentNode t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private StudentNode rotateLeft(StudentNode x) {

        StudentNode y = x.right;
        StudentNode t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private void updateHeight(StudentNode node) {

        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private int height(StudentNode node) {

        return node == null ? 0 : node.height;
    }

    private int getBalance(StudentNode node) {

        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private static class AddOutcome {

        private final StudentNode node;
        private final AddStatus status;

        private AddOutcome(StudentNode node, AddStatus status) {

            this.node = node;
            this.status = status;
        }
    }

    public void buildTreeString(StudentNode node, String prefix, boolean isLeft, StringBuilder sb) {
        if (root == null) {
            return;
        }
        buildVerticalTree(root, "", true, sb);
    }

    private void buildVerticalTree(StudentNode node, String indent, boolean isLeft, StringBuilder sb) {
        if (node == null) {
            return;
        }

        buildVerticalTree(node.right, indent + (isLeft ? "│   " : "    "), false, sb);

        sb.append(indent)
                .append(isLeft ? "└── " : "┌── ")
                .append(node.number)
                .append(" - ")
                .append(node.name)
                .append("\n");

        buildVerticalTree(node.left, indent + (isLeft ? "    " : "│   "), true, sb);
    }
}

class StudentNode {

    int number;
    String name;
    int height;

    StudentNode left, right;

    public StudentNode(int number, String name) {

        this.number = number;
        this.name = name;
        this.height = 1;

        this.left = this.right = null;
    }
}
