package edu.school21.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    public int identifier;
    public String login;
    public String password;
    public boolean isAuthenticated;

}
