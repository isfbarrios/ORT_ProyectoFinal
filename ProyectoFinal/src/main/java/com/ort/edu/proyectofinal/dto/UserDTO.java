package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String name;
    private String surname;
    private String mail;
    private String username;
    private String sessionId;
    private CoreManager.UserType type;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.mail = user.getMail();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.sessionId = user.getSessionId();
        //Por defecto se asigna como local
        this.type = CoreManager.UserType.LOCAL;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", sessionid='" + sessionId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(username, userDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
