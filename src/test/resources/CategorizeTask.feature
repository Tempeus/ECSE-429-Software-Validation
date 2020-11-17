Feature: a student, I categorize tasks as HIGH, MEDIUM or LOW priority, so I can better manage my time.

  Background:
    Given the todo manager API server is already running
    Given HIGH, MEDIUM and LOW categories exist

  Scenario Outline: categorize a task with a priority (Normal Flow)
    Given a todo with the title "<title>", done status "<doneStatus>" and description "<description>"
    When the user requests to give the task "<title>" with a priority "<priority>"
    Then the task "<title>" will be assigned with the category "<priority>"

    Examples:
    | title   | doneStatus  | description     | priority  |
    | FACC300 | false       | Study for Exam  | HIGH      |
    | MATH240 | false       | Homework        | MEDIUM    |
    | COMP551 | false       | Project work    | LOW       |

  Scenario Outline: change the priority category of a task (Alternative Flow)
    Given a todo with the title "<title>", done status "<doneStatus>", description "<description>" and category "<priority>"
    When user request to update the category of "<title>" from "<priority>" to "<newPriority>"
    Then  task "<title>" will be assigned with a new category of "<newPriority>"

  Examples:
    | title   | doneStatus  | description     | priority  | newPriority |
    | FACC300 | false       | Study for Exam  | HIGH      | HIGH        |
    | MATH240 | false       | Homework        | MEDIUM    | LOW         |
    | COMP551 | false       | Project work    | LOW       | MEDIUM      |


  Scenario Outline: categorize a non-existing task with a priority (Error Flow)
    Given a todo with the title "<title>", done status "<doneStatus>" and description "<description>"
    When user request to categorize a todo with title "<fakeTitle>" with "<priority>"
    Then system will output an error

  Examples:
    | title     | doneStatus  | description     | fakeTitle | priority  |
    | ECSE420   | false       | Finish Homework | NULL      | LOW       |
    | MATH240   | false       | Watch Lectures  | MOTH240   | HIGH      |
