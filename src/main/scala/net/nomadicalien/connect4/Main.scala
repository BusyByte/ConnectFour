package net.nomadicalien.connect4

class Main extends App {

  def gameLoop(gameState: GameState): Unit = {
    import Writer.Implicits._
    println(implicitly[Writer[GameState]].write(gameState))

    import Instructor.Implicits._
    println(implicitly[Instructor[GameState]].instruct(gameState))

    val input = Console.in.readLine()

    import StateTransition.Implicits._
    val updatedState = implicitly[StateTransition[GameState]].transition(input, gameState)
    updatedState match {
      case ExitGameState => ()
      case _ => gameLoop(updatedState)
    }

  }

  gameLoop(GameState.initial)
}
