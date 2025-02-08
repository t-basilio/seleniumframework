@regression
Feature: Error validations

  @errorValidations
  Scenario Outline: Loggin user exception
    Given I landed on Ecommerce page
    When Logged in with username <username> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples:
      | username         | password    |
      | shetty@gmail.com | Iamking@001 |