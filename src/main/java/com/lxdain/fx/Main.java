package com.lxdain.fx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private TableView<InternetPackage> tableView;
    private ObservableList<InternetPackage> packageList = FXCollections.observableArrayList();
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField addressTextField;
    private ComboBox<String> speedComboBox;
    private ComboBox<String> bandwidthComboBox;
    private ToggleGroup durationToggleGroup;
    private Button submitButton;
    private Button clearFormButton;
    private Button deleteRowButton;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Controller controller = loader.getController();


        stage.setTitle("Internet Package Registration");

        // Left-side Form Layout
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        Label speedLabel = new Label("Speed:");
        speedComboBox = new ComboBox<>();
        speedComboBox.getItems().addAll("2 Mbit/s", "5 Mbit/s", "10 Mbit/s", "20 Mbit/s", "50 Mbit/s", "100 Mbit/s");
        speedComboBox.setPromptText("Select Speed");

        Label bandwidthLabel = new Label("Bandwidth:");
        bandwidthComboBox = new ComboBox<>();
        bandwidthComboBox.getItems().addAll("1 GB", "5 GB", "10 GB", "100 GB", "Flat");
        bandwidthComboBox.setPromptText("Select Bandwidth");

        Label durationLabel = new Label("Duration of Contract:");
        durationToggleGroup = new ToggleGroup();
        RadioButton oneYearRadioButton = new RadioButton("One Year");
        RadioButton twoYearsRadioButton = new RadioButton("Two Years");
        oneYearRadioButton.setToggleGroup(durationToggleGroup);
        twoYearsRadioButton.setToggleGroup(durationToggleGroup);

        Label fNameLabel = new Label("First Name:");
        firstNameTextField = new TextField();

        Label lNameLabel = new Label("Last Name:");
        lastNameTextField = new TextField();

        Label addressLabel = new Label("Address:");
        addressTextField = new TextField();

        // Initialize submitButton
        submitButton = new Button("Submit");

        Label clearFormLabel = new Label(""); // Placeholder for clearing form

        clearFormButton = new Button("Clear Form");

        formGrid.add(speedLabel, 0, 0);
        formGrid.add(speedComboBox, 1, 0);
        formGrid.add(bandwidthLabel, 0, 1);
        formGrid.add(bandwidthComboBox, 1, 1);
        formGrid.add(durationLabel, 0, 2);
        formGrid.add(oneYearRadioButton, 1, 2);
        formGrid.add(twoYearsRadioButton, 2, 2);
        formGrid.add(fNameLabel, 0, 3);
        formGrid.add(firstNameTextField, 1, 3);
        formGrid.add(lNameLabel, 0, 4);
        formGrid.add(lastNameTextField, 1, 4);
        formGrid.add(addressLabel, 0, 5);
        formGrid.add(addressTextField, 1, 5);
        formGrid.add(submitButton, 1, 6);
        formGrid.add(clearFormLabel, 2, 6);
        formGrid.add(clearFormButton, 3, 6);
        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(10);
        deleteRowButton = new Button("Delete Row");
        buttonLayout.getChildren().addAll(submitButton, clearFormButton, deleteRowButton);

        submitButton.setOnAction(e -> controller.handleSubmitButtonClick(null));
        clearFormButton.setOnAction(e -> controller.handleClearFormButtonClick(null));
        deleteRowButton.setOnAction(e -> controller.handleDeleteRowButtonClick(null));


        // Right-side Table Layout
        tableView = new TableView<>();
        tableView.setEditable(false);

        TableColumn<InternetPackage, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<InternetPackage, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<InternetPackage, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<InternetPackage, String> speedColumn = new TableColumn<>("Speed (mb/s)");
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<InternetPackage, String> bandwidthColumn = new TableColumn<>("Bandwidth");
        bandwidthColumn.setCellValueFactory(new PropertyValueFactory<>("bandwidth"));

        TableColumn<InternetPackage, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, addressColumn, speedColumn, bandwidthColumn, durationColumn);

        // Combine Left-side and Right-side Layouts
        HBox mainLayout = new HBox();
        mainLayout.setSpacing(20);
        mainLayout.getChildren().addAll(formGrid, tableView);

        VBox rootLayout = new VBox();
        rootLayout.setSpacing(10);
        rootLayout.getChildren().addAll(mainLayout, buttonLayout);

        stage.show();
    }
}
