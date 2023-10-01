Feature: Filters CRUD

  Background:
    Given User logs into Report Portal with username "default" and password "1q2w3e"
    Then User opens Filters page

  Scenario: Create a filter
    When User creates a new filter
    And Success system message is displayed
    And User searches for filter
    Then Filter is found as search result

  Scenario: Update filter name
    Given Filter is created
    When User searches for filter
    And User updates filter name
    And User searches for filter
    Then Filter is found as search result