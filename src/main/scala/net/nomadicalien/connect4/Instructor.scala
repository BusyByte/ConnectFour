package net.nomadicalien.connect4

trait Instructor[A] {
  def instruct(a: A): String
}

object Instructions {
  lazy val playNewGame = "Play a new game?"
  lazy val enterPlayer1Name = "Enter player 1’s name"
  lazy val enterPlayer2Name = "Enter player 2’s name"
  lazy val gameOverMessage = "Game Over"
  lazy val chooseColumn = "Choose a column (0 through 6) and press the enter key"
}

object Instructor {
  object Implicits {
    implicit def initialStateInstructor = new Instructor[InitialState] {
      override def instruct(state: InitialState): String = Instructions.playNewGame
    }

    implicit def enterPlayerOneStateInstructor = new Instructor[EnterPlayerOneState] {
      override def instruct(state: EnterPlayerOneState): String = Instructions.enterPlayer1Name
    }

    implicit def enterPlayerTwoStateInstructor = new Instructor[EnterPlayerTwoState] {
      override def instruct(state: EnterPlayerTwoState): String = Instructions.enterPlayer2Name
    }

    implicit def gameOverStateInstructor = new Instructor[GameOverState] {
      override def instruct(state: GameOverState): String =
        Instructions.gameOverMessage + lineSeparator + s"Player ${state.winner.number} has won"
    }

    implicit def exitGameStateInstructor = new Instructor[ExitGameState.type] {
      def instruct(a: ExitGameState.type): String = {
        "" // TODO: Could do option so don't need empty strings
      }
    }

    implicit def inPlayStateInstructor = new Instructor[InPlayState] {
      def instruct(a: InPlayState): String = {
        Instructions.chooseColumn
      }
    }

    implicit def gameStateInstructor = new Instructor[GameState] {
      def instruct(state: GameState): String = state match {
        case s: InitialState => implicitly[Instructor[InitialState]].instruct(s)
        case s: EnterPlayerOneState => implicitly[Instructor[EnterPlayerOneState]].instruct(s)
        case s: EnterPlayerTwoState => implicitly[Instructor[EnterPlayerTwoState]].instruct(s)
        case s: InPlayState => implicitly[Instructor[InPlayState]].instruct(s)
        case s: GameOverState => implicitly[Instructor[GameOverState]].instruct(s)
        case s @ ExitGameState => implicitly[Instructor[ExitGameState.type]].instruct(s)
      }
    }


  }
}
