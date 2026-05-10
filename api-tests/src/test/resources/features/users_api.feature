Feature: Users API

  Scenario: Verify user can retrieve a single user
    When I send a GET request to retrieve user "2"
    Then the response status code should be 200
    And the response should contain user id "2"
    And the response header "Content-Type" should contain "application/json"