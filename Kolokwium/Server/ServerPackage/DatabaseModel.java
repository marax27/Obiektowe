package ServerPackage;

import java.sql.*;
import java.sql.SQLException;

public class DatabaseModel {
    private Connection connection = null;
    private SQLException last_sql_error = null;

    public void finalize(){
        close();
    }

    public boolean connect(){
        DriverManager.setLoginTimeout(5);
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://mysql.agh.edu.pl/ktonia", "ktonia", "=JavaJAVAjava="
            );
            return true;
        }catch(SQLException e){
            last_sql_error = e;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRecord(Winner winner){
        try{
            System.out.println(">>>");
            Statement st = connection.createStatement();
            System.out.println(">>>");
            st.executeUpdate(
                "INSERT INTO winners (name) VALUES ("
                + winner.getName() + ");"
            );
            System.out.println(">>>");
        }catch(SQLException exc){
            last_sql_error = exc;
            return false;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void close(){
        try{ connection.close(); } catch(Exception e){}
        connection = null;
        last_sql_error = null;
    }

    public SQLException getError(){
        return last_sql_error;
    }
}
