Feature: a student, I remove an unnecessary task from my course to do list, so I can forget about it.

  Background:
    Given the Todo API server is running

  Scenario Outline: Remove a task from my course to do list (Normal Flow)
    Given "<title>" is the title of the task to be removed
    And "<titleproject>" is the title of the to do list
    When the user posts a request to the server to remove a task "<title>" from "<titleproject">
    Then the to do "<titleproject>" list will no longer have the task "<title>"

    Examples:
      | title   | titleproject       |
      | comp250 | CSstuff            |
      | ecse429 | softwarevaldiation |

  Scenario Outline: Remove a task that has a category from a course to do list(Alternative Flow)
    Given "<title>" is the title of the task to be removed
    And "<titleproject>" is the title of the to do list
    And "<titlecat>" is the id of the task category related to "<task>"
    When the user posts a request to the server to remove a task "<title>" from "<titleproject">
    Then the to do "<titleproject>" list will no longer have the task "<title>"

    Examples:
      | title   | titleproject       | titlecat |
      | comp250 | CSstuff            | house    |
      | ecse429 | softwarevaldiation | campuse  |


  Scenario Outline: Remove a non-existent task from my course to do list (Error Flow)
    Given the id of a non-existent task is "<id>"
    And "<titleproject>" is the title of the to do list
    When the user posts a request to the server to remove a task "<title>" from "<titleproject">
    Then a "<message>" error with "<id>" message will be displayed

    Examples:
      | id | titleproject                      | message                                  | title |
      | -1 | Introduction-to-Computer-Science | Could not find an instance with todos/-1 | comp  |
      | 0  | Introduction-to-Computer-Science | Could not find an instance with todos/0  | ecse  |



