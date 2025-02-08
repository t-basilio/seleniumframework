@regression
Feature: Purchase the order from Ecommerce Website

  Background:
    Given I landed on Ecommerce page

  @submitOrder
  Scenario Outline: Positive test of submitting the order
    Given Logged in with username <username> and password <password>
    When I add product <productName> from Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on confirmation page

    Examples:
    | username         | password    | productName |
    | shetty@gmail.com | Iamking@000 | QWERTY      |
