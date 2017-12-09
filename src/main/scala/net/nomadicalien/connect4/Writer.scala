package net.nomadicalien.connect4

import net.nomadicalien.connect4.PlayField.PlayField

import scala.collection.immutable

trait Writer[ITEM] {
  def write(i: ITEM): String
}

object Writer {

  object Implicits {

    implicit def emptyCellWriter = new Writer[EmptyCell.type] {
      override def write(e: EmptyCell.type) = " "
    }

    implicit def colorWriter = new Writer[Color] {
      override def write(c: Color) = c match {
        case Red   => "R"
        case Black => "B"
      }
    }

    implicit def cellWriter = new Writer[Cell] {
      override def write(c: Cell) = c match {
        case e @ EmptyCell       => implicitly[Writer[EmptyCell.type]].write(e)
        case SelectedCell(color) => implicitly[Writer[Color]].write(color)
      }

    }

    implicit def playFieldWriter = new Writer[PlayField] {
      override def write(state: PlayField): String = {
        val columnHeaders = (0 until PlayField.numColumns).mkString("| ", " | ", " |")

        val rows: immutable.Seq[String] = (PlayField.numRows - 1 to (0, -1)).map { r =>
          val rowDisplay: immutable.Seq[String] = (0 until PlayField.numColumns).map { c =>
            val cell = state(c)(r)
            implicitly[Writer[Cell]].write(cell)
          }
          rowDisplay.mkString("| ", " | ", " |")
        }

        columnHeaders + lineSeparator + rows.mkString(lineSeparator)
      }
    }

    implicit def initialStateWriter = new Writer[InitialState] {
      override def write(state: InitialState): String = {
        implicitly[Writer[PlayField]].write(state.playField)
      }
    }

    implicit def enterPlayerOneStateStateWriter = new Writer[EnterPlayerOneState] {
      override def write(state: EnterPlayerOneState): String = {
        implicitly[Writer[PlayField]].write(state.playField)
      }
    }

    implicit def enterPlayerTwoStateStateWriter = new Writer[EnterPlayerTwoState] {
      override def write(state: EnterPlayerTwoState): String = {
        implicitly[Writer[PlayField]].write(state.playField)
      }
    }

    implicit def inPlayStateWriter = new Writer[InPlayState] {
      override def write(state: InPlayState): String = {
        val playField = implicitly[Writer[PlayField]].write(state.playField)
        val turn      = s"${state.turn.name.value}'s turn (${state.turn.color})"
        List(
          playField,
          turn
        ).mkString(lineSeparator)
      }
    }

    implicit def gameOverStateWriter = new Writer[GameOverState] {
      override def write(state: GameOverState): String = {
        implicitly[Writer[PlayField]].write(state.playField)
      }
    }

    implicit def exitGameStateWriter = new Writer[ExitGameState.type] {
      override def write(state: ExitGameState.type): String = {
        ""
      }
    }

    implicit def gameStateWriter = new Writer[GameState] {
      def write(i: GameState): String = i match {
        case s: InitialState        => implicitly[Writer[InitialState]].write(s)
        case s: EnterPlayerOneState => implicitly[Writer[EnterPlayerOneState]].write(s)
        case s: EnterPlayerTwoState => implicitly[Writer[EnterPlayerTwoState]].write(s)
        case s: InPlayState         => implicitly[Writer[InPlayState]].write(s)
        case s: GameOverState       => implicitly[Writer[GameOverState]].write(s)
        case s @ ExitGameState      => implicitly[Writer[ExitGameState.type]].write(s)
      }
    }

  }
}
