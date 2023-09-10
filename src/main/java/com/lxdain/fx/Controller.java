package com.lxdain.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private ObservableList<InternetPackage> packageList = FXCollections.observableArrayList();

    private InternetPackage selectedPackage;

    @FXML
    protected void initialize() {
        initializeTableView();
        initializeDurationChoiceBox();
        loadInternetPackagesFromDatabase();

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFormWithUserData(newValue);
                selectedPackage = newValue;
            }
        });
    }

    private void initializeTableView() {
        tableView.getColumns();
        tableView.setItems(packageList);
    }

    private TableColumn<InternetPackage, String> createTableColumn(String text, String property) {
        TableColumn<InternetPackage, String> column = new TableColumn<>(text);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void initializeDurationChoiceBox() {
        durationChoiceBox.setItems(FXCollections.observableArrayList("One Year", "Two Years"));
        durationChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    protected void handleSubmitButtonClick(ActionEvent event) {
        InternetPackage updatedPackage = createInternetPackageFromForm();

        if (selectedPackage != null) {
            // Update the existing package with new values
            selectedPackage.copyFrom(updatedPackage);
            updateInternetPackageInList(selectedPackage);
            updateInternetPackageInDatabase(selectedPackage);

            // Populate the form fields with the updated data
            populateFormWithUserData(selectedPackage);
        } else {
            // Create and save a new package
            saveInternetPackageToDatabase(updatedPackage);

            // Add the new package to the list and refresh the table view
            packageList.add(updatedPackage);
            tableView.refresh();
        }

        clearFormInputs();
    }

    @FXML
    protected void handleClearFormButtonClick(ActionEvent event) {
        // Clear the form inputs
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

    private void updateInternetPackageInDatabase(InternetPackage internetPackage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(internetPackage); // Use update instead of persist for existing entities
            transaction.commit();
        } catch (Exception e) {
            handleDatabaseError(transaction, e);
        } finally {
            session.close();
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

    private void populateFormWithUserData(InternetPackage user) {
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        addressTextField.setText(user.getAddress());
        speedComboBox.setValue(user.getSpeed());
        bandwidthComboBox.setValue(user.getBandwidth());
        durationChoiceBox.setValue(user.getContractDuration());
    }

    private void updateInternetPackageInList(InternetPackage updatedPackage) {
        int index = packageList.indexOf(updatedPackage);
        if (index != -1) {
            packageList.set(index, updatedPackage);
        }
    }

    private void saveInternetPackageToDatabase(InternetPackage internetPackage) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(internetPackage);
            transaction.commit();
        } catch (Exception e) {
            handleDatabaseError(transaction, e);
        } finally {
            session.close();
        }
    }

    private void loadInternetPackagesFromDatabase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<InternetPackage> internetPackages = session.createQuery("FROM InternetPackage", InternetPackage.class).list();
            packageList.addAll(internetPackages);
        } catch (Exception e) {
            handleDatabaseError(null, e);
        } finally {
            session.close();
        }
    }

    private InternetPackage createInternetPackageFromForm() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String address = addressTextField.getText();
        String speed = speedComboBox.getValue();
        String bandwidth = bandwidthComboBox.getValue();
        String duration = durationChoiceBox.getValue();
        return new InternetPackage(firstName, lastName, address, speed, bandwidth, duration);
    }

    private void clearFormInputs() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        speedComboBox.getSelectionModel().clearSelection();
        bandwidthComboBox.getSelectionModel().clearSelection();
        durationChoiceBox.getSelectionModel().selectFirst();
    }

    private void handleDatabaseError(Transaction transaction, Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}