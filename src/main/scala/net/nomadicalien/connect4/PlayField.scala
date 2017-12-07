package net.nomadicalien.connect4


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
    val currentColunn = playField(columnNumber.number)
    val indexToUpdate: Option[Int] = currentColunn.zipWithIndex.collectFirst {
      case (EmptyCell, index) => index
    }
    indexToUpdate.fold(playField) { index =>
      playField.updated(columnNumber.number, currentColunn.updated(index, SelectedCell(player.color)))
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
    ??? // TODO: implement
  }

  def isDiagonalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    ??? // TODO: implement
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
    case d :: _  if d.isDigit && d.toInt >=0 && d.toInt < PlayField.numColumns => Some(new ColumnNumber(d.toInt))
    case _ => None
  }
}



