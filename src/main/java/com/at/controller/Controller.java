package com.at.controller;

import com.at.model.User;
import com.at.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controller {

    @FXML
    private Label lblInformation;

    @FXML
    private TextField txtFieldUsernameRegister;

    @FXML
    private PasswordField pwdFieldRegister;

    @FXML
    private PasswordField pwdFieldConfirmRegister;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtFieldUsernameLogin;

    @FXML
    private AnchorPane pwdFieldConfirmLogin;

    @FXML
    private PasswordField pwdFieldLogin;

    @FXML
    UserRepository userRepository;
    private boolean isConnectionSuccessfull=false;

    private Button btnLogin;
    public void initialize(){
        try {
            persistanceConnection();
        }catch (Exception e){
            System.out.println("Connection is not allowed");
            isConnectionSuccessfull=false;
        }

    }

    private void persistanceConnection(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ToDoApp");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        userRepository.findById(16);
    }
    @FXML
    private void registerUser(ActionEvent actionEvent){
        //to do:  Add button to show password
        //to do: check if username already exists
        //to do: check if password contains certain characters
        //to do: save encoded password

        User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());

        if(pwdFieldLogin.getText().equals(pwdFieldConfirmRegister.getText()) && user==null) {
            user = new User();
            user.setUsername(txtFieldUsernameRegister.getText());
            user.setPassword(pwdFieldRegister.getText());
            userRepository.save(user);
        }
    }
    @FXML
    private void loginUser(ActionEvent actionEvent){
    // to do: implement login action

    }
}
