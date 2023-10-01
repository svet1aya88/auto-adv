Feature: User login

  Scenario: Invalid user login
    When User logs into Report Portal with username and password
      | [blank]          | [blank]          |
      | [blank]          | 1q2w3e           |
      | default          | [blank]          |
      | invalid username | 1q2w3e           |
      | default          | invalid password |
      | invalid username | invalid password |
    Then Erroneous field is highlighted
