Feature: a student, I remove an unnecessary task from my course to do list, so I can forget about it.

  Background:
    Given the Todo API server is running

  Scenario Outline: Remove a task from my course to do list (Normal Flow)
    Given <taskid> is the id of the task
    And <todolistid> is the id of the to do list
    When the user posts a request to the server
    Then the to do list will no longer have the task

    Examples:
      | todolistid   | taskid         |
      | 1            | 1              |
      | 1            |                |

  Scenario Outline: Remove a task from a non-existent my course to do list (Error Flow)
    Given <taskid> is the id of the task
    And <todolistid> is the id of the non-existent to do list
    When the user posts a request to the server
    Then a 404 error message will be displayed

    Examples:
      | todolistid  | taskid  | Error Message|
      | 2           | 1       | 404          |


  Scenario Outline: Remove a non-existent task from my course to do list (Normal Flow)
    Given "<taskid>" is the id of the non-existent task
    And "<todolistid>" is the id of the to do list
    When the user posts a request to the server
    Then a 404 error message will be displayed

    Examples:
      | todolistid  | taskid  | Error Message|
      | 1           | 2       | 404          |


