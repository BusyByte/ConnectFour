# ConnectFour
- Created for [December CIJUG Connect Four Code Off](https://github.com/central-iowa-java-users-group/connect-four/blob/master/README.md)

## Running
- Install Java 8 JDK
- Install [SBT](https://www.scala-sbt.org)
- Clone this GitHub repo
- Open a shell (command prompt) and navigate to project directory
- execute the following command: 
```bash
sbt run
```
- Have fun!

## About
- Scala
- Everything is immutable
- Uses [Type Classes](http://danielwestheide.com/blog/2013/02/06/the-neophytes-guide-to-scala-part-12-type-classes.html) for printing the game state, giving instructions, and transitioning from one state to another.
  - Decouples data and behavior
  - Compartmentalizes logic per game state
  - Implicit proof required in order to compile
- Use of types in place of primitives to prevent mistakes
  - [Value Classes](https://docs.scala-lang.org/overviews/core/value-classes.html) erase to primitives at runtime
- Everything is values and only real side effects is printing in Main  
- Game loop is tail recursive
  - Functional looping rather than while or for statement  
- No libraries
  - I've created my own limited versions of some things 
  which exist in [ScalaZ](https://github.com/scalaz/scalaz) or [Cats](https://typelevel.org/cats) libraries for simplicity
- Use [specs2](https://etorreborre.github.io/specs2) for testing
  - Tests run in parallel by default
  - Has a [Given When Then](https://etorreborre.github.io/specs2/guide/SPECS2-4.0.0/org.specs2.guide.GivenWhenThenStyle.html) syntax but chose not to do that to save time  
- Formatted source with [scalafmt](http://scalameta.org/scalafmt)
  - Can setup to auto-format on compile but didn't want to introduce plugin for simplicity    

## Further thoughts
- Set up compiler settings so if basic warnings and bad practices used then will just not compile
  - [tpolecat compiler settings](https://tpolecat.github.io/2017/04/25/scalac-flags.html)
- Set up [Wart Remover](http://www.wartremover.org) (compiler linter) to catch other bad practices and prevent compilation if violated
- Could look at using [state monad](http://eed3si9n.com/learning-scalaz/State.html#State+and+StateT) or [indexed state](https://youtu.be/eO1JLs5FR6k) monad possibly for state transitions
- Bad input goes back to original state with no really notifications
  - Could use [Validation](http://eed3si9n.com/learning-scalaz/Validation.html) from ScalaZ or Cats
- Could introduce packages and scope type creation to go through smart constructors 
which provide errors or validations if the type can not be realized
  - Could also use [ScalaZ Tags](http://eed3si9n.com/learning-scalaz/Tagged+type.html)
  - Could also use [refinement types](https://github.com/fthomas/refined)
- Eliminate some of the boilerplate of pattern matching on sealed trait co-product with [shapeless type class derivation](https://github.com/milessabin/shapeless/blob/master/examples/src/main/scala/shapeless/examples/derivation.scala#L108)
- Updating nested immutable collections is a bit painful
  - Use [Lens](http://eed3si9n.com/learning-scalaz/Lens.html) to update easier
- Still a little bit of primitive obsession with newlines and empty strings.
  - Should make this a list of case classes and add the newlines in Main

