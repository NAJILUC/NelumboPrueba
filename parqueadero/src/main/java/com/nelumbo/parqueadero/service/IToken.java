package com.nelumbo.parqueadero.service;

public interface IToken {

    String getBearerToken();

    String getUserAuthenticatedEmail(String token);

    Long getUserAuthenticatedId(String token);
}
