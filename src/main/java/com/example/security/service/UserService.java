package com.example.security.service;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void checkSniffer(String username, String password, HttpServletResponse response);
}
