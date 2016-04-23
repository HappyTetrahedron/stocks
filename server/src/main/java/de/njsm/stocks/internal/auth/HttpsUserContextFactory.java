package de.njsm.stocks.internal.auth;

import javax.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;

public class HttpsUserContextFactory implements ContextFactory {


    public UserContext getUserContext(HttpServletRequest request) {

        Object rawClientName = request.getAttribute("X-SSL-Client-S-DN");
        String clientName;

        if (rawClientName instanceof String) {
            clientName = (String) rawClientName;
        } else {
            throw new SecurityException("request was not sent over HTTPS!");
        }

        return parseSubjectName(clientName);
    }

    protected UserContext parseSubjectName(String subject){
        int[] indices = new int[3];
        int last_index = 0;

        // find indices of the $ signs
        for (int i = 0; i < 3; i++){
            indices[i] = subject.indexOf('$', last_index);
            last_index = indices[i];
            if (last_index == -1){
                throw new SecurityException("client name is malformed");
            }
        }

        String username = subject.substring(0, indices[0]);
        String deviceName = subject.substring(indices[1] + 1, indices[2]);
        int userId;
        int deviceId;

        try {
            userId = Integer.parseInt(subject.substring(indices[0] + 1, indices[1]));
            deviceId = Integer.parseInt(subject.substring(indices[2] + 1, subject.length()));
        } catch (NumberFormatException e){
            throw new SecurityException("client ID is malformed");
        }

        return new UserContext(username, userId, deviceName, deviceId);

    }

}
