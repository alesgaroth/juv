Feature: text entry to editor backend

  Scenario Outline: can create an algorithm
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I get the named <algorithm>
    Examples:
      | initial | commands                            | algorithm |
      | empty   |                                     | empty |
      | empty   | CreateNode foo                      | oneNode |
      | oneNode | CreateNode bar\nConnect foo 0 bar 0 | twoConnectedNodes |

  Scenario Outline: doesn't create incorrect an algorithm
    Given an <initial> algorithm
    When I give the following "<commands>"
    #Then I dont get the named <algorithm>
    Examples:
      | initial | commands                            | algorithm |
      | empty   | CreateNode foo                      | empty |
      | oneNode | CreateNode bar\nConnect foo 0 bar 0 | empty |
      | empty   | CreateNode foo                      | twoConnectedNodes |
      | oneNode | CreateNode bar\nConnect foo 0 bar 0 | oneNode |
