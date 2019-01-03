package Labb4;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class Superfigur extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Array av namnen
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver did not load");
        }
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/Superheroes?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root", "xxx")) {
            System.out.println("Connected");

            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("SELECT Fnamn from superfigur");

            ArrayList<String> superfigurerNamn = new ArrayList<>();

            while(rs.next()) {
                superfigurerNamn.add(rs.getString(1));
            }

            for (String s : superfigurerNamn){
                System.out.println(s);
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }
    }
}
