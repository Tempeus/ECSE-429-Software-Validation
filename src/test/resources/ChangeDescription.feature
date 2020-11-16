Feature: a student, I want to change a task description, to better represent the work to do.

  Background:
    Given the Todo API server is running

  Scenario Outline: Change a task description (Normal Flow)
    Given <description> is the description of the task
    When the user posts a request to the server
    Then the task description will be changed to <description>

    Examples:
      | title   | description                       |
      | COMP250 | Introduction to Computer Science  |
      | ECSE429 | Software Validation               |
      | MATH240 | Discrete Structures               |

  Scenario Outline: Change a description for a non-existent task (Error Flow)
    Given <description> is the description of the task
    And task is non-existent
    When the user posts a request to the server
    Then an error 404 message will be displayed

    Examples:
      | title  | description                       | Error Message|
      | ""     | Introduction to Computer Science  | 404          |
