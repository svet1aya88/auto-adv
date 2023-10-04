Feature: Filters CRUD

  Background:
    Given User logs into Report Portal with username "default" and password "1q2w3e"
    And User opens Filters page

  Scenario: Create and update filter
    When User creates a new filter
    Then Success system message is displayed
    And User opens Filters page
    And User searches for filter
    Then Filter is found as search result
    And User updates filter name
    Then Success system message is displayed
    And User searches for filter
    Then Filter is found as search result