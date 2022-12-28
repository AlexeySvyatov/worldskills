package com.example.proekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Organizator {
    @FXML private CheckBox checkPas;
    @FXML private Pane paneMyProf;
    @FXML private Pane paneMerop;
    @FXML private Button btnMerop;
    @FXML private Button btnMyProf;
    @FXML private Button btnJury;
    @FXML private Button btnUchast;
    @FXML private Button btnOkProf;
    @FXML private Button btnOtmProf;
    @FXML private Button btnSaveM;
    @FXML private Button btnOtmM;
    @FXML private PasswordField tfPass;
    @FXML private PasswordField tfPass1;
    @FXML private TextField tfDays;
    @FXML private TextField tfDay;
    @FXML private TextField tfH;
    @FXML private TextField tfMin;
    @FXML private TextField tfNameM;
    @FXML private TextField tfNameAct;
    @FXML private Label lbError;
    @FXML private Label lbPrivetTime;
    @FXML private Label lbPrivetName;
    @FXML private Label lbFIO;
    @FXML private Label lbCountry;
    @FXML private Label lbNumber;
    @FXML private Label lbEmail;
    @FXML private Label lbID;
    @FXML private Label lbBdate;
    @FXML private Label lbPol;
    @FXML private Label lbErrorPas;
    @FXML private ImageView imageID;
    @FXML private ComboBox<String> comboMod;
    @FXML private ComboBox<String> comboJ1;
    @FXML private ComboBox<String> comboJ2;
    @FXML private ComboBox<String> comboJ3;
    @FXML private ComboBox<String> comboJ4;
    @FXML private ComboBox<String> comboJ5;
    @FXML private ComboBox<String> comboWin;
    @FXML private DatePicker comboDate;
    public String picID;

    private final ObservableList<String> moderatorData = FXCollections.observableArrayList();
    private final ObservableList<String> juryData = FXCollections.observableArrayList();
    private final ObservableList<String> winData = FXCollections.observableArrayList();
    private String selectModer = "";
    private String selectWinner = "";
    private String selectJury1 = "";
    private String selectJury2 = "";
    private String selectJury3 = "";
    private String selectJury4 = "";
    private String selectJury5 = "";


    @FXML
    void initialize() throws SQLException {
        showTime();
        moderCombo();
        juryCombo();
        winCombo();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                    "root", "Qwerty123456");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM organizator WHERE `id_organizator` = " + Authorization.idOrg);
            while (resultSet.next()) {
                lbPrivetName.setText(resultSet.getString("ФИО"));
            }
        } catch (Exception e) {
            System.out.println("Ошибка с приветствием");
        }

        btnOkProf.setOnAction(event -> {
            if(checkPas.isSelected() && !(tfPass.getText().isEmpty() || tfPass1.getText().isEmpty())
                    && Objects.equals(tfPass.getText(), tfPass1.getText())){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                            "root", "Qwerty123456")) {
                        PreparedStatement statement = conn.prepareStatement
                                ("UPDATE organizator SET пароль = '" + tfPass.getText() + "' WHERE id_organizator = " + Authorization.idOrg);
                        statement.executeUpdate();
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка с изменением пароля");
                }
                paneMyProf.setVisible(false);
                lbErrorPas.setText("");}
            else if (!(checkPas.isSelected())) {
                paneMyProf.setVisible(false);
                lbErrorPas.setText("");}
            else {lbErrorPas.setText("Проверьте пароль");}
        });

        btnMyProf.setOnAction(event -> {
            paneMyProf.setVisible(true);
            showMyInfo(Authorization.idOrg);
        });
        btnOtmProf.setOnAction(event -> {
            paneMyProf.setVisible(false);
            lbErrorPas.setText("");
        });

        btnMerop.setOnAction(event -> {
            paneMerop.setVisible(true);
        });
        btnOtmM.setOnAction(event -> {
            paneMerop.setVisible(false);
        });
        btnSaveM.setOnAction(event -> {
            if (!(tfH.getText().isEmpty() || tfMin.getText().isEmpty()
                    || tfNameAct.getText().isEmpty() || tfNameM.getText().isEmpty()) && (5<Integer.parseInt(tfH.getText())
                    && Integer.parseInt(tfH.getText())<24) && (0<=Integer.parseInt(tfMin.getText()) && Integer.parseInt(tfMin.getText())<=59)
                    && (0<Integer.parseInt(tfDays.getText()) && 0<Integer.parseInt(tfDay.getText()))) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                            "root", "Qwerty123456")){
                        PreparedStatement statement = conn.prepareStatement
                                ("INSERT into act(НаименованиеМероприятия, ДатаНачала, Дни, Активность, День, ВремяНачала," +
                                        "Модератор, Жюри1, Жюри2 ,Жюри3 ,Жюри4 ,Жюри5, Победитель) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        statement.setString(1, tfNameM.getText());
                        statement.setString(2, String.valueOf(java.sql.Date.valueOf(comboDate.getValue())));
                        statement.setString(3, tfDays.getText());
                        statement.setString(4, tfNameAct.getText());
                        statement.setString(5, tfDay.getText());
                        statement.setString(6, (tfH.getText() +":"+ tfMin.getText()));
                        statement.setString(7, selectModer);
                        statement.setString(8, selectJury1);
                        statement.setString(9, selectJury2);
                        statement.setString(10, selectJury3);
                        statement.setString(11, selectJury4);
                        statement.setString(12, selectJury5);
                        statement.setString(13, selectWinner);
                        statement.executeUpdate();
                    }
                    paneMerop.setVisible(false);}
                catch (Exception e) {
                    System.out.println("Ошибка с заполнением");
                }}
            else {lbError.setText("Проверьте, все ли поля заполнены верно");}
        });

    }

    private void showTime(){
        int time = Integer.parseInt(String.valueOf(java.time.LocalDateTime.now()).substring(11, 13));
        if (9<=time && time<=10) {
            lbPrivetTime.setText("Доброе утро");
        }
        else if (11<=time && time<=17) {
            lbPrivetTime.setText("Добрый день");
        }
        else if (18<=time && time<=23) {
            lbPrivetTime.setText("Добрый вечер");
        }
    }

    private void showMyInfo(int Code) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                    "root", "Qwerty123456");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  id_organizator, ФИО, Почта, ДатаРождения, страна, телефон, фото, пол FROM organizator WHERE `id_organizator` = " + Code);
            while (resultSet.next()) {
                lbFIO.setText(resultSet.getString("ФИО"));
                lbPol.setText(resultSet.getString("пол"));
                lbBdate.setText(resultSet.getString("ДатаРождения"));
                lbID.setText(resultSet.getString("id_organizator"));
                lbCountry.setText(resultSet.getString("страна"));
                lbNumber.setText(resultSet.getString("телефон"));
                lbEmail.setText(resultSet.getString("Почта"));
                picID = resultSet.getString("фото");
                imageID.setImage(new Image("C:\\Users\\Sanches\\IdeaProjects\\DemoEkz\\src\\main\\resources\\image\\organizator\\" + picID));

            }
        } catch (Exception e) {
            System.out.println("Ошибка с личной инфой");
        }

    }

    public void moderCombo() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                "root", "Qwerty123456")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM moderator");
            while (resultSet.next()){
                moderatorData.add(resultSet.getString("ФИО"));
            }
            comboMod.getItems().addAll( moderatorData);
            comboMod.setValue("Модератор");
            comboMod.setOnAction(this::getModer);
        }catch (Exception e){
            System.out.println("Ошибка с выбором модератора");
        }}
    public void juryCombo() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                "root", "Qwerty123456")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM jury");
            while (resultSet.next()){
                juryData.add(resultSet.getString("ФИО"));
            }
            comboJ1.getItems().addAll( juryData);
            comboJ1.setValue("Жюри 1");
            comboJ1.setOnAction(this::getJ1);
            comboJ2.getItems().addAll( juryData);
            comboJ2.setValue("Жюри 2");
            comboJ2.setOnAction(this::getJ2);
            comboJ3.getItems().addAll( juryData);
            comboJ3.setValue("Жюри 3");
            comboJ3.setOnAction(this::getJ3);
            comboJ4.getItems().addAll( juryData);
            comboJ4.setValue("Жюри 4");
            comboJ4.setOnAction(this::getJ4);
            comboJ5.getItems().addAll( juryData);
            comboJ5.setValue("Жюри 5");
            comboJ5.setOnAction(this::getJ5);
        }catch (Exception e){
            System.out.println("Ошибка с выбором жюри");
        }}
    public void winCombo() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/4_course",
                "root", "Qwerty123456")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM member");
            while (resultSet.next()){
                winData.add(resultSet.getString("ФИО"));
            }
            comboWin.getItems().addAll(winData);
            comboWin.setValue("Победитель");
            comboWin.setOnAction(this::getWinner);
        }catch (Exception e){
            System.out.println("Ошибка с выбором победителя");
        }}

    private void getJ1(javafx.event.ActionEvent actionEvent1) {
        selectJury1 = comboJ1.getValue();
    }
    private void getJ2(javafx.event.ActionEvent actionEvent1) {
        selectJury2 = comboJ2.getValue();
    }
    private void getJ3(javafx.event.ActionEvent actionEvent1) {
        selectJury3 = comboJ3.getValue();
    }
    private void getJ4(javafx.event.ActionEvent actionEvent1) {
        selectJury4 = comboJ4.getValue();
    }
    private void getJ5(javafx.event.ActionEvent actionEvent1) {
        selectJury5 = comboJ5.getValue();
    }
    private void getWinner(javafx.event.ActionEvent actionEvent1) {
        selectWinner = comboWin.getValue();
    }
    private void getModer(javafx.event.ActionEvent actionEvent1) {
        selectModer = comboMod.getValue();
    }
}
