package net.nomadicalien.connect4

import scala.annotation.tailrec

object Main extends App {

  @tailrec def gameLoop(gameState: GameState): Unit = {
    import Writer.Implicits._
    println()
    println(implicitly[Writer[GameState]].write(gameState))

    import Instructor.Implicits._
    println(implicitly[Instructor[GameState]].instruct(gameState))

    val input = Console.in.readLine()

    import StateTransition.Implicits._
    val updatedState = implicitly[StateTransition[GameState]].transition(input, gameState)
    updatedState match {
      case ExitGameState => ()
      case _             => gameLoop(updatedState)
    }

  }

  gameLoop(GameState.initial)
}
