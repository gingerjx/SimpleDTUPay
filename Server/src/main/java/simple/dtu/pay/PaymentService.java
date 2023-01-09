package simple.dtu.pay;

import dtu.ws.fastmoney.*;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    // 676fe29f-62f0-4bdf-a899-b332c420d4e9
    private BankService bankService = new BankServiceService().getBankServicePort();
    private List<String> createdAccounts = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();


    public String registerUser(CreateAccountWithBalance accountWithBalance) throws BankServiceException_Exception {
        String createdAccount = bankService.createAccountWithBalance(accountWithBalance.getUser(), accountWithBalance.getBalance());
        createdAccounts.add(createdAccount);
        return createdAccount;
    }

    public void processPayment(Payment payment) throws BankServiceException_Exception {
        Account customer = bankService.getAccount(payment.getCustomerId());
        Account merchant = bankService.getAccount(payment.getMerchantId());
        bankService.transferMoneyFromTo(customer.getId(), merchant.getId(), payment.getAmount(), "Description");
        payments.add(payment);
    }

    public Account getAccountInfo(String accountId) throws BankServiceException_Exception {
        return bankService.getAccount(accountId);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void clean() {
        payments = new ArrayList<>();
        createdAccounts.forEach(accountId -> {
            try {
                bankService.retireAccount(accountId);
            } catch (BankServiceException_Exception e) {
                throw new RuntimeException(e);
            }
        });
        createdAccounts = new ArrayList<>();
    }

}
