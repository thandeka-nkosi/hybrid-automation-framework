Feature: Register user

  Scenario Outline: Verify user can register on Para Bank website
    Given the user on the landing page
    When user clicks on register link
    And sign up form is displayed
    And the user captures "<First Name>","<Last Name>","<Address>", "<City>", "<State>", "<Zip Code>", "<Phone>", "<SSN>"
    And the user clicks register button
    Then the user is registered and logged in

    Examples:
      |First Name|Last Name|Address         |City      |State  |Zip Code|Phone     |SSN|
      |Ban       |McNight    |55 Market Street|Boksburg |Gauteng|1459    |0117144402|n/a|


