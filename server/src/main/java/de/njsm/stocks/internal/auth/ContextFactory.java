package de.njsm.stocks.internal.auth;

import javax.servlet.http.HttpServletRequest;

public interface ContextFactory {

    Principals getPrincipals(HttpServletRequest request);
}
