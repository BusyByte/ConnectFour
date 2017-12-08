package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*

Given a prompt to enter player 2’s name

When a player enters a name
And the player presses enter
Then the players see a playfield that is a grid with 6 rows and 7 columns
And player 1 is assigned to the red color
And player 2 is assigned to the black color
And player 1 is in control of the playfield



*/
class BeginGameSpec extends Specification {
  "EnterPlayerTwoState" should {

    "playfield has correct number of rows and columns" in new context {
      newState must beLike {
        case InPlayState(playField, _, _, _) =>
          playField.length must_=== PlayField.numColumns
          playField(0).length must_=== PlayField.numRows
      }
    }

    "player1 is red" in new context {
      newState must beLike {
        case InPlayState(_, Player1(_, _, Red), _, _) => ok
      }
    }

    "player2 is black" in new context {
      newState must beLike {
        case InPlayState(_, _, Player2(_, _, Black), _) => ok
      }
    }

    "player1 in control" in new context {
      newState must beLike {
        case InPlayState(_, p1, _, control) => p1 must_== control
      }
    }
  }

  trait context extends Scope {
    import StateTransition.Implicits._
    lazy val playField = PlayField.empty
    lazy val player1 = Player1(new NonEmptyString("player1"))
    lazy val state = EnterPlayerTwoState(playField, player1)
    lazy val player2Name = "player2"
    lazy val player2 = Player2(new NonEmptyString(player2Name))
    lazy val newState = implicitly[StateTransition[EnterPlayerTwoState]].transition(player2Name, state)
  }
}
