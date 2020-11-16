Feature: a student, I query the incomplete tasks for a class I am taking, to help better manage my time.

  Background:
    Given the todo manager API server is already running
    And the user is registered to a class

  Scenario Outline: query all incomplete tasks for a class (Normal Flow)
    Given <amount> todos with the title "<title>", done status "<doneStatus>" and class "<className>"
    When the user queries all tasks with done status "<doneStatus>" and class "<className>"
    Then <amount> tasks with done status "<doneStatus>" and class "<className>" will be returned.

    Examples:
      | amount | title        | doneStatus | className |
      | 1      | FinalProject | false      | ECSE429   |
      | 2      | Assignment   | false      | ECSE429   |
      | 3      | Quiz         | false      | ECSE429   |

  Scenario Outline: query all complete tasks for a class (Alternative Flow)
    Given <amount> todos with the title "<title>", done status "<doneStatus>" and class "<className>"
    When the user queries all tasks with done status "<doneStatus>" and class "<className>"
    Then <amount> tasks with done status "<doneStatus>" and class "<className>" will be returned.

    Examples:
      | amount | title        | doneStatus | className |
      | 1      | FinalProject | true       | ECSE429   |
      | 2      | Assignment   | true       | ECSE429   |
      | 3      | Quiz         | true       | ECSE429   |

  Scenario Outline: query class with no tasks (Error Flow)
    Given no todos with class "<className>"
    When the user queries all tasks with done status "<doneStatus>" and class "<className>"
    Then <amount> tasks with done status "<doneStatus>" and class "<className>" will be returned.

    Examples:
      | amount | doneStatus | className |
      | 0      | false      | ECSE429   |

