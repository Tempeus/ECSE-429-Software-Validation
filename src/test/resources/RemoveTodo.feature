Feature: a student, I remove a to do list for a class which I am no longer taking, to declutter my schedule.

  Background:
    Given the Todo API server is running

  Scenario Outline: Remove a to do list (Normal Flow)
    Given "<id>" is the id of the to do list to remove a to do list
    When the user posts a request to the server to remove a to do list
    Then the to do list will no longer be in the schedule

    Examples:
      | id  |
      | 1   |
      | 2   |

  Scenario Outline: Remove a to do list related to a category (Alternative Flow)
    Given "<todolistid>" is the id of the to do list to remove a to do list
    And "<categoryid>" is the id of the category related to the to do list
    When the user posts a request to the server to remove a to do list
    Then the to do list will no longer have the task
    And the task will be deleted

    Examples:
      | todolistid   | categoryid |
      | 1           |   1         |
      | 2          |    1          |

  Scenario Outline: Remove a non-existent to do list (Error Flow)
    Given "<id>" is the id of a non-existent list
    When the user posts a request to the server to remove a to do list
    Then the to do list will no longer be in the schedule

    Examples:
      | id  |
      | 1   |
      | 2   |