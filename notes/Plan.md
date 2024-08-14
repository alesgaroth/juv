My Plan:

TDD X layers
   Storage
   Design
   Instantiation
   Runtime
   Type
   Compilation
   Optimization

Storage
  Is how we store Source Code

Design
  Is Object Code.  The layout information for Instances.
  Layer 1
  Nodes, Connections, I/O,  Constants, Variables, Algorithms, Functions,
   Dependencies, Operations
  You can think of Algorithms like functions in a normal language.  It's an
  encapsulation of how to calculate something.  But unlike a function which
  runs and then finishes, algorithms don't run.  

Instantiation
  Is the individual stack frames in a calculation.
  Layer 2

Runtime
  Is the whether the instance is even interesting for the present calculations
  Layer 3

Types
  What objects can hook to what objects
 
Compilation
  How can we make this less interpreted

Optimization
  How can we make this fast
