package dtu.example;

import fastmoney.Account;
import fastmoney.CreateAccountWithBalance;
import fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

/*
	Replace the class with your own step definition
   	classes.
 */
public class PaymentSteps {

    RESTClient restClient = new RESTClient();
    Response response = null;
    String customerId = null;
    String merchantId = null;
    int amount;

    @After
    public void beforeEach() {
        restClient.clean();
    }

    @Given("register a customer with name {string}, lastname {string}, cpr {string} and initial balance {int}")
    public void registerACustomerWithNameLastnameCprAndInitialBalance(String name, String lastname, String cpr, int balance) {
        customerId = restClient.registerClient(getAccountWithBalance(name, lastname, cpr, balance)).readEntity(String.class);
    }

    @Given("register a merchant with name {string}, lastname {string}, cpr {string} and initial balance {int}")
    public void registerAMerchantWithNameLastnameCprAndInitialBalance(String name, String lastname, String cpr, int balance) {
        merchantId = restClient.registerClient(getAccountWithBalance(name, lastname, cpr, balance)).readEntity(String.class);
    }

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        customerId = cid;
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        merchantId = mid;
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int a) {
        amount = a;
        response = restClient.pay(new Payment(customerId, merchantId, BigDecimal.valueOf(amount)));
    }

    @And("the list of payments is retrieved")
    public void theListOfPaymentsIsRetrieved() {
        response = restClient.getPayments();
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertEquals(200, response.getStatus());
    }

    @Then("the list contains a payments where customer paid {int} kr to merchant")
    public void theListContainsAPaymentsWhereCustomerPaidKrToMerchant(int amount) {
        List<Payment> payments = response.readEntity(new GenericType<List<Payment>>() {});
        assertEquals(200, response.getStatus());
        assertEquals(1, payments.size());
        assertEquals(customerId, payments.get(0).getCustomerId());
        assertEquals(merchantId, payments.get(0).getMerchantId());
        assertEquals(BigDecimal.valueOf(amount), payments.get(0).getAmount());
    }

    private static CreateAccountWithBalance getAccountWithBalance(String name, String lastname, String cpr, int balance) {
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastname);
        user.setCprNumber(cpr);
        CreateAccountWithBalance accountWithBalance = new CreateAccountWithBalance();
        accountWithBalance.setBalance(BigDecimal.valueOf(balance));
        accountWithBalance.setUser(user);
        return accountWithBalance;
    }


    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertEquals(404, response.getStatus());
    }

    @And("an error message is returned saying {string}")
    public void anErrorMessageIsReturnedSaying(String message) {
        String responseMessage = response.readEntity(String.class);
        assertEquals(message, responseMessage);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(int a) {
        Account account = restClient.getAccountInfo(customerId).readEntity(Account.class);
        assertEquals(BigDecimal.valueOf(a), account.getBalance());
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(int a) {
        Account account = restClient.getAccountInfo(merchantId).readEntity(Account.class);
        assertEquals(BigDecimal.valueOf(a), account.getBalance());
    }
}

