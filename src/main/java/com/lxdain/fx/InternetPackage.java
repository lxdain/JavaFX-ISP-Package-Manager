package com.lxdain.fx;

import jakarta.persistence.*;

@Entity
@Table(name = "internet_packages")
public class InternetPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "speed")
    private String speed;

    @Column(name = "bandwidth")
    private String bandwidth;

    @Column(name = "contract_duration")
    private String contractDuration;

    // Consturctors
    public InternetPackage() {}

    public InternetPackage(int id, String firstName, String lastName, String address, String speed, String bandwidth, String contractDuration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.speed = speed;
        this.bandwidth = bandwidth;
        this.contractDuration = contractDuration;
    }

    public InternetPackage(String firstName, String lastName, String address, String speed, String bandwidth, String contractDuration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.speed = speed;
        this.bandwidth = bandwidth;
        this.contractDuration = contractDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(String contractDuration) {
        this.contractDuration = contractDuration;
    }

    public void copyFrom(InternetPackage other) {
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.address = other.address;
        this.speed = other.speed;
        this.bandwidth = other.bandwidth;
        this.contractDuration = other.contractDuration;
    }

    @Override
    public String toString() {
        return "InternetPackage{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", speed='" + speed + '\'' +
                ", bandwidth='" + bandwidth + '\'' +
                ", contractDuration='" + contractDuration + '\'' +
                '}';
    }
}