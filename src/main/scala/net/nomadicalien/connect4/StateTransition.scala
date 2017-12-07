package net.nomadicalien.connect4

trait StateTransition[CURRENT] {
  def transition(input: String, current: CURRENT): GameState
}

object StateTransition {
  object Implicits {
    implicit def initialStateTransition = new StateTransition[InitialState] {
      override def transition(input: String, current: InitialState) = input match {
        case Answer(YesAnswer) => EnterPlayerOneState(current.playField)
        case Answer(NoAnswer) => ExitGameState
        case _ => current
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
            if(PlayField.isFourConnected(updatedPlayField, columnNumber, current.turn)) {
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
      override def transition(input: String, current: GameOverState) = input match {
          case Answer(YesAnswer) => EnterPlayerOneState(PlayField.empty)
          case Answer(NoAnswer) => ExitGameState
          case _ => current
        }
    }

  }
}

class NonEmptyString(val value: String) extends AnyVal

object NonEmptyString {
  def unapply(input: String): Option[NonEmptyString] = input.trim match {
    case s if s.nonEmpty => Some(new NonEmptyString(s))
    case _ => None
  }
}

sealed trait Answer
case object YesAnswer extends Answer
case object NoAnswer extends Answer


object Answer {
  def unapply(input: String): Option[Answer] = input.toLowerCase.trim.toCharArray.toList match {
    case 'y' :: _ => Some(YesAnswer)
    case 'n' :: _ => Some(NoAnswer)
    case _ => None
  }
}