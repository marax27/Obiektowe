package LibraryDB;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.LinkedList;

public class BookTableView extends TableView {

    public void setBooks(LinkedList<Book> book_list){
        ObservableList<Book> list = getItems();
        list.clear();

        for(Book i : book_list)
            list.add(i);
    }
}
