Feature: a student, I want to change a task description, to better represent the work to do.

  Background:
    Given the Todo API server is running

  Scenario Outline: Change a task description (Normal Flow)
    Given the title of the task "<title>"
    When the user posts description change of task "<title>" to "<description>"
    Then the task "<title>" description will be changed to "<description>"

    Examples:
      | title   | description                      |
      | comp250 | Introduction-to-Computer-Science |
      | ecse429 | Software-Validation              |
      | math240 | Discrete-Structures              |

  Scenario Outline: Change a task description that is related to a project (Alternative Flow)
    Given the title of the task "<title>"
    And "<title>" is related to projects with title "<projecttitle>"
    When the user posts description change of task "<title>" to "<description>"
    Then the task "<title>" description will be changed to "<description>"

    Examples:
      | title   | description                      | projecttitle |
      | comp250 | Introduction-to-Computer Science | 1            |
      | ecse429 | Software-Validation              | 2            |
      | math240 | Discrete-Structures              | 3            |

  Scenario Outline: Change a description for a non-existent task (Error Flow)
    Given the id of a non-existent task is "<id>"
    When the user posts description change of task "<title>" to "<description>"
    Then an error message "<message>" with "<id>" will occur
    Examples:
      | id | description                      | message                                  | title |
      | -1 | Introduction-to-Computer-Science | Could not find an instance with todos/-1 | comp  |
      | 0  | Introduction-to-Computer-Science | Could not find an instance with todos/0  | ecse  |
