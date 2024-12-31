This is embarrassing.

Zuv is an data flow system that is intended to show all connections between modules.
To show the flow of data from module to module, variable to function to variable.
To show state machines, and static logic.  To make debug dead easy since you can see where a value came from.

Juv is the Java portion of it.  Java so we have access to Android systems.
How much will stay in Java, I don't know.

### What Zuv (and Juv) is for:
  Have you ever looked at code that calculates two values and you really just want one of them, but they're completely complected together?
  Have you ever wished you could see how data flows through your program?
  Have you ever tried to track down Race Conditions?
  
Zuv is an attempt to build something so you can just grab one output of a function and not calculate all the other, see in a graphical way how data flows through a program, and see everywhere where a variable is being set.

It may be ambitious.  It may be impossible.  This project is an attempt to find out.

### Thoughts:

In Zuv, you don't modify the source code. You write a transaction to change the program..

Zuv expose what most systems find hard to model. Memory allocation, threads, race conditions,

Zuv is coding by clicking/tapping
