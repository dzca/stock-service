package com.demo.stocks.services.impl;

import com.demo.stocks.services.AuthenticationService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * This is mock AuthenticationService, hard code the token, account-name for demo only
 */
@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public boolean isTokenValid(String token) {
        return "Nipcx3WZJQvQqr07RZ8qrHMXTiYqP3rBpX3NgHgIWc3USkd1hwBQyt8WC9otXG086s30L+zBnzrEN2ZN".equals(token);
    }

    @Override
    public boolean isAccountNameValid(String accountName) {
        List<String> accounts = Arrays.asList("aabbcc", "xxyyzz", "ddeekk");
        return accounts.contains(accountName);
    }
}
