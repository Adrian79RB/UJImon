package es.uji.al385729.ujimon.Model

import android.widget.TextView

class UjimonModel(val playerTrainer : Trainer, val enemyTrainer1 : Trainer, val enemyTrainer2: Trainer, val enemyTrainer3 : Trainer, val soundPlayer : SoundPlayer) {

    interface SoundPlayer{
        fun playIntroMusic()
        fun playBattleMusic()
        fun playHealingMusic()
        fun playVictory()
        fun playDefeated()
        fun playAttackSound()
    }

    enum class UjimonState {
        UJIMON_SELECTION,
        UJIMON_SELECTED,
        PLAYER_TOURN,
        PLAYER_ATTACK,
        PLAYER_CHOOSE_UJIMON,
        COMPUTER_TOURN,
        HEALTH_HEALING,
        END
    }

    var state = UjimonState.UJIMON_SELECTION
    var lastState = UjimonState.UJIMON_SELECTION

    fun changeModelState(newState : UjimonState) {
        lastState = state
        state = newState
    }
}
