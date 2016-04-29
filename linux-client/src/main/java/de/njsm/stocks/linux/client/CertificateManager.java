package de.njsm.stocks.linux.client;

import de.njsm.stocks.linux.client.Configuration;
import de.njsm.stocks.linux.client.InitManager;
import de.njsm.stocks.linux.client.frontend.UIFactory;

import java.io.File;

public class CertificateManager {

    public static final String keystorePath = System.getProperty("user.home") + "/.stocks/keystore";

    protected Configuration c;

    public CertificateManager(Configuration c){
        this.c = c;
    }

    public void loadCertificates(UIFactory f) {

        if (! hasCerts()) {
            (new InitManager(c)).initCertificates(f.getCertGenerator());
        }

    }

    public boolean hasCerts() {
        return new File(keystorePath).exists();
    }
}
