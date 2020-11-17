Feature: Add Task
  As a student, 
  I add a task to a course todo list,
  so I can remember it.

  Background: 
    Given the todo manager API server is already running

  Scenario Outline: Adding a new unique task to a todo list (Normal Flow)
    Given there exists a project with title "<projectTitle>"
    When I add a new task with the title "<taskTitle>" to the todo list "<projectTitle>"
    Then there will be a new task with title "<taskTitle>" in the todo list "<projectTitle>"

    Examples: 
      | taskTitle         | projectTitle |
      | Relax             | Today        |
      | Finish Assignment | General      |
      | Watch Netflix     | Today        |

  Scenario Outline: Adding a pre-existing task to a todo list (Alternate Flow)
    Given there exists a project with title "<projectTitle>"
    When I add a new task with the title "<taskTitle>" to the todo list "<projectTitle>"
    Then there will be a new task with title "<taskTitle>" in the todo list "<projectTitle>"

    Examples: 
      | taskTitle | projectTitle |
      | Youtube   | Today        |
      | Youtube   | Today        |
      | Youtube   | Today        |

  Scenario Outline: Adding a task to a non-existent todo list (Error Flow)
    Given there exists a project with title "<projectTitle>"
    When I add a new task with the title "<taskTitle>" to the wrong todo list "<wrongTitle>"
    Then the system will inform the user that the todo list "<wrongTitle>" is non-existent

    Examples: 
      | taskTitle | projectTitle | wrongTitle |
      | Study     | FACC300      | FACC250    |
