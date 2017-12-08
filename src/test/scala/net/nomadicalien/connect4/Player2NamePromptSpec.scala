package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*
Given a prompt to enter player 1’s name

When a player enters a name
And the player presses enter
Then the players are prompted to enter player 2’s name
*/
class Player2NamePromptSpec extends Specification {
  "EnterPlayerOneState" should {

    "enter name and transition" in new context {
      import StateTransition.Implicits._
      val newState = implicitly[StateTransition[EnterPlayerOneState]].transition(player1Name, enterPlayerOneState)
      newState must beLike {
        case EnterPlayerTwoState(_, _) => ok
      }
    }

    "prompt to enter player2 name" in new context {
      import Instructor.Implicits._
      val result = implicitly[Instructor[EnterPlayerTwoState]].instruct(enterPlayerTwoState)
      result must_== Instructions.enterPlayer2Name
    }

  }

  trait context extends Scope with TestData
}
