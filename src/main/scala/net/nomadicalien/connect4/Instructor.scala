package net.nomadicalien.connect4

trait Instructor[A] {
  def instruct(a: A): String
}

object Instructions {
  lazy val playNewGame = "Play a new game?"
}

object Instructor {
  object Implicits {
    implicit def initialStateInstructor = new Instructor[InitialState] {
      override def instruct(state: InitialState): String = Instructions.playNewGame
    }
  }
}
