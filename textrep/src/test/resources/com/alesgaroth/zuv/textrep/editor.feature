Feature: backend for an editor (MVP scenarios)

  Scenario: create node
    Given an <initial> algorithm
    When I give the following "<commands>"
    Then I get the named <algorithm>
    Examples:
      | initial | commands                                            | algorithm |
      | empty   |                                                     | empty |
      | empty   | CreateNode foo                                      | oneNode |
      | empty   | CreateNode foo\nCreateNode bar\nConnect foo 0 bar 0 | twoConnectedNodes |
  Scenario: delete node
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
  Scenario: set the java function behind a node
    It will add the inputs and outputs of the function
  Scenario: can run a node with a java function behind it
  Scenario: set the algorithm behind a node
    It will add the inputs and outputs of the algorithm
  Scenario: enter an algorithm
  Scenario: can run a node with a valid algorithm behind it
  Scenario: can add inputs to a node
  Scenario: can add outputs to a node
  Scenario: can remove inputs from a node
  Scenario: can remove outputs from a node
  Scenario: connect input of one node to the output of another
  Scenario: connect input of one node to the closure of another
  Scenario: connect application input of one node to the closure of another
  Scenario: connect application input of one node to the output of another
  Scenario: remove connection from input of one node to the output of another
  Scenario: remove connection from input of one node to the closure of another
  Scenario: remove connection from application input of one node to the closure of another
  Scenario: remove connection from application input of one node to the output of another
  Scenario: label connections
  Scenario: label nodes
  Scenario: label inputs
  Scenario: label outputs
  Scenario: from a node choose to edit its algorithm
  Scenario: copy algorithm so we're not editing the default one
  Scenario: set X,Y for a node
  Scenario: set way point for connection
  Scenario: reset way points for connection
