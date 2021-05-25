package es.uji.al385729.ujimon.Model

import android.widget.TextView
import es.uji.al385729.ujimon.Assets

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
        PLAYER_TURN,
        PLAYER_ATTACK,
        PLAYER_CHOOSE_UJIMON,
        COMPUTER_TURN,
        HEALTH_HEALING,
        END
    }

    var state = UjimonState.UJIMON_SELECTION
    var lastState = UjimonState.UJIMON_SELECTION

    fun changeModelState(newState : UjimonState) {
        lastState = state
        state = newState
    }

    fun initializeUjimon(){
        var maglugInstance : Ujimon = Ujimon(1000f,"Maglug", Assets.maglugAsset, false, Type.FIRE)
        var obshoInstance : Ujimon = Ujimon(1000f, "Obsho", Assets.obshoAsset, false, Type.DARKNESS)
        var redashInstance : Ujimon = Ujimon(1000f, "Redash", Assets.redashAsset, false, Type.PLANT);
        var sworsthInstance : Ujimon = Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS);
    }
}
