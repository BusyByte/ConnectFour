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

    implicit def exitGameStateTransition = new StateTransition[ExitGameState.type ] {
      override def transition(input: String, current: ExitGameState.type) = current
    }


    implicit def gameStateTransition = new StateTransition[GameState] {
      def transition(input: String, current: GameState): GameState = current match {
        case s: InitialState => implicitly[StateTransition[InitialState]].transition(input, s)
        case s: EnterPlayerOneState => implicitly[StateTransition[EnterPlayerOneState]].transition(input, s)
        case s: EnterPlayerTwoState => implicitly[StateTransition[EnterPlayerTwoState]].transition(input, s)
        case s: InPlayState => implicitly[StateTransition[InPlayState]].transition(input, s)
        case s: GameOverState => implicitly[StateTransition[GameOverState]].transition(input, s)
        case s @ ExitGameState => implicitly[StateTransition[ExitGameState.type]].transition(input, s)
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