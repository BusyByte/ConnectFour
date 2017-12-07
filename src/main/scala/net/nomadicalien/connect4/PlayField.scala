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

  def selectCell(playField: PlayField, columnNumber: ColumnNumber, player: Player): PlayField =
    ??? // TODO: implement


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

  def isDiagnonalFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    ??? // TODO: implement
  }

  def isFourConnected(playField: PlayField, lastPlayedColumn: ColumnNumber, player: Player): Boolean = {
    isVerticalFourConnected(playField, lastPlayedColumn, player) ||
    isHorizontalFourConnected(playField, lastPlayedColumn, player) ||
    isDiagnonalFourConnected(playField, lastPlayedColumn, player)
  }

}

class ColumnNumber(val number: Int) extends AnyVal

object ColumnNumber {
  def unapply(input: String): Option[ColumnNumber] = input.trim.toCharArray.toList match {
    case d :: _  if d.isDigit => Some(new ColumnNumber(d.toInt))
    case _ => None
  }
}



