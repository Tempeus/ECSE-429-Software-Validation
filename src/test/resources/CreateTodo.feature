Feature: a student, I create a to do list for a new class I am taking, so I can manage course work.

  Background:
    Given the Todo API server is running

    Scenario Outline: Create a new class todo with a title (Normal Flow)
      Given "<title>" is the title of the class
      When the user posts a request to the server
      Then a todo instance with "<title>" will be created

      Examples:
        | title   |
        | COMP250 |
        | ECSE429 |
        | MATH240 |

    Scenario Outline: Create a new class todo with a title, done status and description (Alternative Flow)
      Given "<title>" is the title of the class
      And "<doneStatus>" is the done status of the class
      And "<description>" is the description of the class
      When the user posts a request to the server
      Then a todo instance with "<title>", "<doneStatus>", "<description>" will be created

      Examples:
        | title   | doneStatus  | description                       |
        | COMP250 | true      | Introduction to Computer Science  |
        | ECSE429 | true      | Software Validation               |
        | MATH240 | false     | Discrete Structures               |


    Scenario Outline: Create a new class todo without a title but has a done status and description (Error flow)
      Given "<doneStatus>" is the done status of the class
      And "<description>" is the description of the class
      When the user posts a request to the server
      Then error 404 will occur

    Examples:
      | doneStatus  | description                        |
      | true      | Introduction to Computer Science   |
      | true      | Software Validation                |
      | false     | Discrete Structures                |