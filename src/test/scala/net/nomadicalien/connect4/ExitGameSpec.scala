package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*

Given the players see a prompt with an option to play another game

When the players select no
Then the program exits


*/
class ExitGameSpec extends Specification {
  "InitialState" should {
    "ExitGame when no selected" in new context {
      import StateTransition.Implicits._
      val newState = implicitly[StateTransition[InitialState]].transition("no", initialState)
      newState must beLike {
        case ExitGameState => ok
      }
    }
  }

  trait context extends Scope with TestData
}
