package dsproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

    private StudentBST bst = new StudentBST();
    private JTextField numberInput, nameInput;
    private JTextArea resultArea;

    public MainFrame() {

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
            } // SEARCH BY NUMBER
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
}
