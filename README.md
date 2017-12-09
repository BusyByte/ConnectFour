# ConnectFour
- Created for [December CIJUG Connect Four Code Off](https://github.com/central-iowa-java-users-group/connect-four/blob/master/README.md)

## Running
- Install Java 8 JDK
- Install SBT
- Clone this GitHub repo
- Open a shell (command prompt) and navigate to project directory
- run `sbt run`
- Have fun!

## About
- Scala
- Everything is immutable
- Uses Type Classes for printing the game state, giving instructions, and transitioning from one state to another.
  - Decouples data and behavior
  - Compartmentalizes logic per game state
  - Implicit proof required in order to compile
- Use of types in place of primitives to prevent mistakes
  - Value Classes erase to primitives at runtime
- Game loop is tail recursive
  - Functional looping rather than while or for statement  
- No libraries
  - I've created my own limited versions of some things 
  which exist in ScalaZ or Cats libraries for simplicity
- Use specs2 for testing
  - Tests run in parallel by default
  - Have a Given When Then syntax but chose not to do that to save time  
- Formatted source with scalafmt
  - Can setup to autoformat on compile but didn't want to introduce plugin for simplicity    

## Further thoughts
- Could look at using state monad or indexed state monad possibly for state transitions
- Bad input goes back to original state with no really notifications
  - Could use Validation from ScalaZ or Cats
- Could introduce packages and scope type creation to go through smart constructors 
which provide errors or validations if the type can not be realized   