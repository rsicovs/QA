Feature: Check that correct item is added in the cart

  @SmokeTest
  Scenario: Login into the website and add item to the cart. Check that the correct item is in the cart.
    When Enter url in browser
    And All login elements are visible
    And I fill username field: "<username>"
    And I fill password field: "<password>"
    And I click on Login button
    And I have successfully logged in
    And Products are available in the store
    And I add item with "<productName>" to the cart
    Then I check that the correct item with "<productName>" is in the cart
    Examples:
      | username                | password     | productName             |
      | standard_user           | secret_sauce | Sauce Labs Backpack     |
      | standard_user           |              | Sauce Labs Backpack     |
      | locked_out_user         | secret_sauce | Sauce Labs Bike Light   |
      | problem_user            | secret_sauce | Sauce Labs Backpack     |
      | standard_user           | secret_sauce | Sauce Labs Fleece Jacket|
      | error_user              | secret_sauce | Sauce Labs Onesie       |
      | visual_user             | secret_sauce | Sauce Labs Backpack     |
      | random                  | random       | Sauce Labs Onesie       |
      |                         |              | Sauce Labs Bike Light   |
      | standard_user           | secret_sauce | fictitious name of item |
