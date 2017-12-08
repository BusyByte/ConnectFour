package net.nomadicalien.connect4

trait Instructor[A] {
  def instruct(a: A): String
}

object Instructions {
  lazy val playNewGame = "Play a new game?"
  lazy val enterPlayer1Name = "Enter player 1’s name"
  lazy val enterPlayer2Name = "Enter player 2’s name"
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


  }
}
