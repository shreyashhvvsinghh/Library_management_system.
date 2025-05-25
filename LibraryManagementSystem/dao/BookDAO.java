package dao;

import model.Book;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookDAO {
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM books");

        while (rs.next()) {
            books.add(new Book(rs.getInt("id"), rs.getString("title"),
                    rs.getString("author"), rs.getBoolean("is_issued")));
        }
        return books;
    }

    public void addBook(String title, String author) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)");
        ps.setString(1, title);
        ps.setString(2, author);
        ps.executeUpdate();
    }

    public void issueBook(int id) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE books SET is_issued = TRUE WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void returnBook(int id) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE books SET is_issued = FALSE WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}