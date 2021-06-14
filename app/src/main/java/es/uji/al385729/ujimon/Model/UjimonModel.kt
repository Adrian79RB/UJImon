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
        fun playNormalAttackSound()
        fun playPowerAttackSound()
    }

    enum class UjimonState {
        UJIMON_SELECTION,
        UJIMON_SELECTED,
        PLAYER_TURN,
        PLAYER_CONDITION,
        PLAYER_ATTACK,
        PLAYER_CHOOSE_UJIMON,
        COMPUTER_TURN,
        COMPUTER_CONDITION,
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
                selectedUjimon = Random.nextInt(0,10)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer1.ujimonTeam))
            enemyTrainer1.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset,ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type, ujimonInstances.ujimonArray[selectedUjimon].condition)
            enemyTrainer1.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun createEnemyTrainer2Team(){
        var selectedUjimon : Int
        for( i in 0 until 3){
            do {
                selectedUjimon = Random.nextInt(0,10)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer2.ujimonTeam))
            enemyTrainer2.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type, ujimonInstances.ujimonArray[selectedUjimon].condition)
            enemyTrainer2.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun createEnemyTrainer3Team(){
        var selectedUjimon : Int
        for( i in 0 until 4){
            do {
                selectedUjimon = Random.nextInt(0,10)
            }
            while (ujimonAlreadySelected(ujimonInstances.ujimonArray[selectedUjimon], enemyTrainer3.ujimonTeam))
            enemyTrainer3.ujimonTeam[i] = Ujimon(ujimonInstances.ujimonArray[selectedUjimon].healthPoints, ujimonInstances.ujimonArray[selectedUjimon].name, ujimonInstances.ujimonArray[selectedUjimon].imageAsset, ujimonInstances.ujimonArray[selectedUjimon].buttonAsset, ujimonInstances.ujimonArray[selectedUjimon].dead, ujimonInstances.ujimonArray[selectedUjimon].type, ujimonInstances.ujimonArray[selectedUjimon].condition)
            enemyTrainer3.ujimonTeam[i].ujimonTolerance = ujimonInstances.ujimonArray[selectedUjimon].ujimonTolerance
        }
    }

    fun computerAttack(computerEnemy : Trainer, player : Trainer): Attack? {
        var attackSelected: Attack? = null
        var attackIndex: Int
        val probability = Random.nextFloat()

        do {
            attackIndex = if (computerEnemy.ujimonSelected.type == player.ujimonSelected.type && probability < 0.5)
                Random.nextInt(2, 4)
            else
                Random.nextInt(0, 4)
            attackSelected = computerEnemy.ujimonSelected.ujimonAttacks[attackIndex]
            computerEnemy.ujimonSelected.ujimonAttacks[attackIndex].currentAmount--
        } while (attackSelected == null || attackSelected.currentAmount <= 0)

        if(probability < 0.1){
            attackSelected = null
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
    fun askEffectiveness(gameLevel : Int, attack: Attack): String {
        var message = ""
        when(gameLevel){
            1->{
                message = enemyTrainer1.ujimonSelected.attackEffectiveness(attack)
            }
            2->{
                message = enemyTrainer2.ujimonSelected.attackEffectiveness(attack)
            }
            3->{
                message = enemyTrainer3.ujimonSelected.attackEffectiveness(attack)
            }
        }
        return  message
    }

    fun askEnemyEffectiveness(attack: Attack): String {
        return playerTrainer.ujimonSelected.attackEffectiveness(attack)
    }

    fun playerSelectUjimon(ujimonSelected: Ujimon, ujimonTeam: Array<Ujimon>) {
        for(i in ujimonTeam.indices){
            if(ujimonSelected.name == ujimonTeam[i].name){
                ujimonTeam[i] = Ujimon(0f, "", null, null, false, Type.NORMAL, Pair(0, Type.NORMAL))
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

    fun countPlayerScore(): Float {
        var playerScore = 0f
        for(ujimon in playerTrainer.ujimonTeam)
            playerScore += ujimon.healthPoints
        
        return playerScore
    }

    fun establishTrainersUjimon() {
        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[0]
        enemyTrainer1.ujimonSelected = enemyTrainer1.ujimonTeam[0]
        enemyTrainer2.ujimonSelected = enemyTrainer2.ujimonTeam[0]
        enemyTrainer3.ujimonSelected = enemyTrainer3.ujimonTeam[0]
        for(ujimon in playerTrainer.ujimonTeam){
            ujimon.establishAttacks()
        }
        for(ujimon in enemyTrainer1.ujimonTeam){
            ujimon.establishAttacks()
        }
        for(ujimon in enemyTrainer2.ujimonTeam){
            ujimon.establishAttacks()
        }
        for(ujimon in enemyTrainer3.ujimonTeam){
            ujimon.establishAttacks()
        }
    }

    fun chooseUjimonAttack(i: Int): Attack? {
        val probability = Random.nextFloat()
        playerTrainer.ujimonSelected.ujimonAttacks[i].currentAmount-=1
        return if(probability > 0.1){
            playerTrainer.ujimonSelected.ujimonAttacks[i]
        }
        else
            null
    }

    fun playerAttackToEnemy(gameLevel: Int, chosenAttack : Attack) {
        when (gameLevel) {
            1 -> {
                enemyTrainer1.ujimonSelected.recieveAttack(chosenAttack)
                if (enemyTrainer1.ujimonSelected.dead) {
                    if (checkEnemyUjimonTeamDead(enemyTrainer1)){
                        changeModelState(UjimonState.HEALTH_HEALING)
                    }
                    else {
                        for (ujimon in enemyTrainer1.ujimonTeam) {
                            if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                enemyTrainer1.ujimonSelected = ujimon
                                break
                            }
                        }
                    }
                }
            }
            2 -> {
                enemyTrainer2.ujimonSelected.recieveAttack(chosenAttack)
                if (enemyTrainer2.ujimonSelected.dead) {
                    if (checkEnemyUjimonTeamDead(enemyTrainer2)){
                        changeModelState(UjimonState.HEALTH_HEALING)
                    }
                    else {
                        for (ujimon in enemyTrainer2.ujimonTeam) {
                            if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                enemyTrainer2.ujimonSelected = ujimon
                                break
                            }
                        }
                    }
                }
            }
            3 -> {
                enemyTrainer3.ujimonSelected.recieveAttack(chosenAttack)
                if (enemyTrainer3.ujimonSelected.dead) {
                    if (!checkEnemyUjimonTeamDead(enemyTrainer3)){
                        for (ujimon in enemyTrainer3.ujimonTeam) {
                            if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                enemyTrainer3.ujimonSelected = ujimon
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    fun healUjimonSelected(index: Int) {
        playerTrainer.ujimonTeam[index].healHealthPoints()
        playerTrainer.ujimonTeam[index].recoverEnergy()
    }

    fun chooseEnemyUjimonAttack(gameLevel: Int): Attack? {
        var chosenAttack : Attack? = null

        when(gameLevel){
            1 -> {
                chosenAttack = computerAttack(enemyTrainer1, playerTrainer)
            }
            2 -> {
                chosenAttack = computerAttack(enemyTrainer2, playerTrainer)
            }
            3 -> {
                chosenAttack = computerAttack(enemyTrainer3, playerTrainer)
            }
        }
        if(chosenAttack != null)
            playerTrainer.ujimonSelected.recieveAttack(chosenAttack)

        return chosenAttack
    }

    fun chooseEnemyActiveUjimon(gameLevel: Int): Ujimon? {
        return when(gameLevel){
            1 -> {
                enemyTrainer1.ujimonSelected
            }
            2 -> {
                enemyTrainer2.ujimonSelected
            }
            else -> {
                enemyTrainer3.ujimonSelected
            }
        }

    }



    fun checkConditions(gameLevel: Int): Type {

        when (gameLevel) {
            -1 -> {
                if(playerTrainer.ujimonSelected.condition.second != Type.NORMAL && playerTrainer.ujimonSelected.condition.first > 0) {
                    playerTrainer.ujimonSelected.receiveConditionDamage()
                }
                return playerTrainer.ujimonSelected.condition.second
            }
            1 -> {
                if(enemyTrainer1.ujimonSelected.condition.second != Type.NORMAL && enemyTrainer1.ujimonSelected.condition.first > 0) {
                    enemyTrainer1.ujimonSelected.receiveConditionDamage()
                    if (enemyTrainer1.ujimonSelected.dead) {
                        if (checkEnemyUjimonTeamDead(enemyTrainer1)) {
                            changeModelState(UjimonState.HEALTH_HEALING)
                        } else {
                            for (ujimon in enemyTrainer1.ujimonTeam) {
                                if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                    enemyTrainer1.ujimonSelected = ujimon
                                    break
                                }
                            }
                        }
                    }
                }

                return enemyTrainer1.ujimonSelected.condition.second
            }

            2 -> {
                if(enemyTrainer2.ujimonSelected.condition.second != Type.NORMAL && enemyTrainer2.ujimonSelected.condition.first > 0) {
                    enemyTrainer2.ujimonSelected.receiveConditionDamage()
                    if (enemyTrainer2.ujimonSelected.dead) {
                        if (checkEnemyUjimonTeamDead(enemyTrainer2)) {
                            changeModelState(UjimonState.HEALTH_HEALING)
                        } else {
                            for (ujimon in enemyTrainer2.ujimonTeam) {
                                if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                    enemyTrainer2.ujimonSelected = ujimon
                                    break
                                }
                            }
                        }
                    }
                }
                return enemyTrainer2.ujimonSelected.condition.second
            }

            else -> {
                if(enemyTrainer3.ujimonSelected.condition.second != Type.NORMAL && enemyTrainer3.ujimonSelected.condition.first > 0) {
                    enemyTrainer3.ujimonSelected.receiveConditionDamage()
                    if (enemyTrainer3.ujimonSelected.dead) {
                        if (checkEnemyUjimonTeamDead(enemyTrainer3)) {
                            changeModelState(UjimonState.HEALTH_HEALING)
                        } else {
                            for (ujimon in enemyTrainer3.ujimonTeam) {
                                if (!ujimon.dead && ujimon.type != Type.NORMAL) {
                                    enemyTrainer3.ujimonSelected = ujimon
                                    break
                                }
                            }
                        }
                    }
                }
                return enemyTrainer3.ujimonSelected.condition.second
            }
        }
    }

}
