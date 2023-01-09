package dtu.example;

import fastmoney.CreateAccountWithBalance;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RESTClient {

    // Change to VM's URL
    private static final String REST_URI = "http://localhost:8080/payments";

    private Client client = ClientBuilder.newClient();

    public Response registerClient(CreateAccountWithBalance accountWithBalance) {
        return client
                .target(REST_URI + "/register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(accountWithBalance, MediaType.APPLICATION_JSON));
    }

    public Response pay(Payment payment) {
        return client
                .target(REST_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));
    }

    public Response getAccountInfo(String accountId) {
        return client
                .target(REST_URI + "/" + accountId)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public Response getPayments() {
        return client
                .target(REST_URI)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public Response clean() {
        return client
                .target(REST_URI)
                .request()
                .delete();
    }

}
