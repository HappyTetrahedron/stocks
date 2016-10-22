package de.njsm.stocks.server.internal.auth;

import de.njsm.stocks.server.internal.Config;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class X509CertificateAdmin implements AuthAdmin {

    private static final Logger LOG = Logger.getLogger(X509CertificateAdmin.class);

    public void revokeCertificate(int id) {

        String command = String.format("openssl ca " +
                "-config ../CA/intermediate/openssl.cnf " +
                "-batch " +
                "-revoke ../CA/intermediate/certs/user_%d.cert.pem",
                id);
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            refreshCrl();
        } catch (IOException e){
            LOG.error("Failed to revoke certificate", e);
        } catch (InterruptedException e) {
            LOG.error("Interrupted while waiting", e);
        }

    }

    private void refreshCrl() {
        String crlCommand = "openssl ca " +
                "-config ../CA/intermediate/openssl.cnf " +
                "-gencrl " +
                "-out ../CA/intermediate/crl/intermediate.crl.pem";
        String nginxCommand = "sudo /usr/lib/stocks-server/nginx-reload";
        
        try {
            Process p = Runtime.getRuntime().exec(crlCommand);
            p.waitFor();

            FileOutputStream out = new FileOutputStream("../CA/intermediate/crl/whole.crl.pem");
            IOUtils.copy(new FileInputStream("../CA/crl/ca.crl.pem"), out);
            IOUtils.copy(new FileInputStream("../CA/intermediate/crl/intermediate.crl.pem"), out);
            out.close();

            p = Runtime.getRuntime().exec(nginxCommand);
            p.waitFor();
        } catch (IOException e) {
            LOG.error("Failed to reload CRL", e);
        } catch (InterruptedException e) {
            LOG.error("Interrupted while waiting", e);
        }
    }
}
