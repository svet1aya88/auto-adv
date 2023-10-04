Feature: User login

  Rule: User cannot log into Report Portal with invalid credentials

    Scenario: Invalid user login
      When User logs into Report Portal with username and password
        | [blank]          | [blank]          |
        | [blank]          | 1q2w3e           |
        | default          | [blank]          |
        | invalid username | 1q2w3e           |
        | default          | invalid password |
        | invalid username | invalid password |
      Then Erroneous field is highlighted

  Rule: User of different type (default, admin) is able to log into Report Portal with valid credentials

    Scenario Outline: Valid user login
      When User logs into Report Portal with username "<username>" and password "<password>"
      Then User avatar is <displayed> in the left-side menu
      Examples:
        | username   | password | displayed |
        | default    | 1q2w3e   | true      |
        | superadmin | erebus   | true      |