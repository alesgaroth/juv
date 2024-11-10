Feature: text entry to editor backend

  Scenario Outline: can create an algorithm
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I get the named <algorithm>
    Examples:
      | initial | commands | algorithm |
      | empty | CreateNode foo| oneNode |
      | oneNode | CreateNode bar\nConnect foo 0 bar 0 | twoConnectedNodes |
