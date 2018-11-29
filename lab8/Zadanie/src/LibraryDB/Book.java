package LibraryDB;

public class Book {
    // Constructors.
    public Book(String _title, String _author, int _year, String _isbn){
        title = _title;
        author = _author;
        year = _year;
        isbn = _isbn;
    }

    public Book(){}

    // Getters/Setters.
    public void setTitle(String s){ title = s; }
    public String getTitle(){ return title; }

    public void setAuthor(String s){ author = s; }
    public String getAuthor(){ return author; }

    public void setYear(int y){ year = y; }
    public int getYear(){ return year; }

    public void setISBN(String s){ isbn = s; }
    public String getISBN(){ return isbn; }

    // Fields.
    private String title;
    private String author;
    private int    year;
    private String isbn;
}
