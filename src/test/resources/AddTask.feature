Feature: Add Task
  As a student, 
  I add a task to a course to do list,
  so I can remember it.

  Background: 
    Given the todo manager API server is already running

  Scenario Outline: Adding a new unique task to a todo list (Normal Flow)
    Given there exists a todo list in the system with title "<listTitle>"
    When I add a new task with the title "<taskTitle>" to the todo list "<listTitle>"
    Then there will be a new task with title "<taskTitle>" in the todo list "<listTitle>"

    Examples: 
      | taskTitle         | listTitle |
      | Relax             | Today     |
      | Finish Assignment | General   |
      | Watch Netflix     | Today     |

  Scenario Outline: Adding a pre-existing task to a todo list (Alternate Flow)
    Given there exists a todo list in the system with title "<listTitle>"
    When I add a new task with title "<taskTitle>" to the todo list "<listTitle>"
    Then there will be a new task with title "<taskTitle>" in the todo list "<listTitle>"

    Examples: 
      | taskTitle | listTitle |
      | Youtube   | Today     |
      | Youtube   | Today     |
      | Youtube   | Today     |

  Scenario Outline: Adding a task to a non-existent todo list (Error Flow)
    Given there does not exist a todo list with title "<listTitle>"
    When I add a new task with title "<taskTitle>" to the todo list "<listTitle>"
    Then the system will inform the user that the todo list "<listTitle>" is non-existent

    Examples: 
      | taskTitle | listTitle |
      | Study     | oaskjd    |
