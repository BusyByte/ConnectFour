package net.nomadicalien.connect4

import net.nomadicalien.connect4.PlayField.PlayField

import scala.collection.immutable

trait Writer[ITEM] {
  def write(i: ITEM): String
}


object Writer {

  object Implicits {

    implicit def emptyCellWriter = new Writer[EmptyCell.type]{
      override def write(e: EmptyCell.type) = " "
    }

    implicit def colorWriter = new Writer[Color] {
      override def write(c: Color) = c match {
        case Red => "R"
        case Black => "B"
      }
    }


    implicit def cellWriter = new Writer[Cell] {
      override def write(c: Cell) = c match {
        case e @ EmptyCell => implicitly[Writer[EmptyCell.type]].write(e)
        case SelectedCell(color) => implicitly[Writer[Color]].write(color)
      }

    }

    implicit def playFieldWriter = new Writer[PlayField] {
      override def write(state: PlayField): String = {
        val columnHeaders = (0 until PlayField.numColumns).mkString("| ", " | ", " |")

        val rows: immutable.Seq[String] = (0 until PlayField.numRows).map { r =>
          val rowDisplay: immutable.Seq[String] = (0 until PlayField.numColumns).map { c =>
            val cell = state(c)(r)
            implicitly[Writer[Cell]].write(cell)
          }
          rowDisplay.mkString(s"| ", " | ", " |")
        }


        columnHeaders + lineSeparator + rows.mkString(lineSeparator)
      }
    }

    implicit def initialStateWriter = new Writer[InitialState] {
      override def write(state: InitialState): String = {
        implicitly[Writer[PlayField]].write(state.playField)
      }
    }




  }
}