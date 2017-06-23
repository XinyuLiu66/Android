package com.example.apple.liu_androidauto_parkingsaverapp.Auth_Basic;

/**
 * Basic Authentication class, you can compare the username and password from the login-GUI
 * @author Xinyu Liu, Yue Hu
 * Created by Yue Hu on 18.06.2017.
 */

public class Auth_Basic {

    private String username;

    private String password;

    public Auth_Basic(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
