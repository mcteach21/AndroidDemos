package mc.apps.demos.dao;

import java.util.List;

import mc.apps.demos.model.User;

public class UserDao extends Dao<User> {
    public UserDao(){
        super("users");
    }
    public void login(String login, String password, OnSuccess onSuccess){
        find("login=" + login + "&password=" + password, onSuccess);
    }
}
