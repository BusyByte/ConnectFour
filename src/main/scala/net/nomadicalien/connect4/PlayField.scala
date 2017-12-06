package net.nomadicalien.connect4


object PlayField {
  type Columns[A] = Array[A]
  type Rows[A] = Array[A]
  type PlayField = Columns[Rows[Cell]]

  val numColumns =  7
  val numRows = 6

  def empty:PlayField = Array.fill(numColumns, numRows)(EmptyCell)
}



