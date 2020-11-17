Feature: a student, I remove a to do list for a class which I am no longer taking, to declutter my schedule.

  Background:
    Given the Todo API server is running

  Scenario Outline: Remove a to do list (Normal Flow)
    Given "<title>" is the title of the to do list to be removed
    When the user posts a request to the server to remove a to do list "<title>"
    Then the to do list with "<title>" will no longer be in the schedule

    Examples:
      | title   |
      | comp250 |
      | ecse429 |


  Scenario Outline: Remove a to do list related to a category (Alternative Flow)
    Given "<title>" is the title of the to do list to be removed
    And "<titlecat>" is the title of the category related to the to do list "<title>"
    When the user posts a request to the server to remove a to do list "<title>"
    Then the to do list with "<title>" will no longer be in the schedule

    Examples:
      | title   | titlecat |
      | comp250 | house    |
      | ecse429 | campuse  |

  Scenario Outline: Change a description for a non-existent task (Error Flow)
    Given the id of a non-existent to do list is "<id>"
    When the user posts a request to the server to remove a to do list "<title>"
    Then an error message "<message>" with "<id>" will occur
    Examples:
      | id | message                                  | title |
      | -1 | Could not find an instance with todos/-1 | comp  |
      | 0  | Could not find an instance with todos/0  | ecse  |