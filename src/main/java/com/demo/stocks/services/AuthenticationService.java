package com.demo.stocks.services;

public interface AuthenticationService {
    boolean isTokenValid(String token);

    boolean isAccountNameValid(String accountName);
}
