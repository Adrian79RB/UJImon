package es.uji.al385729.ujimon.Model

import android.widget.TextView
import es.uji.al385729.ujimon.Assets
import kotlin.random.Random

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
    val ujimonInstances = UjimonInstances()



    fun changeModelState(newState : UjimonState) {
        lastState = state
        state = newState
    }

    fun selectUjimon(index : Int, row : Int): Ujimon {

    }

    fun createEnemyTrainer1Team(){
        var selectedUjimon : Int
        for( i in 0 until 2){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer1.ujimonTeam[0].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer1.ujimonTeam[1].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer1.ujimonTeam[2].name)
            enemyTrainer1.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset,ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
        }
    }

    fun createEnemyTrainer2Team(){
        var selectedUjimon : Int
        for( i in 0 until 3){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer2.ujimonTeam[0].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer2.ujimonTeam[1].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer2.ujimonTeam[2].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer2.ujimonTeam[3].name )
            enemyTrainer2.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
        }
    }

    fun createEnemyTrainer3Team(){
        var selectedUjimon : Int
        for( i in 0 until 4){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer3.ujimonTeam[0].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer3.ujimonTeam[1].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer3.ujimonTeam[2].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer3.ujimonTeam[3].name && ujimonInstances.ujimonArray[selectedUjimon].name != enemyTrainer3.ujimonTeam[4].name)
            enemyTrainer3.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
        }
    }
}
