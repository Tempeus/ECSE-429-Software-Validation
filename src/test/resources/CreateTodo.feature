Feature: a student, I create a to do list for a new class I am taking, so I can manage course work.

  Background:
    Given the Todo API server is running

    Scenario Outline: Create a new class todo with a title (Normal Flow)
      Given "<title>" is the title of the class
      When the user creates a new to do list for a class
      Then a todo list instance with "<title>" will be created

      Examples:
        | title   |
        | COMP250 |
        | ECSE429 |
        | MATH240 |

    Scenario Outline: Create a new class todo with a title, done status and description (Alternative Flow)
      Given "<title>" is the title of the class
      And "<active>" is the active state of the class
      And "<completed>" is the completion state of the class
      And "<description>" is the description of the class
      When the user creates a new to do list for a class
      Then a todo instance with "<title>", "<active>", "<completed>", "<description>" will be created

      Examples:
        | title   | active | completed | description                       |
        | COMP250 | true   | false     | Introduction-to-Computer Science  |
        | ECSE429 | true   | true      | Software-Validation               |
        | MATH240 | false  | false     | Discrete-Structures               |


    Scenario Outline: Create a new class todo without a title but has a done status and description (Error flow)
      Given "<completed>" is the completion state of the class
      And "<description>" is the description of the class
      When the user creates a new to do list for a class
      Then error 404 will occur

    Examples:
      | completed  | description                        |
      | true      | Introduction-to-Computer Science   |
      | true      | Software-Validation                |
      | false     | Discrete-Structures                |