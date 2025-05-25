package ui;

import dao.BookDAO;
import model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LibraryUI extends JFrame {
    private BookDAO dao = new BookDAO();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> bookList = new JList<>(listModel);

    public LibraryUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JTextField titleField = new JTextField(10);
        JTextField authorField = new JTextField(10);
        JButton addBtn = new JButton("Add Book");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        topPanel.add(new JLabel("Title:"));
        topPanel.add(titleField);
        topPanel.add(new JLabel("Author:"));
        topPanel.add(authorField);
        topPanel.add(addBtn);
        topPanel.add(issueBtn);
        topPanel.add(returnBtn);
        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(bookList), BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            try {
                dao.addBook(titleField.getText(), authorField.getText());
                refreshList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        issueBtn.addActionListener(e -> {
            try {
                int index = bookList.getSelectedIndex();
                if (index != -1) dao.issueBook(index + 1);
                refreshList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int index = bookList.getSelectedIndex();
                if (index != -1) dao.returnBook(index + 1);
                refreshList();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        refreshList();
        setVisible(true);
    }

    private void refreshList() {
        listModel.clear();
        try {
            List<Book> books = dao.getAllBooks();
            for (Book book : books) {
                listModel.addElement(book.getId() + ". " + book.getTitle() + " - " + book.getAuthor() + (book.isIssued() ? " (Issued)" : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}