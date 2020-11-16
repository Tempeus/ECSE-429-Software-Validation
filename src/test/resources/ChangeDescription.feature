Feature: a student, I want to change a task description, to better represent the work to do.

  Background:
    Given the Todo API server is running

  Scenario Outline: Change a task description (Normal Flow)
    Given "<description>" is the new description of the task
    And "<taskid>" is the given task id to be changed
    When the user posts a request to the server
    Then the task description will be changed to "<description>"

    Examples:
      | taskid   | description              |
      | 1 | Introduction to Computer Science |
      | 2 | Software Validation              |
      | 2 | Discrete Structures              |

  Scenario Outline: Change a task description that is related to a project (Alternative Flow)
    Given "<description>" is the new description of the task
    And "<taskid>" is the given task id to be changed
    And is related to projects with id "<tasksofid>"
    When the user posts a request to the server
    Then the task description will be changed to "<description>"

    Examples:
      | taskid| description                        | tasksofid |
      | 1    | Introduction to Computer Science 1  | 1         |
      | 2    | Software Validation 1              |  2         |
      | 3    | Discrete Structures 1             |   3         |

  Scenario Outline: Change a description for a non-existent task (Error Flow)
    Given "<description>" is the new description of the task
    And "<taskid>" is the given id of the non-existent task
    When the user posts a request to the server
    Then a "<Error Message>" error message 404 will be displayed

    Examples:
      | taskid | description                      | Error Message |
      | -1   | Introduction to Computer Science | 404           |
