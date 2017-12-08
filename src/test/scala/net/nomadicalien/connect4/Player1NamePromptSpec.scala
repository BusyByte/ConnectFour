package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*

Given a prompt to play a new game

And two players
When the players select to play a new game
Then the players are prompted to enter player 1’s name

*/
class Player1NamePromptSpec extends Specification {
  "EnterPlayerOneState" should {

    "select to play new game" in new context {
      import StateTransition.Implicits._
      val result = implicitly[StateTransition[InitialState]].transition("yes", initialState)
      result must beLike {
        case EnterPlayerOneState(_) => ok
      }
    }

    "prompt to enter player one name" in new context {
      import Instructor.Implicits._
      val result = implicitly[Instructor[EnterPlayerOneState]].instruct(enterPlayerOneState)
      result must_== Instructions.enterPlayer1Name
    }

  }

  trait context extends Scope with TestData
}
