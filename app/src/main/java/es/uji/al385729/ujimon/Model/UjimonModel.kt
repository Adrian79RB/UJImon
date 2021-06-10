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
        WAITING,
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
        if(row == 1)
            return ujimonInstances.ujimonArray[index]
        else
            return ujimonInstances.ujimonArray[index + 5]
    }

    fun createEnemyTrainer1Team(){
        var selectedUjimon : Int
        for( i in 0 until 2){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer1.ujimonTeam))
            enemyTrainer1.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset,ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
            enemyTrainer1.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun createEnemyTrainer2Team(){
        var selectedUjimon : Int
        for( i in 0 until 3){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer2.ujimonTeam))
            enemyTrainer2.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
            enemyTrainer2.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun createEnemyTrainer3Team(){
        var selectedUjimon : Int
        for( i in 0 until 4){
            do {
                selectedUjimon = Random.nextInt(0,9)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer3.ujimonTeam))
            enemyTrainer3.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type)
            enemyTrainer3.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun computerAttack(computerEnemy : Trainer, player : Trainer): Attack? {
        var attackSelected : Attack? = null
        var attackIndex = 0
        val probability = Random.nextFloat()

        if(probability > 0.1){
            do{
                attackIndex = if(computerEnemy.ujimonSelected!!.type == player.ujimonSelected!!.type && probability < 0.5)
                    Random.nextInt(2, 4)
                else
                    Random.nextInt(0, 4)
                attackSelected = computerEnemy.ujimonSelected!!.ujimonAttacks[attackIndex]
            } while (attackSelected == null || attackSelected.currentAmount <= 0)
        }
        return attackSelected
    }

    fun ujimonAlreadySelected(ujimonSelected: Ujimon, ujimonTeam: Array<Ujimon>): Boolean {
        for(ujimon in ujimonTeam){
            if(ujimonSelected.name == ujimon.name)
                return true
        }

        return false
    }

    fun playerSelectUjimon(ujimonSelected: Ujimon, ujimonTeam: Array<Ujimon>) {
        for(i in ujimonTeam.indices){
            if(ujimonSelected.name == ujimonTeam[i].name){
                ujimonTeam[i] = Ujimon(0f, "", null, null, false, Type.NORMAL)
                return
            }
        }

        for(i in ujimonTeam.indices){
            if(ujimonTeam[i].name == "") {
                ujimonTeam[i] = ujimonSelected
                break
            }
        }
    }

    fun checkPlayerUjimonTeamDead() : Boolean {
        var ujimonAlive = 0
        for (ujimon in playerTrainer.ujimonTeam) {
            if (!ujimon.dead)
                ujimonAlive++
        }

        return ujimonAlive == 0
    }

    fun checkEnemyUjimonTeamDead(currentEnemy : Trainer) : Boolean {
        var ujimonAlive = 0
        for(ujimon in currentEnemy.ujimonTeam){
            if(!ujimon.dead && ujimon.type != Type.NORMAL)
                ujimonAlive++
        }

        return ujimonAlive == 0
    }
}
