package net.nomadicalien.connect4

import net.nomadicalien.connect4.PlayField.PlayField

sealed trait GameState

case class InitialState(
                       playField: PlayField
                       ) extends GameState

case class EnterPlayerOneState(
                         playField: PlayField
                       ) extends GameState

case class EnterPlayerTwoState(
                         playField: PlayField,
                         player1: Player
                       ) extends GameState

case class InPlayState(
                         playField: PlayField,
                         player1: Player,
                         player2: Player,
                         turn: Player
                       ) extends GameState

case class GameOverState(
                           playField: PlayField,
                           player1: Player,
                           player2: Player,
                           winner: Player
                         ) extends GameState


object GameState {
  lazy val empty = InitialState(PlayField.empty)
}