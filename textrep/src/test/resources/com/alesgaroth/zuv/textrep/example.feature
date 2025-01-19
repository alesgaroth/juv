Feature: text entry to editor backend

  Scenario Outline: can create an algorithm
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I get the named <algorithm>
    Examples:
      | initial | commands                                            | algorithm |
      | empty   |                                                     | empty |
      | empty   | CreateNode foo                                      | oneNode |
      | empty   | CreateNode foo\nCreateNode bar\nConnect foo 0 bar 0 | twoConnectedNodes |

  Scenario Outline: doesn't create an incorrect algorithm
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I dont get the named <algorithm>
    Examples:
      | initial | commands                                            | algorithm |
      | empty   | CreateNode foo                                      | empty |
      | empty   | CreateNode foo                                      | twoConnectedNodes |
      | empty   | CreateNode foo\nCreateNode bar\nConnect foo 0 bar 0 | oneNode |
      | empty   | CreateNode foo\nCreateNode bar\nConnect foo 0 bar 0 | empty |

  Scenario Outline: can remove nodes and connections
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I get the named <algorithm>
    Examples:
      | initial           | commands                                  | algorithm |
      | oneNode           | DeleteNode foo                            | empty     |
      | twoConnectedNodes | DeleteNode foo                            | oneNode   |
      | twoConnectedNodes | DeleteNode bar                            | oneNode   |
      | empty             | DeleteNode foo                            | empty     |
      | oneNode           | DeleteNode bar                            | oneNode   |
