package de.njsm.stocks.linux.client.network;


import de.njsm.stocks.linux.client.data.Ticket;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;


public interface SentryClient {

    @POST("uac/newuser")
    Call<Ticket> requestCertificate(@Body Ticket ticket);

}
