package com.example.proekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    private ObservableList<MainTable> events = FXCollections.observableArrayList();

    @FXML private Button authBtn;
    @FXML private TableView<MainTable> mainTable;
    @FXML private TableColumn<MainTable, String> nameClm;
    @FXML private TableColumn<MainTable, String> dirClm;
    @FXML private TableColumn<MainTable, String> dateClm;

    @FXML
    void initialize(){
        initEvent();
        nameClm.setCellValueFactory(new PropertyValueFactory<MainTable, String>("name"));
        dateClm.setCellValueFactory(new PropertyValueFactory<MainTable, String>("date"));
        mainTable.setItems(events);
        authBtn.setOnAction(event ->{
            try{
                HelloApplication.openAnotherWindow("authorization.fxml");
            }catch (IOException ex){
                ex.printStackTrace();
            }
            authBtn.getScene().getWindow().hide();
        });
    }

    private void initEvent() {
        try{
            dbConnection = getDbConnection();
            ResultSet resSet = dbConnection.createStatement().executeQuery("SELECT Событие, Дата FROM worldskills.events");
            while(resSet.next()){
                events.add(new MainTable(resSet.getString("Событие"), resSet.getString("Дата")));
            }
        }catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localHost:3306/worldskills";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "1234");
        return dbConnection;
    }
}