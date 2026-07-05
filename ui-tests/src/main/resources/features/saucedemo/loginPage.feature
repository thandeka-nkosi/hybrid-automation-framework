Feature: Login to Sauce Lab

  Scenario Outline: User logs in with username and password

    Given user is on Sauce Lab login page
    And user captures login credentials "<username>" and "<password>" and clicks on the login button


    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
