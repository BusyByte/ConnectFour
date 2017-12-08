package net.nomadicalien.connect4

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/*
Given a playfield
And two players
When the players load the playfield
Then the players see a playfield that is a grid with 6 rows and 7 columns
And the players see a prompt to play a new game
*/
class NewGamePromptSpec extends Specification {
  "InitialState" should {
    "render a play field" in new context {
      val expectedDisplay =
        List(
          "| 0 | 1 | 2 | 3 | 4 | 5 | 6 |",
          "|   |   |   |   |   |   |   |",
          "|   |   |   |   |   |   |   |",
          "|   |   |   |   |   |   |   |",
          "|   |   |   |   |   |   |   |",
          "|   |   |   |   |   |   |   |",
          "|   |   |   |   |   |   |   |",
        )


      import Writer.Implicits._
      val result = implicitly[Writer[InitialState]].write(gameState).split(lineSeparator).toList
      result.zipWithIndex.foreach {
        case (value, i) => value must_== expectedDisplay(i)
      }
    }

    "prompt to play a new game" in new context {
      import Instructor.Implicits._
      implicitly[Instructor[InitialState]].instruct(gameState) must_== Instructions.playNewGame
    }

  }

  trait context extends Scope {
    lazy val gameState = GameState.initial
  }
}
