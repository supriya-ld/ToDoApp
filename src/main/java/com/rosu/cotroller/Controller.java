package com.rosu.cotroller;

import com.rosu.model.Task;
import com.rosu.model.User;
import com.rosu.repository.TaskRepository;
import com.rosu.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

//TODO: Add scenes for different operations: e.g. login, register.
//TODO: make all the tabs hidden. Login window opens when you click an option in the menu
//TODO: After login success show other tabs. Clear text fields. Show message
//TODO: after clicking register , empty textboxes, show message user registered

//TODO: create other entities. Task, Subtask + repository for each
//TODO: create tab for registering tasks
//TODO: create controls for registering subtasks
//TODO: create tab for assign task. Two comoboboxes. One for tasks, one for all users.


public class Controller {
    public VBox vBoxTasks;
    public Button btnInsert;
    public TextField txtFieldTask;
    public TableView tableView;
    public TableColumn<Task, String> colTaskDesc;
    public TableColumn<Task, Integer> colTaskId;
    public TabPane tabPaneTasks;
    public Tab tabAddTask;
    public Tab tabAllTasks;

    @FXML
    private Label lblInformation;
    @FXML
    private TextField txtFieldUsernameRegister;
    @FXML
    private PasswordField pwdFieldRegister;
    @FXML
    private PasswordField pwdFieldConfirmRegister;
    @FXML
    private TextField txtFieldUsernameLogin;
    @FXML
    private PasswordField pwdFieldLogin;
    @FXML
    private TextField txtFieldRegister;
    @FXML
    private TextField txtFieldLogin;

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private boolean isConnectionSuccessful = false;
    private Task task;
    private ObservableList<Task> tasksList;


    public void initialize() {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initColumn();
        tableView.setItems(getTasksList());
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);
        taskRepository = new TaskRepository(entityManager);
    }


    @FXML
    private void registerUser(ActionEvent actionEvent) {
        // Action Event = a semantic event which indicates that a component-defined action occurred.

        User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if (user != null) {
            lblInformation.setText("this username already exists");
            lblInformation.setAlignment(Pos.CENTER);
        } else if (pwdFieldRegister.getText().matches(passwordPattern)) {

            if (pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText())) {
                user = new User();
                user.setUsername(txtFieldUsernameRegister.getText());
                user.setPassword(pwdFieldRegister.getText());
                userRepository.save(user);
                lblInformation.setText("user registered");
                lblInformation.setAlignment(Pos.CENTER);
                txtFieldUsernameRegister.clear();
                pwdFieldRegister.clear();
                pwdFieldConfirmRegister.clear();

            } else {
                lblInformation.setText("passwords doesn't match");
                lblInformation.setAlignment(Pos.CENTER);
            }
        } else {
            lblInformation.setText("password doesn't contains certain characters");
            lblInformation.setAlignment(Pos.CENTER);
        }
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) throws IOException {

        User user = userRepository.findByUsername(txtFieldUsernameLogin.getText());
        if(user.getPassword().equals(pwdFieldLogin.getText())){

            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("task.fxml");
            Parent root = fxmlLoader.load(resourceAsStream);

            Scene loginScene = new Scene(root);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Tasks");
            window.setScene(loginScene);
            window.show();
            tabPaneTasks.getTabs().add(tabAddTask);
            tabPaneTasks.getTabs().add(tabAllTasks);
        }
    }


    @FXML
    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldRegister.isVisible()) {
            txtFieldRegister.setText(pwdFieldRegister.getText());
            txtFieldRegister.setEditable(false);
            txtFieldRegister.setVisible(true);
            pwdFieldRegister.setVisible(false);
        } else {
            txtFieldRegister.setVisible(false);
            pwdFieldRegister.setVisible(true);
        }
    }

    @FXML
    public void showPasswordConfirm(ActionEvent actionEvent) {
        if (!txtFieldLogin.isVisible()) {
            txtFieldLogin.setText(pwdFieldConfirmRegister.getText());
            txtFieldLogin.setEditable(false);
            txtFieldLogin.setVisible(true);
            pwdFieldConfirmRegister.setVisible(false);
        } else {
            txtFieldLogin.setVisible(false);
            pwdFieldConfirmRegister.setVisible(true);
        }
    }

    @FXML
    public void showPasswordLogin(ActionEvent actionEvent) {
        if (!txtFieldLogin.isVisible()) {
            txtFieldLogin.setText(pwdFieldLogin.getText());
            txtFieldLogin.setEditable(false);
            txtFieldLogin.setVisible(true);
            pwdFieldLogin.setVisible(false);
        } else {
            txtFieldLogin.setVisible(false);
            pwdFieldLogin.setVisible(true);
        }
    }

    public void insertTaskEnter(KeyEvent keyEvent) {
        insertTask(keyEvent);
    }

    public void insertTask(ActionEvent actionEvent) {
        insertTask(actionEvent);
    }

    private void insertTask(KeyEvent keyEvent) {

        /** TO DO: show all tasks not only the last added ones **/

        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            Task task = new Task();
            task.setCreatedAt(new Date());
            task.setDescription(txtFieldTask.getText());

            taskRepository.save(task);

            CheckBox checkBox = new CheckBox();
            checkBox.setText(task.getDescription());

            vBoxTasks.getChildren().add(checkBox);
        }
    }
//
//    public void allTasksPane(){
//        tabPaneTasks.getTabs().add(tabAllTasks);
//
//    }

    public void loadTasks(Event event){
//        List<Task> tasks = taskRepository.findAll();
//        final ObservableList<Task> dbTasks = FXCollections.observableList(tasks);
//        initColumn();
//        tableView.setItems(dbTasks);


    }

    public void initColumn(){
        try {
            colTaskId.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
            colTaskDesc.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        }catch (NullPointerException e){
            System.out.println("Exception caught");
        }
    }

    public ObservableList<Task> getTasksList() {
        task = new Task();
        tasksList = FXCollections.observableArrayList(taskRepository.findAll());
        return tasksList;
    }

}
