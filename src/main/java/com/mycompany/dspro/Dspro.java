package com.mycompany.dspro;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// --- 1. MODULE: BST ALGORITHM ---
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

class StudentBST {

    StudentNode root;

    // ADD STUDENT
    public void add(int number, String name) {

        root = addRecursive(root, number, name);
    }

    private StudentNode addRecursive(StudentNode root, int number, String name) {

        if (root == null)
            return new StudentNode(number, name);

        if (number < root.number)
            root.left = addRecursive(root.left, number, name);

        else if (number > root.number)
            root.right = addRecursive(root.right, number, name);

        return root;
    }

    // SEARCH BY NUMBER
    public StudentNode searchByNumber(int number) {

        return searchRecursive(root, number);
    }

    private StudentNode searchRecursive(StudentNode root, int number) {

        if (root == null || root.number == number)
            return root;

        if (number < root.number)
            return searchRecursive(root.left, number);

        return searchRecursive(root.right, number);
    }

    // SEARCH BY NAME
    public void searchByName(StudentNode root, String keyword, ArrayList<String> results) {

        if (root != null) {

            searchByName(root.left, keyword, results);

            if (root.name.toLowerCase().contains(keyword.toLowerCase())) {

                results.add("Number: " + root.number + " - Name: " + root.name);
            }

            searchByName(root.right, keyword, results);
        }
    }

    // LIST STUDENTS FROM SMALLEST TO BIGGEST NUMBER
    public void listAscending(StudentNode root, ArrayList<String> list) {

        if (root != null) {

            listAscending(root.left, list);

            list.add("Number: " + root.number + " - Name: " + root.name);

            listAscending(root.right, list);
        }
    }
}

// --- 2. MODULE: INTERFACE ---
public class Dspro extends JFrame {

    private StudentBST bst = new StudentBST();

    private JTextField numberInput, nameInput;

    private JTextArea resultArea;

    public Dspro() {

        setTitle("Discrete Mathematics - Java BST System");

        setSize(550, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // INPUT PANEL
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel(" Student Number:"));

        numberInput = new JTextField();

        inputPanel.add(numberInput);

        inputPanel.add(new JLabel(" Student Name:"));

        nameInput = new JTextField();

        inputPanel.add(nameInput);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add");

        JButton searchBtn = new JButton("Search");

        JButton listBtn = new JButton("List Students");

        buttonPanel.add(addBtn);

        buttonPanel.add(searchBtn);

        buttonPanel.add(listBtn);

        // RESULT AREA
        resultArea = new JTextArea();

        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        scrollPane.setPreferredSize(new Dimension(500, 200));

        add(inputPanel, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.CENTER);

        add(scrollPane, BorderLayout.SOUTH);

        // ADD BUTTON
        addBtn.addActionListener(e -> {

            try {

                int number = Integer.parseInt(numberInput.getText());

                String name = nameInput.getText();

                if (!name.isEmpty()) {

                    bst.add(number, name);

                    resultArea.append("✅ Added: " + number + " - " + name + "\n");

                    numberInput.setText("");

                    nameInput.setText("");
                }

            } catch (Exception ex) {

                resultArea.append("⚠️ Error: Invalid input!\n");
            }
        });

        // SEARCH BUTTON
        searchBtn.addActionListener(e -> {

            String nameText = nameInput.getText().trim();

            // SEARCH BY NAME
            if (!nameText.isEmpty()) {

                ArrayList<String> results = new ArrayList<>();

                bst.searchByName(bst.root, nameText, results);

                if (results.size() > 0) {

                    resultArea.append("🔍 Matching Students:\n");

                    for (String s : results) {

                        resultArea.append(s + "\n");
                    }

                } else {

                    resultArea.append("❌ No matching student found.\n");
                }
            }

            // SEARCH BY NUMBER
            else {

                try {

                    int number = Integer.parseInt(numberInput.getText());

                    StudentNode result = bst.searchByNumber(number);

                    if (result != null) {

                        resultArea.append("🔍 Found: " + result.number + " - " + result.name + "\n");

                    } else {

                        resultArea.append("❌ Student not found.\n");
                    }

                } catch (Exception ex) {

                    resultArea.append("⚠️ Error: Enter number or name!\n");
                }
            }
        });

        // LIST BUTTON
        listBtn.addActionListener(e -> {

            ArrayList<String> list = new ArrayList<>();

            bst.listAscending(bst.root, list);

            resultArea.setText("--- Student List (Ascending by Number) ---\n");

            for (String s : list) {

                resultArea.append(s + "\n");
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Dspro().setVisible(true));
    }
}