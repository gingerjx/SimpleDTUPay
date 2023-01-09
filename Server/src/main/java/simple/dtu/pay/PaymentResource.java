package simple.dtu.pay;

import dtu.ws.fastmoney.CreateAccountWithBalance;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payments")
public class PaymentResource {

    private PaymentService paymentService = new PaymentService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(Payment payment) {
        try {
            paymentService.processPayment(payment);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(CreateAccountWithBalance accountWithBalance) {
        try {
            return Response.ok(paymentService.registerUser(accountWithBalance)).build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments() {
        return Response.ok(paymentService.getPayments()).build();
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountInfo(@PathParam("accountId") String accountId) {
        try {
            return Response.ok(paymentService.getAccountInfo(accountId)).build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @DELETE
    public Response clean() {
        paymentService.clean();
        return Response.ok().build();
    }

}