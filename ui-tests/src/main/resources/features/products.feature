Feature: Add product to cart

  Background:
    Given user is on Sauce Lab login page
    And user captures login credentials "standard_user" and "secret_sauce" and clicks on the login button

  Scenario: Verify user can add a product to cart
    Given the user is on the products page
    When the user adds the following products to cart
      | Product                  |
      | Sauce Labs Bike Light    |
      | Sauce Labs Backpack      |
      | Sauce Labs Onesie        |
    Then the following products are visible in the cart
      | Product                  |
      | Sauce Labs Bike Light    |
      | Sauce Labs Backpack      |
      | Sauce Labs Onesie        |
