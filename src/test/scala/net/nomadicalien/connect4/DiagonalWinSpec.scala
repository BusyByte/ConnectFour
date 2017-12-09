package net.nomadicalien.connect4

import net.nomadicalien.connect4.PlayField.PlayField
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*

Given player 1 is in control of the playfield

When there are 4 red colored cells diagonally adjacent to each other
Then player 2 is not in control of the playfield
And the players see a prompt that the game is over
And the players see in the prompt that player 1 has won
And the players see in the prompt an option to play another game
option to play another game

And vice versa


*/
class DiagonalWinSpec extends Specification {
  "player 1" should {
    "win diagonally" in new context {
      val picks = List(
        0, 1,
        1, 2,
        2, 6,
        2, 5,
        3, 3,
        3, 4,
        3
      )

      val newState = performPicks(playerOneInControlPlayState, picks)
      newState must beLike {
        case g @ GameOverState(_, _, _, winner) if winner.number == 1 =>
          val prompt = gameOverInstructor.instruct(g).split(lineSeparator).toList
          prompt must_== List(Instructions.gameOverMessage, "player1 has won", Instructions.playNewGame)
      }
    }
  }

  "player 2" should {
    "win diagonally" in new context {
      val picks = List(
        1, 0,
        0, 1,
        2, 1,
        5, 2,
        0, 3,
        4, 3,
        3, 2,
        4, 3
      )

      val newState = performPicks(playerOneInControlPlayState, picks)
      newState must beLike {
        case g @ GameOverState(_, _, _, winner) if winner.number == 2 =>
          val prompt = gameOverInstructor.instruct(g).split(lineSeparator).toList
          prompt must_== List(Instructions.gameOverMessage, "player2 has won", Instructions.playNewGame)
      }

    }
  }


  trait context extends Scope with TestData {

    def performPicks(state: InPlayState, picks: List[Int]): GameState = {
      picks.foldLeft[GameState](state) { (state, pick) =>
        state match {
          case i: InPlayState =>
            stateTransition.transition(pick.toString, i)
          case _ => state
        }
      }
    }

    import StateTransition.Implicits._
    lazy val stateTransition = implicitly[StateTransition[InPlayState]]

    import Instructor.Implicits._
    lazy val gameOverInstructor = implicitly[Instructor[GameOverState]]
  }
}
