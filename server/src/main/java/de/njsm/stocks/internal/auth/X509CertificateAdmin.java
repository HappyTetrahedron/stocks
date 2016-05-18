package de.njsm.stocks.internal.auth;

import de.njsm.stocks.internal.Config;

import java.io.IOException;
import java.util.logging.Level;

public class X509CertificateAdmin implements CertificateAdmin {

    @Override
    public void revokeCertificate(int id) {

        String command = String.format("openssl ca " +
                "-config ../CA/intermediate/openssl.cnf " +
                "-batch " +
                "-revoke ../CA/intermediate/certs/user_%d.cert.pem",
                id);
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException e){
            (new Config()).getLog().log(Level.SEVERE,
                    "X509CertificateAdmin: Failed to revoke certificate: " +
                            e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
