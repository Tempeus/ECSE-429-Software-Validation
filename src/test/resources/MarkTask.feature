Feature: a student, I mark a task as done on my course to do list, so I can track my accomplishments.

  Background:
    Given the todo manager API server is already running

  Scenario Outline: mark a not done task as done (Normal Flow)
    Given a todo with the title "<title>" and done status "<prevDoneStatus>"
    When the user requests to mark the task "<title>" with a done status "<nextDoneStatus>"
    Then the task "<title>" will be marked with the done status "<nextDoneStatus>"

    Examples:
      | title       | prevDoneStatus | nextDoneStatus |
      | Assignment1 | false          | true           |
      | Assignment2 | false          | true           |
      | Quiz1       | false          | true           |

  Scenario Outline: mark a done task as done (Alternative Flow)
    Given a todo with the title "<title>" and done status "<prevDoneStatus>"
    When the user requests to mark the task "<title>" with a done status "<nextDoneStatus>"
    Then the task "<title>" will be marked with the done status "<nextDoneStatus>"

    Examples:
      | title       | prevDoneStatus | nextDoneStatus |
      | Assignment1 | true           | true           |
      | Assignment2 | true           | true           |
      | Quiz1       | true           | true           |

  Scenario Outline: mark a non-existing task as done (Error Flow)
    Given no todo with id "<id>" is registered in the API server
    When the user requests to mark the task "<id>" with a done status "<doneStatus>"
    Then system will output an error with error code "<errorCode>"

    Examples:
      | id   | doneStatus | errorCode |
      | 100  | true       | 404       |
      | -1   | true       | 404       |

