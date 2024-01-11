package com.logic;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.datastorage.SQLServerDatabase;
import com.domain.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

public class CourseController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnViewCourse;

    @FXML
    private TableColumn<Course, String> colCourseName;

    @FXML
    private TableColumn<Course, Integer> colCourseNumber;

    @FXML
    private TextField tfCoursename;

    @FXML
    private TextField tfCoursenumber;

    @FXML
    private TextField tfSubject;

    @FXML
    private TextArea tfIntroductiontext;

    @FXML
    private ComboBox<String> cbDifficulty;

    @FXML
    private TableView<Course> tvCourses;

    @FXML
    private ComboBox<String> cbModuleNames;

    boolean isClicked = false;

    ObservableList<Course> courses;



    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnInsert) {
            insertButton();
        } 
        if (event.getSource() == btnDelete) {
            deleteButton();
        } 
        if (event.getSource() == btnClear) {
            isClicked = true;
            clear();    
        } 
        if(event.getSource() == btnBack) {
            backToHome();
        }
        if (event.getSource() == btnUpdate && !isClicked) {
            isClicked = true;
            setText();
        } else if (event.getSource() == btnUpdate && isClicked) {
            updateButton();
            isClicked = false;
        }
        if (event.getSource() == btnViewCourse) {
            viewCourseDetails();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        showCourse();
        cbModuleNames.setItems(FXCollections.observableArrayList(getModuleNames()));
        cbDifficulty.setItems(FXCollections.observableArrayList("beginner", "intermediate", "expert"));
    }
    
    public List<String> getModuleNames() {
        List<String> moduleNames = new ArrayList<>();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT ModuleTitle FROM Module WHERE CourseName IS NULL";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    moduleNames.add(rs.getString("ModuleTitle"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return moduleNames;
    }

    public void backToHome() throws IOException{
        Stage stage = null;
        Parent root = null;

        stage = (Stage) btnBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../presentation/GUI/LayoutGUI.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<Course> getCourse() {
        courses = FXCollections.observableArrayList();
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "SELECT * FROM Course;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                Course course = new Course(
                        rs.getString("CourseName"),
                        rs.getInt("courseNumber"),
                        rs.getString("Subject"),
                        rs.getString("IntroductionText"),
                        rs.getString("Difficulty"));

                courses.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("return");
        System.out.println(courses.size());

        return courses;
    }

    public void showCourse() {
        ObservableList<Course> courseList = getCourse();

        colCourseName.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseName"));
        colCourseNumber.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseNumber"));

        tvCourses.setItems(courseList);
    }

    private void insertButton() {
        System.out.println("Insert Button Clicked");
    
        if (cbModuleNames.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Please select a module.");
            return;
        }
        String selectedModule = cbModuleNames.getSelectionModel().getSelectedItem();
    
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String insertQuery = "INSERT INTO COURSE VALUES (?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE Module SET CourseName = ? WHERE ModuleTitle = ?";
    
        try {
            PreparedStatement insertStm = conn.prepareStatement(insertQuery);
    
            insertStm.setString(1, tfCoursename.getText());
    
            int courseNumber = Integer.parseInt(tfCoursenumber.getText());
            insertStm.setInt(2, courseNumber);
    
            insertStm.setString(3, tfSubject.getText());
            insertStm.setString(4, tfIntroductiontext.getText());
    
            String difficulty = cbDifficulty.getSelectionModel().getSelectedItem();
            insertStm.setString(5, difficulty);
    
            insertStm.execute();
    
            PreparedStatement updateStm = conn.prepareStatement(updateQuery);
            updateStm.setString(1, tfCoursename.getText());
            updateStm.setString(2, selectedModule);
    
            updateStm.execute();
    
            clear();
    
            showCourse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteButton() {
        Connection conn = SQLServerDatabase.getDatabase().getConnection();
        String query = "DELETE FROM Course where CourseName = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, tvCourses.getSelectionModel().getSelectedItem().getCourseName());

            stm.execute();
            showCourse();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setText() {
        Course selectedCourse = tvCourses.getSelectionModel().getSelectedItem();

        tfCoursename.setText(tvCourses.getSelectionModel().getSelectedItem().getCourseName());
        tfCoursenumber.setText(Integer.toString(selectedCourse.getCourseNumber()));
        tfSubject.setText(tvCourses.getSelectionModel().getSelectedItem().getSubject());
        tfIntroductiontext.setText(tvCourses.getSelectionModel().getSelectedItem().getIntroductionText());
        cbDifficulty.getSelectionModel().select(tvCourses.getSelectionModel().getSelectedItem().getDifficulty());

        System.out.println("Set Text in");
    }


    public void updateButton() {

        Connection conn = SQLServerDatabase.getDatabase().getConnection();

        try {

            String value1 = tfCoursename.getText();
            String value2 = tfCoursenumber.getText();
            String value3 = tfSubject.getText();
            String value4 = tfIntroductiontext.getText();
            String value5 = cbDifficulty.getSelectionModel().getSelectedItem();
            System.out.println("Put Text in");

            String query = "UPDATE COURSE SET CourseNumber= ?, Subject= ?, IntroductionText= ?, Difficulty= ? WHERE CourseName= ?";

                    
            PreparedStatement stm = conn.prepareStatement(query);

            int courseNumber = Integer.parseInt(value2);
            stm.setInt(1, courseNumber);
            stm.setString(2, value3);
            stm.setString(3, value4);
            stm.setString(4, value5);
            stm.setString(5, value1);
            System.out.println("SQL Query: " + stm.toString());

            System.out.println("execute");
            stm.execute();
            clear();
            showCourse();

        } catch (Exception e) {

            System.out.println(e);

        }
    }

    public void clear() {
        tfCoursename.clear();
        tfCoursenumber.clear();
        tfSubject.clear();
        tfIntroductiontext.clear();
        cbDifficulty.getSelectionModel().clearSelection();
    }

    private void viewCourseDetails() throws IOException {
    Course selectedCourse = tvCourses.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/CourseDetail/layoutCourseDetail.fxml"));
        Parent courseDetailsRoot = loader.load();

        CourseDetailController courseDetailController = loader.getController();
        courseDetailController.setSelectedCourse(selectedCourse);

        Stage stage = new Stage();

        stage.setScene(new Scene(courseDetailsRoot));

        stage.show();
    }
}

