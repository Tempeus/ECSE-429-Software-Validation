Feature: Adjust Priority
  As a student, 
  I want to adjust the priority of a task, 
  to help better manage my time.

  Background: 
    Given the todo manager API server is already running
    And HIGH, MEDIUM and LOW categories are registered in the todo manager API

  Scenario Outline: Student changed priority to a different priority (Normal Flow)
    Given the task with title "<title>" exists and has "<old_priority>" priority
    When the student changes the task "<title>" from "<old_priority>" priority to "<new_priority>" priority
    Then the priority of the task "<title>" should be "<new_priority>"

    Examples: 
      | title    | old_priority | new_priority |
      | FACC300  | LOW          | MEDIUM       |
      | ECSE429  | MEDIUM       | HIGH         |
      | LeetCode | HIGH         | LOW          |
      | COMP551  | LOW          | HIGH         |
      | Grading  | MEDIUM       | LOW          |
      | Netflix  | HIGH         | MEDIUM       |

  Scenario Outline: Student changed priority to the same priority (Alternative Flow)
    Given the task with title "<title>" exists and has "<old_priority>" priority
    When the student changes the task "<title>" from "<old_priority>" priority to "<new_priority>" priority
    Then the priority of the task "<title>" should be "<new_priority>"

    Examples: 
      | title   | old_priority | new_priority |
      | Netflix | LOW          | LOW          |
      | COMP551 | MEDIUM       | MEDIUM       |
      | ECSE429 | HIGH         | HIGH         |

  Scenario Outline: Student attempted to change priority of non-existing task (Error Flow)
    Given the task with title "<title>" exists and has "<old_priority>" priority
    When the student changes the task with the wrong title "<wrongTitle>" from "<old_priority>" priority to "<new_priority>" priority
    Then the system shall inform the user that the task is non-existent

    Examples:
      | title   | old_priority | new_priority | wrongTitle |
      | Netflix | LOW          | LOW          | asdhj      |
      | COMP551 | MEDIUM       | MEDIUM       | aaccd      |
      | ECSE429 | HIGH         | HIGH         | rtqbg      |
