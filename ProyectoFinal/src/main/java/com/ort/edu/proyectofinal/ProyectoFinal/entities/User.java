package com.ort.edu.proyectofinal.ProyectoFinal.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int userId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Mail")
    private String mail;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "UserStateId")
    private int userStateId;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "LastUpdate")
    private Date lastUpdate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(int userStateId) {
        this.userStateId = userStateId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
