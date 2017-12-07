package net.nomadicalien.connect4

sealed trait Player {
  def name: String
  def number: Int
  def color: Color
}

case class Player1(name: String, number: Int, color: Color) extends Player
case class Player2(name: String, number: Int, color: Color) extends Player

object Player1 {
  def apply(name: String) = Player1(name, 1, Red)
}

object Player2 {
  def apply(name: String) = Player2(name, 2, Black)
}