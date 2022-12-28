package com.example.proekt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.Random;

public class Authorization {
    @FXML private Button enterBtn;
    @FXML private TextField login;
    @FXML private TextField captcha;
    @FXML private PasswordField password;
    @FXML private TextField captchaText;
    public static int idOrg = 0;

    @FXML
    void initialize(){
        captchaSet();
        enterBtn.setOnAction(actionEvent -> {
            String loginCheck = login.getText().trim();
            String passCheck = password.getText().trim();
            String captchaCheck = captcha.getText().trim();
            try {
                Statement orgStatement = getDbConnection().createStatement();
                ResultSet orgSet = orgStatement.executeQuery("SELECT * FROM organizators");
                while(orgSet.next()){
                    if(orgSet.getString("Почта").equals(loginCheck) & orgSet.getString("Пароль").equals(passCheck)
                            & captchaCheck.equals(captchaText.getText())){
                        idOrg = orgSet.getInt("ID");
                        HelloApplication.openAnotherWindow("orgWindow.fxml");
                        enterBtn.getScene().getWindow().hide();
                    }
                }
                Statement modStatement = getDbConnection().createStatement();
                ResultSet modSet = modStatement.executeQuery("SELECT * FROM moderators");
                while(modSet.next()){
                    if(modSet.getString("Почта").equals(loginCheck) & modSet.getString("Пароль").equals(passCheck)
                            & captchaCheck.equals(captchaText.getText())){
                        HelloApplication.openAnotherWindow("modWindow.fxml");
                        enterBtn.getScene().getWindow().hide();
                    }
                }
                Statement juryStatement = getDbConnection().createStatement();
                ResultSet jurySet = juryStatement.executeQuery("SELECT * FROM jury");
                while(jurySet.next()){
                    if(jurySet.getString("Почта").equals(loginCheck) & jurySet.getString("Пароль").equals(passCheck)
                            & captchaCheck.equals(captchaText.getText())){
                        HelloApplication.openAnotherWindow("juryWindow.fxml");
                        enterBtn.getScene().getWindow().hide();
                    }
                }
                Statement memStatement = getDbConnection().createStatement();
                ResultSet memSet = memStatement.executeQuery("SELECT * FROM members");
                while(memSet.next()){
                    if(memSet.getString("Почта").equals(loginCheck) & memSet.getString("Пароль").equals(passCheck)
                            & captchaCheck.equals(captchaText.getText())){
                        HelloApplication.openAnotherWindow("memWindow.fxml");
                        enterBtn.getScene().getWindow().hide();
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            captchaSet();
        });
    }

    public void captchaSet(){
        String code = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        int index;
        char cap;
        String captxt = "";
        for(int i = 0; i < 4; i++){
            index = random.nextInt(code.length());
            cap = code.charAt(index);
            captxt += cap;
        }
        captchaText.setText(captxt);
    }

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localHost:3306/worldskills";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "1234");
        return dbConnection;
    }
}