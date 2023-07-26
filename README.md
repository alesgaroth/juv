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


## Development
Some philosophizing is in order.

I want to make it all test driven.  So write a test. See it fail. Record it failed.
write test that should make it pass.  Try it.  If it doesn't reset the prodcution code.
If it passed, check test and production code in. as well as the record of what failed.

During refactoring, everytime you make a change, run the tests. Back out the production changes if they failed a test.
And record which tests failed.
If it passed check in test, production code, and failed test records.


When merging, roll up the record of what failed to get an idea of what's failing.    We need to do something to
discount old fails lowering the roll up for tests that haven't failed for a long time.

That way on changes we can test early things that have broken recently and delay things that haven't failed in a while.
And other ideas too, like every PR test the recent broken tests, and less often run the other tests.

Acceptance tests are allowed to be broken for a long time, but eventually start passing and after many passes without
fail get moved to a stable state, which can kick off a release process.

Once we have that, we can also include the acceptance tests as part of a feature implementation ticket and auto close
the feature ticket when its acceptance tests are stable.

Bug tickets could work the same way.   A bug implies we can write a tests for it.  When the bug test starts passing
the ticket is closed.

No branching!  Small checkins. Continuously integrated to trunk.  Each commit gets reviewed with 24 hours by someone
else.  At release time, there's a day where everything is getting reviewed but otherwise no gating.

Each commit creates a task to review that commit, somehow so it doesn't get lost.

If commits are small, pretested, very few bugs should be able to get in.

Reviews are after the fact, to look for better ways to do things. Make the code quality better.
