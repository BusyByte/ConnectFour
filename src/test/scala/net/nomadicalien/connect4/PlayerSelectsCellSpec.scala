package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*

Given player 1 is in control of the playfield

When player 1 selects a cell of the grid
Then that cell is filled with the red color
And player 2 is in control of the playfield

And vice versa
*/
class PlayerSelectsCellSpec extends Specification {
  "player1 in control" should {
    "cell is filled with red" in new player1Context {
      newState must beLike {
        case s: InPlayState =>
          s.playField(0)(0) must beLike {
            case SelectedCell(Red) => ok
          }
      }
    }

    "player2 is in control" in new player1Context {
      newState must beLike {
        case s: InPlayState if s.turn.number == 2 => ok
      }
    }

  }

  "player2 in control" should {
    "cell is filled with black" in new player2Context {
      newState must beLike {
        case s: InPlayState =>
          s.playField(0)(0) must beLike {
            case SelectedCell(Black) => ok
          }
      }
    }

    "player1 is in control" in new player2Context {
      newState must beLike {
        case s: InPlayState if s.turn.number == 1 => ok
      }
    }

  }

  trait player1Context extends Scope with TestData {
    import StateTransition.Implicits._
    lazy val stateTransition = implicitly[StateTransition[InPlayState]]
    val newState = stateTransition.transition("0", playerOneInControlPlayState)
  }

  trait player2Context extends Scope with TestData {
    import StateTransition.Implicits._
    lazy val stateTransition = implicitly[StateTransition[InPlayState]]
    val newState = stateTransition.transition("0", playerTwoInControlPlayState)
  }
}
