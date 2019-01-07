package Labb3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class SokningKlass extends Application {

    Stage window;

    BorderPane root = new BorderPane();
    private int antalSokresultat;

    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<Superfigur> skapaSuperfigurArrayList(){
        ArrayList<Superfigur> superfigurArrayList = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver did not load");
        }
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/Superheroes?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root", "yeyeye")) {
            System.out.println("Connected");

            //Hämta data från tabellerna "superfigur" och "ingar_i"
            Statement statement = conn.createStatement();
            ResultSet rs_superfigur = statement.executeQuery(
                    "SELECT superfigur.Alias, Fnamn, Enamn, Inriktning, Beskrivning, Unamn FROM superfigur " +
                            "JOIN ingar_i ii ON superfigur.Alias = ii.Alias");

            String alias;
            String fnamn;
            String enamn;
            String inriktning;
            String beskrivning;
            String universum;

            while(rs_superfigur.next()) {
                alias = rs_superfigur.getString(1);
                fnamn = rs_superfigur.getString(2 );
                enamn = rs_superfigur.getString(3);
                inriktning = rs_superfigur.getString(4);
                beskrivning = rs_superfigur.getString(5);
                universum = rs_superfigur.getString(6);

                //Skapa Superfigur-object och lägg till värden
                superfigurArrayList.add(new Superfigur(alias, fnamn, enamn, inriktning, beskrivning, universum));

            }
            //Hämta data från tabellen "superfigur_tillhör"
            Statement statement2 = conn.createStatement();
            ResultSet rs_organisation = statement2.executeQuery(
                    "SELECT Alias, Onamn, Status FROM superfigur_tillhor");

            while (rs_organisation.next()){
                for (Superfigur s : superfigurArrayList){
                    if ((s.getAlias().equals(rs_organisation.getString(1)) && (rs_organisation.getString(3).equals("Nuvarande")))){
                        s.setNuvarandeOrg(rs_organisation.getString(2));
                    }

                    if ((s.getAlias().equals(rs_organisation.getString(1)) && (rs_organisation.getString(3).equals("Tidigare")))){
                        s.addTidigareOrg(rs_organisation.getString(2));
                    }
                }
            }
            Statement statement3 = conn.createStatement();
            ResultSet rs_skapare = statement3.executeQuery(
                    "SELECT superfigur_har.Alias, CONCAT(skapare.Fnamn, ' ', skapare.Enamn) " +
                            "FROM superfigur_har " +
                            "JOIN skapare ON superfigur_har.SkapareID = skapare.SkapareID");

            while (rs_skapare.next()){
                for (Superfigur s : superfigurArrayList) {

                    if (s.getAlias().equals(rs_skapare.getString(1))) {
                        s.addSkapare(rs_skapare.getString(2));
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong..." + ex.getMessage());
        }

        return superfigurArrayList;
    }

    @Override
    public void start(Stage primaryStage) {


        //--------------------Hämta info om superfigurer och lägg till i en datastruktur--------------------


        //Test att skriva ut all info
       /* ArrayList<Superfigur> superfigurArrayList = skapaSuperfigurArrayList();

        for (Superfigur s : superfigurArrayList){
            System.out.print("Alias: " + s.getAlias() + " | Förnamn: " + s.getFnamn() + " | Efternamn: " + s.getEnamn() + " | Inriktning: " + s.getInriktning() + " | Nuvarande organisation: " + s.getNuvarandeOrg() + " | Universum: " + s.getUniversum() + " |\nTidigare organisationer: ");

            for (String to : s.getTidigareOrg()){
                System.out.print(to + ", ");
            }
            System.out.print(" | Skapare: ");

            for (String sk : s.getSkapareArr()) {
                System.out.print(sk + ", ");
            }
            System.out.println("\n");
        }*/

        window = primaryStage;

        ArrayList<Superfigur> superfigurArrayList = skapaSuperfigurArrayList();

        //Mainpage
        //VBox layout = new VBox();
        ToolBar toolBar = new ToolBar();
        Button knapp = new Button("Se medlemmar");
        knapp.setPrefSize(150,35);
        Button knapp2 = new Button("Lägg till medlem");
        knapp2.setPrefSize(150,35);
        Button knapp3 = new Button("Sök");
        knapp3.setPrefSize(150,35);


        TextField sokningTF = new TextField();
        sokningTF.setMaxWidth(100);
        Button sokButton = new Button("Sök");
        HBox sokningHBox = new HBox();
        sokningHBox.setPadding(new Insets(5));
        sokningHBox.getChildren().addAll(sokningTF, sokButton);

        ListView<String> lv_sokresultat = new ListView<>();

        Text alias = new Text();
        alias.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        Text fnamn = new Text();
        Text enamn = new Text();
        Text inriktning = new Text();
        Text beskrivning = new Text();
        beskrivning.setWrappingWidth(200);
        Text nuvarandeOrg = new Text();
        Text universum = new Text();
        Text skapare = new Text();
        Text tidigareOrg = new Text();

        Text fnamn_label = new Text();
        fnamn_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text enamn_label = new Text();
        enamn_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text inriktning_label = new Text();
        inriktning_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text beskrivning_label = new Text();
        beskrivning_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text nuvarandeOrg_label = new Text();
        nuvarandeOrg_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text universum_label = new Text();
        universum_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text skapare_label = new Text();
        skapare_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        Text tidigareOrg_label = new Text();
        tidigareOrg_label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));


        HBox alias_Hbox = new HBox(alias);
        alias_Hbox.setPadding(new Insets(5));
        HBox hBoxFnamn = new HBox(fnamn_label, fnamn);
        hBoxFnamn.setPadding(new Insets(5));
        HBox hBoxEnamn = new HBox(enamn_label, enamn);
        hBoxEnamn.setPadding(new Insets(5));
        HBox hBoxInriktning = new HBox(inriktning_label, inriktning);
        hBoxInriktning.setPadding(new Insets(5));
        HBox hBoxBeskrivning = new HBox(beskrivning_label, beskrivning);
        hBoxBeskrivning.setPadding(new Insets(5));
        HBox hBoxNuvarandeOrg = new HBox(nuvarandeOrg_label, nuvarandeOrg);
        hBoxNuvarandeOrg.setPadding(new Insets(5));
        HBox hBoxUniversum = new HBox(universum_label, universum);
        hBoxUniversum.setPadding(new Insets(5));
        HBox hBoxSkapere = new HBox(skapare_label, skapare);
        hBoxSkapere.setPadding(new Insets(5));
        HBox hBoxTidigareOrg = new HBox(tidigareOrg_label, tidigareOrg);
        hBoxTidigareOrg.setPadding(new Insets(5));

        VBox samladInfo = new VBox(alias_Hbox, hBoxFnamn, hBoxEnamn, hBoxInriktning, hBoxNuvarandeOrg, hBoxUniversum, hBoxSkapere, hBoxTidigareOrg, hBoxBeskrivning);
        samladInfo.setPadding(new Insets(10));

        //info.getChildren().addAll(samladInfo);
        toolBar.getItems().addAll(knapp, knapp2, sokningHBox);

        VBox vBox = new VBox();
        Label sokresultat = new Label();
        sokresultat.setPadding(new Insets(5));
        vBox.getChildren().addAll(sokresultat, lv_sokresultat);
        root.setTop(toolBar);
        root.setCenter(samladInfo);

        sokButton.setOnAction(event -> {
            String sokord = sokningTF.getText().toLowerCase();
            root.setLeft(vBox);
            root.setCenter(samladInfo);

            lv_sokresultat.getItems().clear();

            if (!sokord.isEmpty() && !sokord.equals(" ")) {
                for (Superfigur sf : superfigurArrayList) {
                    if ((sf.getAlias().toLowerCase().contains(sokord)) || ((sf.getFnamn() + " " + sf.getEnamn()).toLowerCase().contains(sokord)) || (sf.getInriktning().toLowerCase().equals(sokord))
                            || (sf.getUniversum().toLowerCase().equals(sokord)) || (sf.getSkapareArr().toString().toLowerCase().contains(sokord))) {
                        lv_sokresultat.getItems().addAll(sf.getAlias());
                        antalSokresultat++;
                    }
                }
                sokresultat.setText("Antal träffar: " + antalSokresultat);
                if (antalSokresultat == 0){
                    sokresultat.setText("Inga resultat hittades för \"" + sokningTF.getText() + "\"");
                }
                antalSokresultat = 0;
            }

            else {
                sokresultat.setText("Skriv ett sökord i sökrutan");
            }
        });

        lv_sokresultat.getSelectionModel().selectedIndexProperty().addListener(e ->{
            String alias_nuvarande = lv_sokresultat.getSelectionModel().getSelectedItem();

            for (Superfigur sf : superfigurArrayList){
                if (sf.getAlias().equals(alias_nuvarande)){
                    alias.setText(sf.getAlias());
                    fnamn.setText(sf.getFnamn());
                    enamn.setText(sf.getEnamn());
                    inriktning.setText(sf.getInriktning());
                    beskrivning.setText(sf.getBeskrivning());
                    nuvarandeOrg.setText(sf.getNuvarandeOrg());
                    universum.setText(sf.getUniversum());
                    skapare.setText("N/A");

                    if (!sf.getSkapareArr().isEmpty()){
                        skapare.setText("");
                        for (int i = 0; i < sf.getSkapareArr().size(); i++){
                            skapare.setText(skapare.getText() + sf.getSkapareArr().get(i));
                            if (i < sf.getSkapareArr().size() - 1){
                                skapare.setText(skapare.getText() + ", ");
                            }
                        }
                    }

                    tidigareOrg.setText("N/A");
                    if (!sf.getTidigareOrg().isEmpty()){
                        tidigareOrg.setText("");
                        for (int i = 0; i < sf.getTidigareOrg().size(); i++){
                            tidigareOrg.setText(tidigareOrg.getText() + sf.getTidigareOrg().get(i));
                            if (i < sf.getTidigareOrg().size() - 1){
                                tidigareOrg.setText(tidigareOrg.getText() + ", ");
                            }
                        }
                    }

                    fnamn_label.setText("Förnamn: ");
                    enamn_label.setText("Efternamn: ");
                    inriktning_label.setText("Inriktning: ");
                    beskrivning_label.setText("Beskrivning: ");
                    nuvarandeOrg_label.setText("Organisation: ");
                    universum_label.setText("Universum: ");
                    skapare_label.setText("Skapare: ");
                    tidigareOrg_label.setText("Tidigare organisationer: ");
                }
            }
        });


        Text welcome = new Text("Välkommen till Superhero Manager");
        welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        root.setCenter(welcome);

        Scene scene = new Scene(root, 750, 500);

        TextField test = new TextField("Testing");
        Label test2 = new Label("Tesssting");


        knapp.setOnAction(event -> {
            root.getChildren().clear();
            root.setTop(toolBar);
            root.setLeft(test);

        });

        knapp2.setOnAction(event -> {
            root.getChildren().clear();
            root.setTop(toolBar);
            root.setLeft(test2);
        });

        window.setScene(scene);
        window.setTitle("Program");
        window.show();
    }
}
