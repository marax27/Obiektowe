package LibraryDB;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseModel {
    private Connection connection = null;
    private SQLException last_sql_error = null;

    //************************************************************

    public boolean connect() {
        DriverManager.setLoginTimeout(5);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/ktonia",
                    "ktonia", "=lab8zad2=");

            return true;

        } catch(SQLException exc){
            last_sql_error = exc;
        } catch(Exception exc){
            exc.printStackTrace();
        }
        return false;
    }

    //************************************************************

    public void close(){
        try{ connection.close(); } catch(Exception e){ /* ignore */ }

        connection = null;
        last_sql_error = null;
    }

    //************************************************************

    public LinkedList<Book> getBooks(){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            LinkedList<Book> result = new LinkedList<Book>();

            // Iterate over records.
            while(rs.next()){
                // Read a record.
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("isbn")
                );

                result.add(book);
            }

            st.close();
            rs.close();

            return result;

        }catch(SQLException exc){ last_sql_error = exc; }

        return null;
    }

    //************************************************************

    public LinkedList<Book> searchBooks(String value, String attribute){
        try {
            Statement st = connection.createStatement();
            LinkedList<Book> result = new LinkedList<Book>();

            String condition = null;
            switch(attribute){
                case "author":
                    condition = "author LIKE '% %" + value + "%'";
                    break;
                case "isbn":
                    condition = "isbn LIKE '%" + value + "%'";
                    break;
                default:
                    return result;
            }

            ResultSet rs = st.executeQuery("SELECT * FROM books WHERE " + condition);

            if(rs == null)
                return result;

            // Iterate over records.
            while(rs.next()){
                // Read a record.
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("isbn")
                );

                result.add(book);
            }

            st.close();
            rs.close();

            return result;

        }catch(SQLException exc){ last_sql_error = exc; }

        return null;
    }

    //************************************************************

    public boolean addBook(Book book){
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(
                    "INSERT INTO books (title, author, year, isbn) VALUES "
                            + String.format("('%s', '%s', %d, '%s')", book.getTitle(), book.getAuthor(), book.getYear(), book.getISBN())
            );
        }catch(SQLException exc){
            last_sql_error = exc;
            return false;
        }

        return true;
    }

    //************************************************************

    public SQLException getError(){
        return last_sql_error;
    }
}
