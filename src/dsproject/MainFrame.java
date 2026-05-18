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

        setTitle("Student Registry");
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
        JButton updateBtn = new JButton("Update");
        JButton listBtn = new JButton("List Students");
        buttonPanel.add(addBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
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
                String numText = numberInput.getText().trim();
                if (numText.isEmpty()) {
                    resultArea.append("⚠️ Error: Number is required.\n");
                    return;
                }

                long parsed = Long.parseLong(numText);
                if (parsed > Integer.MAX_VALUE || parsed < Integer.MIN_VALUE) {
                    resultArea.append("⚠️ Error: Number out of Java int range.\n");
                    return;
                }

                int number = (int) parsed;
                if (number <= 0) {
                    resultArea.append("⚠️ Error: Number must be positive.\n");
                    return;
                }

                String name = nameInput.getText();

                AddStatus status = bst.add(number, name);
                if (status == AddStatus.ADDED) {
                    resultArea.append("✅ Added: " + number + " - " + name + "\n");
                    numberInput.setText("");
                    nameInput.setText("");
                } else if (status == AddStatus.EXISTS) {
                    resultArea.append("⚠️ Student already exists.\n");
                } else {
                    resultArea.append("⚠️ Error: Name is required or number invalid.\n");
                }
            } catch (NumberFormatException nfe) {
                resultArea.append("⚠️ Error: Number must be an integer.\n");
            } catch (Exception ex) {
                resultArea.append("⚠️ Error: Invalid input!\n");
            }
        });

        // UPDATE BUTTON
        updateBtn.addActionListener(e -> {
            try {
                String numText = numberInput.getText().trim();
                if (numText.isEmpty()) {
                    resultArea.append("⚠️ Error: Number is required.\n");
                    return;
                }

                long parsed = Long.parseLong(numText);
                if (parsed > Integer.MAX_VALUE || parsed < Integer.MIN_VALUE) {
                    resultArea.append("⚠️ Error: Number out of Java int range.\n");
                    return;
                }

                int number = (int) parsed;
                if (number <= 0) {
                    resultArea.append("⚠️ Error: Number must be positive.\n");
                    return;
                }

                String name = nameInput.getText();

                UpdateStatus status = bst.update(number, name);
                if (status == UpdateStatus.UPDATED) {
                    resultArea.append("♻️ Updated: " + number + " - " + name + "\n");
                    numberInput.setText("");
                    nameInput.setText("");
                } else if (status == UpdateStatus.NOT_FOUND) {
                    resultArea.append("❌ Student not found.\n");
                } else {
                    resultArea.append("⚠️ Error: Name is required or number invalid.\n");
                }
            } catch (NumberFormatException nfe) {
                resultArea.append("⚠️ Error: Number must be an integer.\n");
            } catch (Exception ex) {
                resultArea.append("⚠️ Error: Invalid input!\n");
            }
        });

        // SEARCH BUTTON
        searchBtn.addActionListener(e -> {

            String numText = numberInput.getText().trim();
            String nameText = nameInput.getText().trim();

            // If number provided, always search by number
            if (!numText.isEmpty()) {
                try {
                    long parsed = Long.parseLong(numText);
                    if (parsed > Integer.MAX_VALUE || parsed < Integer.MIN_VALUE) {
                        resultArea.append("⚠️ Error: Number out of Java int range.\n");
                        return;
                    }

                    int number = (int) parsed;
                    if (number <= 0) {
                        resultArea.append("⚠️ Error: Number must be positive.\n");
                        return;
                    }

                    StudentNode result = bst.searchByNumber(number);
                    if (result != null) {
                        resultArea.append("🔍 Found: " + result.number + " - " + result.name + "\n");
                    } else {
                        resultArea.append("❌ Student not found.\n");
                    }
                } catch (NumberFormatException nfe) {
                    resultArea.append("⚠️ Error: Number must be an integer.\n");
                }

                return;
            }

            // Otherwise, if name provided, search by name
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

                return;
            }

            resultArea.append("⚠️ Error: Enter number or name!\n");
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
