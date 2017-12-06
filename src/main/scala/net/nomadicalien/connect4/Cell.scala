package net.nomadicalien.connect4

sealed trait Cell

case object EmptyCell extends Cell
case class SelectedCell(color: Color)
