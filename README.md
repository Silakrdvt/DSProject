# 🌳 BST Student Search System

![Java](https://img.shields.io/badge/Java-24-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![Algorithm](https://img.shields.io/badge/Algorithm-AVL%20(BST)-green?style=for-the-badge&logo=tree&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Updated-blue?style=for-the-badge)

> **Discrete Mathematics Project** — A student record management GUI backed by an AVL-balanced Binary Search Tree. The app supports safe insertion, controlled updates, validated inputs and fast searches.

---

## 📸 Preview

```
┌────────────────────────────────────────────────────────┐
│   Discrete Mathematics - Java BST System (AVL)         │
├────────────────────────────────────────────────────────┤
│  Student Number: [ 150          ]                      │
│  Student Name:   [ Ayşe         ]                      │
│                                                        │
│  [ Add ]  [ Update ]  [ Search ]  [ List Students ]    │
├────────────────────────────────────────────────────────┤
│  ✅ Added: 200 - Ahmet                                  │
│  ✅ Added: 100 - Mehmet                                 │
│  ⚠️ Student already exists.                             │
│  ♻️ Updated: 150 - Ayşe Kanmaz                          │
│  --- Student List (Ascending by Number) ---             │
│  Number: 100 - Name: Mehmet                            │
│  Number: 150 - Name: Ayşe Kanmaz                       │
│  Number: 200 - Name: Ahmet                             │
└────────────────────────────────────────────────────────┘
```

---

## 📚 Table of Contents

- [About the Project](#-about-the-project)
- [How BST Works](#-how-bst-works)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Classes & Methods](#-classes--methods)
- [Algorithms Used](#-algorithms-used)
- [Getting Started](#-getting-started)
- [How to Use](#-how-to-use)
- [Time Complexity](#-time-complexity)
- [Team](#-team)

---

## 🎯 About the Project

This project was developed for the **Discrete Mathematics / Discrete Structures** course. Instead of storing student records in a simple array or list, we built a **Binary Search Tree** — a mathematical tree structure where every node follows a strict ordering rule.

**Why BST instead of an Array?**

| | Array | BST |
|---|---|---|
| Search | O(n) — checks every item | O(log n) — eliminates half each step |
| Insert | O(1) — but unsorted | O(log n) — stays sorted automatically |
| Sorted Output | Needs extra sort algorithm | Free via Inorder Traversal |

---

## 🌳 How BST Works

The tree is ordered by **Student Number**. Every node follows one rule:

```
Left child  < Parent Node < Right child
```

**Example — Inserting: 200, 100, 300, 150**

```
Step 1: Insert 200       Step 2: Insert 100       Step 3: Insert 300
        [200]                   [200]                   [200]
                               /                        /    \
                             [100]                   [100]  [300]

Step 4: Insert 150
        [200]
       /     \
    [100]   [300]
        \
       [150]
```

**Searching for 150:**
```
200 → Is 150 < 200? Yes → go LEFT
100 → Is 150 > 100? Yes → go RIGHT
150 → Found! ✅  (only 3 steps out of 4 nodes)
```

---

### ✨ Features (Current)

- ✅ **Add Students** — Insert by number; rejects duplicate numbers (shows "Student already exists").
- ♻️ **Update Student** — Dedicated `Update` button that modifies the name for an existing student number.
- 🔍 **Search by Number** — Fast O(log n) search; when both number and name are entered, number search is used.
- 🔎 **Search by Name** — Full-tree inorder traversal with case-insensitive partial matches.
- 📋 **List All Students** — Sorted ascending via inorder traversal.
- 🔄 **AVL Rebalancing** — Tree auto-rebalances on insert (rotations) to keep operations near O(log n).
- 🛡️ **Input Validation** — Number must be a positive Java `int`; name must be non-empty.
- 🖥️ **Graphical Interface** — Java Swing GUI with clear messages for add/update/validation outcomes.

---

## 📁 Project Structure

```
dspro/
├── src/
│   └── main/
│       └── java/
│           └── com/mycompany/dspro/
│               └── Dspro.java          ← All classes in one file
├── pom.xml                             ← Maven build config
└── README.md
```

**Inside `Dspro.java` there are 3 classes:**

```
Dspro.java
├── class StudentNode      → The tree node (data + left/right links)
├── class StudentBST       → BST logic (add, search, list)
└── class Dspro (JFrame)   → GUI interface (buttons, inputs, display)
```

---

## 🔧 Classes & Methods (Updated)

### `StudentNode`
Represents a single student in the tree.

| Field | Type | Description |
|---|---|---|
| `number` | `int` | Student ID — used as BST ordering key |
| `name` | `String` | Student name |
| `left` | `StudentNode` | Link to left child (smaller number) |
| `right` | `StudentNode` | Link to right child (larger number) |

---

### `StudentBST`

| Method | Type | Description |
|---|---|---|
| `add(number, name)` | public | Inserts a student; returns status (`ADDED`, `EXISTS`, `INVALID`). Rebalances the tree after insert.
| `update(number, name)` | public | Updates the name for an existing number; returns status (`UPDATED`, `NOT_FOUND`, `INVALID`).
| `searchByNumber(number)` | public | Fast AVL/BST lookup.
| `searchByName(node, keyword, results)` | public | Inorder traversal that collects partial/name matches.
| `listAscending(node, list)` | public | Inorder traversal — outputs sorted student list.

---

### `Dspro` (JFrame)

The GUI class. Connects user actions to BST operations.

| Component | Role |
|---|---|
| `numberInput` | Text field for student number |
| `nameInput` | Text field for student name |
| `resultArea` | Read-only display area for all output |
| `addBtn` | Calls `bst.add()` |
| `searchBtn` | Detects if name or number is entered, calls appropriate search |
| `listBtn` | Calls `bst.listAscending()` and displays sorted results |

**Smart Search Logic:**
```java
// If name field has text → search by name (full traversal)
if (!nameText.isEmpty()) { bst.searchByName(...) }
// Otherwise → search by number (fast BST search)
else { bst.searchByNumber(...) }
```

---

## 🧠 Algorithms Used

### 1. Recursion
All three core operations (add, search by number, list) use **recursive functions** — each call handles one node and delegates left/right subtrees to itself.

```
Base case:  root == null  →  stop
Recursive:  go left or right, call self again
```

### 2. Inorder Traversal (Left → Root → Right)
Used in both `listAscending` and `searchByName`. Visiting nodes in this order produces values in **ascending order automatically** — no sorting needed.

```
listAscending(left) → add current → listAscending(right)
```

### 3. AVL (Self-Balancing BST)
Insertions trigger local rotations (single/double) to maintain balance factors. This keeps tree height near O(log n) and preserves fast search/insert in most cases.

---

## 🚀 Getting Started

### Prerequisites
- Java 24+
- Maven

### Run

```bash
git clone https://github.com/your-username/dspro.git
cd dspro
mvn compile exec:java
```

Or open in **NetBeans / IntelliJ** and run `Dspro.java` directly.

---

## 🖱️ How to Use (Updated)

| Action | How |
|---|---|
| Add a student | Enter a positive integer `number` and a non-empty `name` → click **Add**. If the number exists the operation is cancelled.
| Update a student | Enter existing `number` and new `name` → click **Update**.
| Search by number | Enter number (takes precedence if both fields filled) → click **Search**.
| Search by name | Enter name only → click **Search**.
| View all students sorted | Click **List Students**.

---

## ⏱️ Time Complexity

| Operation | Complexity | Explanation |
|---|---|---|
| Add | O(log n) avg | Halves the tree each step |
| Search by Number | O(log n) avg | Same as add — BST navigation |
| Search by Name | O(n) | Must visit every node |
| List Ascending | O(n) | Must visit every node once |

> **Worst case for BST:** O(n) if numbers are inserted in sorted order (tree becomes a straight line). This is why balanced trees like AVL exist — but that's beyond this project's scope.

---

## 👥 Team

Developed as a Discrete Mathematics / Discrete Structures course project.

| Role | Description |
|---|---|
| Algorithm Design | BST insert, search, traversal logic |
| GUI Development | Java Swing interface |
| Integration | Connecting BST logic to UI events |

---

## 📄 License

This project is for educational purposes — Discrete Mathematics course project.

---

*Built with ☕ Java — where every node knows its place.*
