package net.nomadicalien.connect4

trait TestData {
  lazy val emptyPlayField = PlayField.empty
  lazy val player1Name = "player1"
  lazy val player1 = Player1(new NonEmptyString(player1Name))
  lazy val enterPlayerTwoState = EnterPlayerTwoState(emptyPlayField, player1)
  lazy val player2Name = "player2"
  lazy val player2 = Player2(new NonEmptyString(player2Name))
  lazy val enterPlayerOneState = EnterPlayerOneState(emptyPlayField)
  lazy val initialState = GameState.initial
  lazy val playerOneInControlPlayState = InPlayState(emptyPlayField, player1, player2, player1)
  lazy val playerTwoInControlPlayState = playerOneInControlPlayState.copy(turn = player2)
}
