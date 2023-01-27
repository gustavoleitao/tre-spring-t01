package br.ufrn.imd.treeleicao.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

    Long id;

    String username;

    Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
