package net.nomadicalien.connect4

case class Player(name: String, number: Int, color: Color)

object Player1 {
  def apply(name: String) = Player(name, 1, Red)
}

object Player2 {
  def apply(name: String) = Player(name, 2, Black)
}