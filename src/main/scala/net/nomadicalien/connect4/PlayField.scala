package net.nomadicalien.connect4

import net.nomadicalien.connect4


object PlayField {
  type Columns[A] = Vector[A]
  type Rows[A] = Vector[A]
  type PlayField = Columns[Rows[Cell]]

  lazy val numColumns =  7
  lazy val numRows = 6

  lazy val empty:PlayField = Vector.fill(numColumns, numRows)(EmptyCell)

  def isColumnFull(playField: PlayField, column: ColumnNumber): Boolean = playField(column.number).forall {
    case EmptyCell => false
    case SelectedCell(_) => true
  }

  //TODO: should be able to pass in non-full column using method above
  def selectCell(playField: PlayField, columnNumber: ColumnNumber, player: Player): PlayField = {
    val currentColumn = playField(columnNumber.number)
    val indexToUpdate: Option[Int] = currentColumn.zipWithIndex.collectFirst {
      case (EmptyCell, index) => index
    }
    indexToUpdate.fold(playField) { index =>
      playField.updated(columnNumber.number, currentColumn.updated(index, SelectedCell(player.color)))
    }
  }


  def isVerticalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    val playersColumnSelected = playField(lastPlayedColumn.number).filterNot {
      case EmptyCell => true
      case _ => false
    }.reverse.takeWhile {
      case SelectedCell(color) if color == player.color  => true
      case _ => false
    }.take(4)
    playersColumnSelected.length == 4
  }

  def isHorizontalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    val currentColumn = playField(lastPlayedColumn.number)
    val filledCells = currentColumn.takeWhile {
      case SelectedCell(_) => true
      case _ => false
    }
    val playedRowIndex = filledCells.length - 1
    val horizontalCells = (0 until numColumns).map { columnNumber =>
      playField(columnNumber)(playedRowIndex)
    }
    val count: Int = horizontalCells.foldLeft(0) {
      case (acc, _) if acc == 4 => acc
      case (acc, SelectedCell(color)) if color == player.color => acc + 1
      case _ => 0
    }

    count == 4
  }

  def isDiagonalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    isUpHillDiagonalFourConnected(playField, lastPlayedColumn, player) ||
    isDownHillDiagonalFourConnected(playField, lastPlayedColumn, player)
  }

  /**
    * Connected like /
    */
  def isUpHillDiagonalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    val currentColumn = playField(lastPlayedColumn.number)
    val filledCells = currentColumn.takeWhile {
      case SelectedCell(_) => true
      case _ => false
    }
    val playedRowIndex = filledCells.length - 1
    def findLeftBase(columnIndex: Int, rowIndex: Int): (Int, Int) = {
      if(rowIndex == 0 || columnIndex == 0) {
        (columnIndex, rowIndex)
      } else {
        findLeftBase(columnIndex-1, rowIndex -1)
      }
    }
    val baseLeft = findLeftBase(lastPlayedColumn.number, playedRowIndex)
    def accumulateDiagonals(columnIndex: Int, rowIndex: Int, acc: List[Cell]): List[Cell] = {
      if(columnIndex == numColumns || rowIndex == numRows) {
        acc
      } else {
        accumulateDiagonals(columnIndex + 1, rowIndex + 1, playField(columnIndex)(rowIndex) :: acc)
      }
    }

    val diagonalCells = accumulateDiagonals(baseLeft._1, baseLeft._2, Nil)
    val count: Int = diagonalCells.foldLeft(0) {
      case (acc, _) if acc == 4 => acc
      case (acc, SelectedCell(color)) if color == player.color => acc + 1
      case _ => 0
    }

    count == 4

  }

  /**
    * Connected like \
    *
    */
  def isDownHillDiagonalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    val currentColumn = playField(lastPlayedColumn.number)
    val filledCells = currentColumn.takeWhile {
      case SelectedCell(_) => true
      case _ => false
    }
    val playedRowIndex = filledCells.length - 1
    def findLeftBase(columnIndex: Int, rowIndex: Int): (Int, Int) = {
      if (columnIndex == 0 || rowIndex == (numRows-1)) {
        (columnIndex, rowIndex)
      } else {
        (columnIndex-1, rowIndex+1)
      }
    }
    val baseLeft = findLeftBase(lastPlayedColumn.number, playedRowIndex)
    def accumulateDiagonals(columnIndex: Int, rowIndex: Int, acc: List[Cell]): List[Cell] = {
      if(columnIndex == numColumns || rowIndex < 0) {
        acc
      } else {
        accumulateDiagonals(columnIndex + 1, rowIndex -1, playField(columnIndex)(rowIndex) :: acc)
      }
    }
    val diagonalCells = accumulateDiagonals(baseLeft._1, baseLeft._2, Nil)
    val count: Int = diagonalCells.foldLeft(0) {
      case (acc, _) if acc == 4 => acc
      case (acc, SelectedCell(color)) if color == player.color => acc + 1
      case _ => 0
    }

    count == 4
  }

  def isFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    isVerticalFourConnected(playField, lastPlayedColumn, player) ||
    isHorizontalFourConnected(playField, lastPlayedColumn, player) ||
    isDiagonalFourConnected(playField, lastPlayedColumn, player)
  }

}

class ColumnNumber(val number: Int) extends AnyVal

object ColumnNumber {
  def unapply(input: String): Option[ColumnNumber] = input.trim.toCharArray.toList match {
    case d :: _ if d.isDigit =>
      val intValue = d.toString.toInt
      if(intValue >= 0 && intValue < PlayField.numColumns) {
        Some(new ColumnNumber(intValue))
      } else {
        None
      }

    case _ => None
  }
}



