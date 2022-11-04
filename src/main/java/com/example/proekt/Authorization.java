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

    @FXML
    void initialize(){
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
        enterBtn.setOnAction(actionEvent -> {
            String loginCheck = login.getText().trim();
            String passwordCheck = password.getText().trim();
            String captchaCheck = captcha.getText().trim();
            ResultSet res = bdread(loginCheck, passwordCheck);
            int count = 0;
            try{
                while(res.next()){
                    count++;
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            if(count >= 1 & captchaCheck.equals(captchaText.getText())){
                System.out.println("Авторизация прошла успешно");
            }else{
                System.out.println("Произошла ошибка");
            }
        });
    }

    public ResultSet bdread(String loginCheck, String passwordCheck){
        ResultSet resSet = null;
        String loginSelect = "SELECT * FROM worldskills.organizators WHERE Почта =? AND Пароль =?";
        try{
            PreparedStatement preSt = getDbConnection().prepareStatement(loginSelect);
            preSt.setString(1, loginCheck);
            preSt.setString(2, passwordCheck);
            resSet = preSt.executeQuery();
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return resSet;
    }

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localHost:3306/worldskills";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, "root", "1234");
        return dbConnection;
    }
}
