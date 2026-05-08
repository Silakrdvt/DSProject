/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dspro;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// --- 1. MODÜL: BST ALGORİTMASI ---
class OgrenciNode {
    int no;
    String isim;
    OgrenciNode sol, sag;

    public OgrenciNode(int no, String isim) {
        this.no = no;
        this.isim = isim;
        this.sol = this.sag = null;
    }
}

class OgrenciBST {
    OgrenciNode root;

    public void ekle(int no, String isim) {
        root = ekleRecursive(root, no, isim);
    }

    private OgrenciNode ekleRecursive(OgrenciNode root, int no, String isim) {
        if (root == null) return new OgrenciNode(no, isim);
        if (no < root.no) root.sol = ekleRecursive(root.sol, no, isim);
        else if (no > root.no) root.sag = ekleRecursive(root.sag, no, isim);
        return root;
    }

    public OgrenciNode ara(int no) {
        return araRecursive(root, no);
    }

    private OgrenciNode araRecursive(OgrenciNode root, int no) {
        if (root == null || root.no == no) return root;
        if (no < root.no) return araRecursive(root.sol, no);
        return araRecursive(root.sag, no);
    }

    public void siraliListele(OgrenciNode root, ArrayList<String> liste) {
        if (root != null) {
            siraliListele(root.sol, liste);
            liste.add("No: " + root.no + " - İsim: " + root.isim);
            siraliListele(root.sag, liste);
        }
    }
}

// --- 2. MODÜL: ARAYÜZ (Senin sınıf ismin olan Dspro'yu buraya yazdım) ---
public class Dspro extends JFrame {
    private OgrenciBST bst = new OgrenciBST();
    private JTextField noInput, isimInput;
    private JTextArea sonucAlani;

    public Dspro() {
        setTitle("Ayrık Matematik - Java BST Sistemi");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel girisPaneli = new JPanel(new GridLayout(3, 2));
        girisPaneli.add(new JLabel(" Öğrenci No:"));
        noInput = new JTextField();
        girisPaneli.add(noInput);
        girisPaneli.add(new JLabel(" Öğrenci Adı:"));
        isimInput = new JTextField();
        girisPaneli.add(isimInput);

        JPanel butonPaneli = new JPanel();
        JButton ekleBtn = new JButton("Ekle");
        JButton araBtn = new JButton("Ara");
        JButton listeleBtn = new JButton("Sıralı Listele");
        butonPaneli.add(ekleBtn);
        butonPaneli.add(araBtn);
        butonPaneli.add(listeleBtn);

        sonucAlani = new JTextArea();
        sonucAlani.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(sonucAlani);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        add(girisPaneli, BorderLayout.NORTH);
        add(butonPaneli, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        ekleBtn.addActionListener(e -> {
            try {
                int no = Integer.parseInt(noInput.getText());
                String isim = isimInput.getText();
                if (!isim.isEmpty()) {
                    bst.ekle(no, isim);
                    sonucAlani.append("✅ Eklendi: " + no + " - " + isim + "\n");
                    noInput.setText(""); isimInput.setText("");
                }
            } catch (Exception ex) { sonucAlani.append("⚠️ Hata: Geçersiz giriş!\n"); }
        });

        araBtn.addActionListener(e -> {
            try {
                int no = Integer.parseInt(noInput.getText());
                OgrenciNode sonuc = bst.ara(no);
                if (sonuc != null) sonucAlani.append("🔍 Bulundu: " + sonuc.no + " - " + sonuc.isim + "\n");
                else sonucAlani.append("❌ " + no + " numaralı öğrenci yok.\n");
            } catch (Exception ex) { sonucAlani.append("⚠️ Hata: Numara girin!\n"); }
        });

        listeleBtn.addActionListener(e -> {
            ArrayList<String> liste = new ArrayList<>();
            bst.siraliListele(bst.root, liste);
            sonucAlani.setText("--- Sıralı Liste (Inorder) ---\n");
            for (String s : liste) sonucAlani.append(s + "\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dspro().setVisible(true));
    }
}



