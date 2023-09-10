package com.lxdain.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Controller {
    @FXML
    private TableView<InternetPackage> tableView;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<String> speedComboBox;
    @FXML
    private ComboBox<String> bandwidthComboBox;
    @FXML
    private ChoiceBox<String> durationChoiceBox;
    @FXML
    private Button submitButton;
    @FXML
    private Button clearFormButton;
    @FXML
    private Button deleteRowButton;

    private ObservableList<InternetPackage> packageList = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        initializeTableView();
        initializeDurationChoiceBox();
        loadInternetPackagesFromDatabase();
    }

    private void initializeTableView() {
        TableColumn<InternetPackage, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        // Add other columns similarly

        tableView.getColumns().addAll(firstNameColumn);
        // Add other columns similarly

        tableView.setItems(packageList);
    }

    private void initializeDurationChoiceBox() {
        durationChoiceBox.setItems(FXCollections.observableArrayList("One Year", "Two Years"));
        durationChoiceBox.getSelectionModel().selectFirst(); // Select the first item by default
    }

    @FXML
    protected void handleSubmitButtonClick(ActionEvent event) {
        // Create a new InternetPackage from the form inputs
        InternetPackage newPackage = createInternetPackageFromForm();

        // Save the new package to the database and add it to the table
        saveInternetPackageToDatabase(newPackage);
        packageList.add(newPackage);
        clearFormInputs();
    }

    // Implement database save logic here
    private void saveInternetPackageToDatabase(InternetPackage internetPackage) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Replace with your Hibernate session configuration
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(internetPackage); // Use persist instead of save
            transaction.commit();
        } catch (Exception e) {
            // Handle exceptions, log, and possibly roll back the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Implement database load logic here
    private void loadInternetPackagesFromDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Replace with your Hibernate session configuration

        try {
            List<InternetPackage> internetPackages = session.createQuery("FROM InternetPackage", InternetPackage.class).list();
            packageList.addAll(internetPackages);
        } catch (Exception e) {
            // Handle exceptions, log, and provide appropriate feedback
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @FXML
    protected void handleClearFormButtonClick(ActionEvent event) {
        clearFormInputs();
    }

    @FXML
    protected void handleDeleteRowButtonClick(ActionEvent event) {
        // Get the selected item in the table
        InternetPackage selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove the selected item from the table and delete it from the database
            packageList.remove(selectedItem);
            deleteInternetPackageFromDatabase(selectedItem);
        }
    }

    private void deleteInternetPackageFromDatabase(InternetPackage internetPackage) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Replace with your Hibernate session configuration
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(internetPackage); // Use remove instead of delete
            transaction.commit();
        } catch (Exception e) {
            // Handle exceptions, log, and possibly roll back the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private InternetPackage createInternetPackageFromForm() {
        // Extract data from form inputs
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String address = addressTextField.getText();
        String speed = speedComboBox.getValue();
        String bandwidth = bandwidthComboBox.getValue();
        String duration = durationChoiceBox.getValue();

        // Create a new InternetPackage
        return new InternetPackage(firstName, lastName, address, speed, bandwidth, duration);
    }

    private void clearFormInputs() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        speedComboBox.getSelectionModel().clearSelection();
        bandwidthComboBox.getSelectionModel().clearSelection();
        durationChoiceBox.getSelectionModel().selectFirst(); // Reset duration to the first item
    }
}
