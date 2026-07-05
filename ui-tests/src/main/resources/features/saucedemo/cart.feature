Feature: Verify cart badge count

  Background:
    Given user is on Sauce Lab login page
    And user captures login credentials "standard_user" and "secret_sauce" and clicks on the login button

  Scenario: Verify cart badge count updates after adding products
  Given the user is on the products page
  When the user adds the following products to cart
  | Product               |
  | Sauce Labs Bike Light |
  | Sauce Labs Backpack   |
Then the cart badge should display "2"