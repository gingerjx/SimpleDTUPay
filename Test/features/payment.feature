Feature: Counter
  Testing REST API of web server returning data of the Person

  Scenario: Make Payment between cid1 and mid1
    Given register a customer with name "Emilie", lastname "Søndergaard", cpr "130264-4736" and initial balance 50
    And register a merchant with name "Jesper", lastname "Møller", cpr "090337-0661" and initial balance 50
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is successful
    And the balance of the customer at the bank is 40 kr
    And the balance of the merchant at the bank is 60 kr

  Scenario: List of payments
    Given register a customer with name "Emilie", lastname "Søndergaard", cpr "130264-4736" and initial balance 50
    And register a merchant with name "Jesper", lastname "Møller", cpr "090337-0661" and initial balance 50
    When the merchant initiates a payment for 10 kr by the customer
    And the list of payments is retrieved
    Then the list contains a payments where customer paid 10 kr to merchant

  Scenario: Customer is not registered
    Given a customer with id "cid"
    And a merchant with id "mid"
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is not successful
    And an error message is returned saying "Account does not exist"

  Scenario: Customer is not registered
    Given register a customer with name "Emilie", lastname "Søndergaard", cpr "130264-4736" and initial balance 50
    And a merchant with id "mid"
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is not successful
    And an error message is returned saying "Account does not exist"

  Scenario: Customer is not registered
    Given a customer with id "cid"
    And register a merchant with name "Jesper", lastname "Møller", cpr "090337-0661" and initial balance 50
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is not successful
    And an error message is returned saying "Account does not exist"
