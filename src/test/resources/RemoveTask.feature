Feature: a student, I remove an unnecessary task from my course to do list, so I can forget about it.

  Background:
    Given the Todo API server is running

  Scenario Outline: Remove a task from my course to do list (Normal Flow)
    Given <taskid> is the id of the task
    And <todolistid> is the id of the to do list
    When the user posts a request to the server
    Then the to do list will no longer have the task

    Examples:
      | taskid        | todolistid    |
      | 1            | 1              |
      | 2            |   2             |

  Scenario Outline: Remove a task that has a category from a course to do list(Alternative Flow)
    Given <taskid> is the id of the task
    And <todolistid> is the id of the to do list
    And <categoryid> is the id of the task category
    When the user posts a request to the server
    Then the to do list will no longer have the task
    Then the to do list will no longer have the task

    Examples:
      | taskid       | todolistid      | categoryid |
      | 1            | 1               | 1          |
      | 2            |   1             |  1          |



  Scenario Outline: Remove a non-existent task from my course to do list (Error Flow)
    Given "<taskid>" is the id of the non-existent task
    And "<todolistid>" is the id of the to do list
    When the user posts a request to the server
    Then a "<Error Message>" error 404 message will be displayed

    Examples:
      | todolistid  | taskid  | Error Message|
      | 1           | 2       | 404          |


