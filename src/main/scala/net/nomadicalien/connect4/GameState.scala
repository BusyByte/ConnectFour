package net.nomadicalien.connect4

import net.nomadicalien.connect4.PlayField.PlayField

sealed trait GameState

case class InitialState(
                       playField: PlayField
                       ) extends GameState

object GameState {
  lazy val empty = InitialState(PlayField.empty)
}