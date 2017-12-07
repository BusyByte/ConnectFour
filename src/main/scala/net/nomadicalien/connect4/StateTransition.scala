package net.nomadicalien.connect4

trait StateTransition[CURRENT] {
  def transition(input: String, current: CURRENT): GameState
}

object StateTransition {
  object Implicits {
    implicit def initialStateTransition = new StateTransition[InitialState] {
      override def transition(input: String, current: InitialState) = input match {
        case Confirmed(_) => EnterPlayerOneState(current.playField)
        case _ => ExitGameState //TODO: need to check for no and not just default to this
      }
    }

    implicit def enterPlayerOneStateTransition = new StateTransition[EnterPlayerOneState] {
      override def transition(input: String, current: EnterPlayerOneState) = input match {
        case NonEmptyString(name) => EnterPlayerTwoState(current.playField, Player1(name))
        case _ => current
      }
    }


    implicit def enterPlayerTwoStateTransition = new StateTransition[EnterPlayerTwoState] {
      override def transition(input: String, current: EnterPlayerTwoState) = input match {
        case NonEmptyString(name) =>
          val player1 = current.player1
          InPlayState(current.playField, player1, Player2(name), player1)
        case _ => current
      }
    }

    implicit def inPlayStateTransition = new StateTransition[InPlayState] {
      override def transition(input: String, current: InPlayState) = input match {
        case ColumnNumber(columnNumber) =>
          if(PlayField.isColumnFull(current.playField, columnNumber)) {
            current
          }
          else {
            val updatedPlayField = PlayField.selectCell(current.playField, columnNumber, current.turn)
            if(PlayField.isFourConnected(updatedPlayField)) {
              GameOverState(updatedPlayField, current.player1, current.player2, current.turn)
            } else {
              val nextTurn = {
                if(current.turn.number == current.player1.number)
                  current.player2
                else
                  current.player1
              }

              current.copy(updatedPlayField, current.player1, current.player2, nextTurn)
            }
          }


        case _ => current

      }
    }


    implicit def gameOverStateTransition = new StateTransition[GameOverState] {
      override def transition(input: String, current: GameOverState) = ??? // TODO: implement like initial
    }

  }
}



object NonEmptyString {
  def unapply(input: String): Option[String] = input.trim match {
    case s if s.nonEmpty => Some(s) //TODO: should return Type
    case _ => None
  }
}

object Confirmed {
  def unapply(input: String): Option[String] = input.toLowerCase.trim.toCharArray.toList match {
    case 'y' :: _ => Some(input)
    case _ => None
  }
}