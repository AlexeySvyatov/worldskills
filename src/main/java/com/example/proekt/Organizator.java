package com.example.proekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.util.Objects;

public class Organizator {
    @FXML private CheckBox checkPw;
    @FXML private Pane paneProfile;
    @FXML private Pane paneEvents;
    @FXML private Button eventsBtn;
    @FXML private Button profileBtn;
    @FXML private Button juryBtn;
    @FXML private Button membersBtn;
    @FXML private Button okBtn;
    @FXML private Button cancelBtn;
    @FXML private Button saveEventBtn;
    @FXML private Button cancelEventBtn;
    @FXML private PasswordField password;
    @FXML private PasswordField password1;
    @FXML private TextField days;
    @FXML private TextField day;
    @FXML private TextField hours;
    @FXML private TextField minutes;
    @FXML private TextField eventName;
    @FXML private TextField actName;
    @FXML private Label errorMes;
    @FXML private Label helloTime;
    @FXML private Label helloName;
    @FXML private Label lbFIO;
    @FXML private Label lbCountry;
    @FXML private Label lbNumber;
    @FXML private Label lbEmail;
    @FXML private Label lbID;
    @FXML private Label lbBDate;
    @FXML private Label lbPol;
    @FXML private Label pwError;
    @FXML private ImageView imageID;
    @FXML private ComboBox<String> comboMod;
    @FXML private ComboBox<String> comboJ1;
    @FXML private ComboBox<String> comboJ2;
    @FXML private ComboBox<String> comboJ3;
    @FXML private ComboBox<String> comboJ4;
    @FXML private ComboBox<String> comboJ5;
    @FXML private ComboBox<String> comboWin;
    @FXML private DatePicker startDate;
    public String picID;

    private final ObservableList<String> moderatorData = FXCollections.observableArrayList();
    private final ObservableList<String> juryData = FXCollections.observableArrayList();
    private final ObservableList<String> winnerData = FXCollections.observableArrayList();
    private String selectModer = "";
    private String selectWinner = "";
    private String selectJury1 = "";
    private String selectJury2 = "";
    private String selectJury3 = "";
    private String selectJury4 = "";
    private String selectJury5 = "";


    @FXML
    void initialize(){
        showTime();
        moderCombo();
        juryCombo();
        winCombo();
        try{
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM organizators WHERE `ID` = " + Authorization.idOrg);
            while(resultSet.next()) {
                helloName.setText(resultSet.getString("ФИО"));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        okBtn.setOnAction(event -> {
            if(checkPw.isSelected() && !(password.getText().isEmpty() || password1.getText().isEmpty())
                    && Objects.equals(password.getText(), password1.getText())){
                try{
                    PreparedStatement statement = getDbConnection().prepareStatement
                            ("UPDATE organizators SET Пароль = '" + password.getText() + "' WHERE ID = " + Authorization.idOrg);
                    statement.executeUpdate();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                paneProfile.setVisible(false);
                pwError.setText("");
            }else if(!(checkPw.isSelected())) {
                paneProfile.setVisible(false);
                pwError.setText("");
            }else{
                pwError.setText("Проверьте пароль");
            }
        });
        profileBtn.setOnAction(event -> {
            paneProfile.setVisible(true);
            showMyInfo(Authorization.idOrg);
        });
        cancelBtn.setOnAction(event -> {
            paneProfile.setVisible(false);
            pwError.setText("");
        });
        eventsBtn.setOnAction(event -> {
            paneEvents.setVisible(true);
        });
        cancelEventBtn.setOnAction(event -> {
            paneEvents.setVisible(false);
        });
        saveEventBtn.setOnAction(event -> {
            if(!(hours.getText().isEmpty() || minutes.getText().isEmpty() || actName.getText().isEmpty() || eventName.getText().isEmpty())
                    && (0<Integer.parseInt(days.getText()) && 0<Integer.parseInt(day.getText()))){
                if((5<Integer.parseInt(hours.getText()) && Integer.parseInt(hours.getText())<24) &&
                        (0<=Integer.parseInt(minutes.getText()) && Integer.parseInt(minutes.getText())<=59)){
                    try{
                        PreparedStatement statement = getDbConnection().prepareStatement("INSERT into act(Мероприятие, Дата, Дни, Активность, День, Время," +
                                        "Модератор, Жюри1, Жюри2 ,Жюри3 ,Жюри4 ,Жюри5, Победитель) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        statement.setString(1, eventName.getText());
                        statement.setString(2, String.valueOf(java.sql.Date.valueOf(startDate.getValue())));
                        statement.setString(3, days.getText());
                        statement.setString(4, actName.getText());
                        statement.setString(5, day.getText());
                        statement.setString(6, (hours.getText() +":"+ minutes.getText()));
                        statement.setString(7, selectModer);
                        statement.setString(8, selectJury1);
                        statement.setString(9, selectJury2);
                        statement.setString(10, selectJury3);
                        statement.setString(11, selectJury4);
                        statement.setString(12, selectJury5);
                        statement.setString(13, selectWinner);
                        statement.executeUpdate();
                        paneEvents.setVisible(false);
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    errorMes.setText("Проверьте время мероприятия");
                }
            }else{
                errorMes.setText("Проверьте, все ли поля заполнены верно");
            }
        });
    }

    private void showTime(){
        int time = Integer.parseInt(String.valueOf(java.time.LocalDateTime.now()).substring(11, 13));
        if (9<=time && time<=10) {
            helloTime.setText("Доброе утро");
        }else if (11<=time && time<=17) {
            helloTime.setText("Добрый день");
        }else if (18<=time && time<=23) {
            helloTime.setText("Добрый вечер");
        }
    }

    private void showMyInfo(int Code) {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, ФИО, Почта, ДатаРождения, Страна, Телефон, Фото, Пол FROM organizators WHERE `ID` = " + Code);
            while (resultSet.next()) {
                lbFIO.setText(resultSet.getString("ФИО"));
                lbPol.setText(resultSet.getString("Пол"));
                lbBDate.setText(resultSet.getString("ДатаРождения"));
                lbID.setText(resultSet.getString("ID"));
                lbCountry.setText(resultSet.getString("Страна"));
                lbNumber.setText(resultSet.getString("Телефон"));
                lbEmail.setText(resultSet.getString("Почта"));
                picID = resultSet.getString("Фото");
                imageID.setImage(new Image("B:\\IntelliJ IDEA\\IdeaProjects\\proekt\\src\\main\\resources\\com\\example\\proekt\\pictures\\" + picID));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void moderCombo(){
        try{
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM moderators");
            while (resultSet.next()){
                moderatorData.add(resultSet.getString("ФИО"));
            }
            comboMod.getItems().addAll( moderatorData);
            comboMod.setValue("Модератор");
            comboMod.setOnAction(this::getModer);
        }catch (Exception ex){
            ex.printStackTrace();
        }}

    public void juryCombo() {
        try{
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM jury");
            while (resultSet.next()){
                juryData.add(resultSet.getString("ФИО"));
            }
            comboJ1.getItems().addAll(juryData);
            comboJ1.setValue("Жюри 1");
            comboJ1.setOnAction(this::getJ1);
            comboJ2.getItems().addAll(juryData);
            comboJ2.setValue("Жюри 2");
            comboJ2.setOnAction(this::getJ2);
            comboJ3.getItems().addAll(juryData);
            comboJ3.setValue("Жюри 3");
            comboJ3.setOnAction(this::getJ3);
            comboJ4.getItems().addAll(juryData);
            comboJ4.setValue("Жюри 4");
            comboJ4.setOnAction(this::getJ4);
            comboJ5.getItems().addAll(juryData);
            comboJ5.setValue("Жюри 5");
            comboJ5.setOnAction(this::getJ5);
        }catch (Exception ex){
            ex.printStackTrace();
        }}

    public void winCombo() {
        try{
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ФИО FROM members");
            while (resultSet.next()){
                winnerData.add(resultSet.getString("ФИО"));
            }
            comboWin.getItems().addAll(winnerData);
            comboWin.setValue("Победитель");
            comboWin.setOnAction(this::getWinner);
        }catch (Exception ex){
            ex.printStackTrace();
        }}

    private void getJ1(ActionEvent actionEvent1) {
        selectJury1 = comboJ1.getValue();
    }
    private void getJ2(ActionEvent actionEvent1) {
        selectJury2 = comboJ2.getValue();
    }
    private void getJ3(ActionEvent actionEvent1) {
        selectJury3 = comboJ3.getValue();
    }
    private void getJ4(ActionEvent actionEvent1) {
        selectJury4 = comboJ4.getValue();
    }
    private void getJ5(ActionEvent actionEvent1) {
        selectJury5 = comboJ5.getValue();
    }
    private void getWinner(ActionEvent actionEvent1) {
        selectWinner = comboWin.getValue();
    }
    private void getModer(ActionEvent actionEvent1) {
        selectModer = comboMod.getValue();
    }

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localHost:3306/worldskills";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "1234");
        return dbConnection;
    }
}