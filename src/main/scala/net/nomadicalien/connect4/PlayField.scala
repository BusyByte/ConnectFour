package net.nomadicalien.connect4


object PlayField {
  type Columns[A] = Vector[A]
  type Rows[A] = Vector[A]
  type PlayField = Columns[Rows[Cell]]

  lazy val numColumns =  7
  lazy val numRows = 6

  lazy val empty:PlayField = Vector.fill(numColumns, numRows)(EmptyCell)
}



