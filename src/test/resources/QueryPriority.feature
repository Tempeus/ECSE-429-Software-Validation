Feature: a student, I query all incomplete HIGH priority tasks from all my classes, to identity my short-term goals.

  Background:
    Given the todo manager API server is already running
    And HIGH, MEDIUM and LOW categories are registered in the todo manager API

  Scenario Outline: query all incomplete HIGH priority tasks (Normal Flow)
    Given <amount> todos with the title "<title>", done status "<doneStatus>" and priority "<priority>"
    When the user queries all tasks with done status "<doneStatus>" and priority "<priority>"
    Then <amount> tasks with done status "<doneStatus>" and priority "<priority>" will be returned.

    Examples:
      | amount | title        | doneStatus | priority |
      | 1      | FinalProject | false      | HIGH     |
      | 2      | Assignment1  | false      | HIGH     |
      | 3      | Quiz1        | false      | HIGH     |

  Scenario Outline: query all tasks by done status and priority (Alternative Flow)
    Given <amount> todos with the title "<title>", done status "<doneStatus>" and priority "<priority>"
    When the user queries all tasks with done status "<doneStatus>" and priority "<priority>"
    Then <amount> tasks with done status "<doneStatus>" and priority "<priority>" will be returned.

    Examples:
      | amount | title        | doneStatus | priority |
      | 1      | FinalProject | false      | LOW      |
      | 2      | Assignment1  | false      | MEDIUM   |
      | 3      | Quiz1        | false      | LOW      |

  Scenario Outline: query priority with no existing tasks (Error Flow)
    Given no todos with priority "<priority>"
    When the user queries all tasks with done status "<doneStatus>" and priority "<priority>"
    Then <amount> tasks with done status "<doneStatus>" and priority "<priority>" will be returned.

    Examples:
      | amount | doneStatus | priority |
      | 0      | false      | HIGH     |
      | 0      | false      | MEDIUM   |
      | 0      | false      | LOW      |

