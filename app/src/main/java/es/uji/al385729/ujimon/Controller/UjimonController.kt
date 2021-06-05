package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.SoundPool
import es.uji.al385729.ujimon.Assets
import es.uji.al385729.ujimon.Model.*
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import java.lang.Float.min

class UjimonController(val width : Int,
                        val height : Int, val contex : Context) : IGameController, UjimonModel.SoundPlayer {

    companion object {
        private val TOTAL_CELL_WIDTH = 24
        private val TOTAL_CELL_HEIGHT = 14
        private val BACKGROUND_COLOR = Color.rgb(0xff, 0xff, 0xff)
        private val SELECTION_MENU_COLOR = Color.rgb(0xC5, 0xC0, 0x24)
        private val BUTTON_SELECTED_COLOR = Color.rgb(0xDF, 0x32, 0x32)
        private val TEXT_COLOR = Color.rgb(0xFF, 0x6A, 0x98)
    }

    private lateinit var soundPool: SoundPool
    private var introMusicId = 0
    private var battleMusicId = 0
    private var healingMusicId = 0
    private var victoryId = 0
    private var defeatId = 0
    private var attackSoundId = 0

    private val graphics : Graphics
    private val model : UjimonModel
    private val cellSide : Float = min(width.toFloat() / TOTAL_CELL_WIDTH, height.toFloat()/ TOTAL_CELL_HEIGHT)
    private val cellSideInt = cellSide.toInt()
    private val xOffset : Float = (width - TOTAL_CELL_WIDTH * cellSide) / 2.0f
    private val yOffset : Float = (height - TOTAL_CELL_HEIGHT * cellSide) / 2.0f
    private val buttonStartColumn = 2 * cellSide + xOffset
    private val buttonStartColumnInt = 2
    private val buttonStartRow = 2 * cellSide + yOffset
    private val buttonStartRowInt = 2
    private val buttonStartSecondRow = 7 * cellSide + yOffset
    private val buttonStartSecondRowInt = 7
    private val battleButtonRowInt = 11
    private val battleButtonColInt = 17
    private val battlePlayerUjimonRow = 6
    private val battlePlayerUjimonCol = 1
    private val battleEnemyUjimonRow = 1
    private val battleEnemuUjimonCol = 10
    private val playerTeamStartColumnInt = 14
    private var ujimonChoosen = 0
    private var gameLevel = 1
    private val attackButtonCol = 8
    private val attackButtonRow = 11
    private val changeButtonCol = 11
    private val changeButtonRow = 11
    private val battleButtonRow = 11 * cellSide + xOffset
    private val battleButtonColumn = 18 * cellSide + yOffset
    private val promptRow = 10
    private val promptColumn = 11
    private var waitingTime = 0f
    private val waitingTimer = 2f

    val playerTrainer = Trainer()
    val computerEnemy1 = Trainer()
    val computerEnemy2 = Trainer()
    val computerEnemy3 = Trainer()
    var chosenAttack : Attack? = null
    var chosenUjimon : Ujimon? = null

    init {
        Assets.createResizedAssets(contex, cellSideInt)
        graphics = Graphics(width, height)
        model = UjimonModel(playerTrainer, computerEnemy1, computerEnemy2, computerEnemy3, this)
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if(touchEvents != null){
            for (event in touchEvents){
                val correctedEventX = ((event.x - xOffset)/cellSide).toInt()
                val correctedEventY = ((event.y - yOffset)/cellSide).toInt()

                when(event.type){
                    TouchHandler.TouchType.TOUCH_UP -> {
                        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION || model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
                            for(i in 0 until 5){
                                if(correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                    && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON){
                                    val ujimonSelected = model.selectUjimon(i, 1)
                                    model.playerSelectUjimon(ujimonSelected, playerTrainer.ujimonTeam)
                                }
                                if(correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                        && correctedEventY >= buttonStartSecondRowInt && correctedEventY < buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    val ujimonSelected = model.selectUjimon(i, 2)
                                    model.playerSelectUjimon(ujimonSelected, playerTrainer.ujimonTeam)
                                }
                            }

                            ujimonChoosen = 0
                            for(ujimon in playerTrainer.ujimonTeam){
                                if(ujimon.name != "")
                                    ujimonChoosen++
                            }
                            if(ujimonChoosen == playerTrainer.ujimonTeam.size)
                                model.changeModelState(UjimonModel.UjimonState.UJIMON_SELECTED)
                            else if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                                model.changeModelState(UjimonModel.UjimonState.UJIMON_SELECTION)
                        }

                        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
                            if(correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.UJIMON_SIZE_BUTTON
                                && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                model.createEnemyTrainer1Team()
                                model.createEnemyTrainer2Team()
                                model.createEnemyTrainer3Team()
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[0]
                                computerEnemy1.ujimonSelected = computerEnemy1.ujimonTeam[0]
                                computerEnemy2.ujimonSelected = computerEnemy2.ujimonTeam[0]
                                computerEnemy3.ujimonSelected = computerEnemy3.ujimonTeam[0]
                                for(ujimon in playerTrainer.ujimonTeam){
                                    ujimon.establishAttacks()
                                }
                                for(ujimon in computerEnemy1.ujimonTeam){
                                    ujimon.establishAttacks()
                                }
                                for(ujimon in computerEnemy2.ujimonTeam){
                                    ujimon.establishAttacks()
                                }
                                for(ujimon in computerEnemy3.ujimonTeam){
                                    ujimon.establishAttacks()
                                }

                            }
                        }

                        if(model.state == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON){
                            var ujimonSelected = false
                            for(i in 0 until 5) {
                                if (correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                    && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    if(playerTrainer.ujimonTeam[i] != playerTrainer.ujimonSelected && playerTrainer.ujimonTeam[i].healthPoints > 0) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonSelected = true
                                    }
                                    else{
                                        graphics.drawText(4 * cellSide + xOffset, 12 * cellSide + yOffset, "That ujimon has no HP, he can't fight")
                                    }
                                }
                                else if(correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.UJIMON_SIZE_BUTTON
                                        && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonColumn + Assets.UJIMON_SIZE_BUTTON){
                                    model.changeModelState(UjimonModel.UjimonState.WAITING)
                                }
                            }
                            if(ujimonSelected){
                                model.changeModelState(UjimonModel.UjimonState.WAITING)
                            }
                        }

                        if(model.state == UjimonModel.UjimonState.PLAYER_TURN){
                            if(correctedEventX >= changeButtonCol && correctedEventX< changeButtonCol + Assets.UJIMON_SIZE_BUTTON
                                && correctedEventY >= changeButtonRow && correctedEventY < changeButtonRow + Assets.UJIMON_SIZE_BUTTON){
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                            }
                        }
                    }
                }
            }
        }

        if(model.state == UjimonModel.UjimonState.COMPUTER_TURN){
            when(gameLevel){
                1 -> {
                    chosenAttack = model.computerAttack(computerEnemy1, playerTrainer)
                    chosenUjimon = computerEnemy1.ujimonSelected
                }
                2 -> {
                    chosenAttack = model.computerAttack(computerEnemy2, playerTrainer)
                    chosenUjimon = computerEnemy2.ujimonSelected
                }
                3 -> {
                    chosenAttack = model.computerAttack(computerEnemy3, playerTrainer)
                    chosenUjimon = computerEnemy3.ujimonSelected
                }
            }

            if(chosenAttack != null) {
                playerTrainer.ujimonSelected!!.recieveAttack(chosenAttack!!)
            }

            model.changeModelState(UjimonModel.UjimonState.WAITING)
        }

        if(model.state == UjimonModel.UjimonState.WAITING){
            waitingTime += deltaTime
            if(waitingTime >= waitingTimer){
                waitingTime = 0f
                if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK || model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                    model.changeModelState(UjimonModel.UjimonState.COMPUTER_TURN)
                else if(model.lastState == UjimonModel.UjimonState.COMPUTER_TURN)
                    model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
            }
        }
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        graphics.setTextColor(TEXT_COLOR)

        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION || model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
            graphics.drawText(4 * cellSide + xOffset, cellSide + yOffset, "Choose your Ujimon Team")
            drawUjimonButtons()

            if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                graphics.drawBitmap( Assets.battleButton, battleButtonColInt * cellSide + xOffset, battleButtonRowInt * cellSide + yOffset)
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN || model.state == UjimonModel.UjimonState.COMPUTER_TURN || model.state == UjimonModel.UjimonState.WAITING){
            graphics.drawBitmap(playerTrainer.ujimonSelected!!.imageAsset, battlePlayerUjimonCol * cellSide + xOffset, battlePlayerUjimonRow * cellSide + yOffset)
            graphics.drawText(battlePlayerUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,battlePlayerUjimonRow * cellSide + yOffset,playerTrainer.ujimonSelected!!.name + " HP: " + playerTrainer.ujimonSelected!!.healthPoints.toString() )

            when(gameLevel){
                1->{
                    graphics.drawBitmap(computerEnemy1.ujimonSelected!!.imageAsset, battleEnemuUjimonCol * cellSide + xOffset, battleEnemyUjimonRow * cellSide + yOffset)
                    graphics.drawText(battleEnemuUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,battleEnemyUjimonRow * cellSide + yOffset,computerEnemy1.ujimonSelected!!.name + " HP: " + computerEnemy1.ujimonSelected!!.healthPoints.toString())

                }
                2->{
                    graphics.drawBitmap(computerEnemy2.ujimonSelected!!.imageAsset, battleEnemuUjimonCol * cellSide + xOffset, battleEnemyUjimonRow * cellSide + yOffset)
                    graphics.drawText(battleEnemuUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,battleEnemyUjimonRow * cellSide + yOffset,computerEnemy2.ujimonSelected!!.name + " HP: " + computerEnemy2.ujimonSelected!!.healthPoints.toString())

                }
                3->{
                    graphics.drawBitmap(computerEnemy3.ujimonSelected!!.imageAsset, battleEnemuUjimonCol * cellSide + xOffset, battleEnemyUjimonRow * cellSide + yOffset)
                    graphics.drawText(battleEnemuUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,battleEnemyUjimonRow * cellSide + yOffset,computerEnemy3.ujimonSelected!!.name + " HP: " + computerEnemy3.ujimonSelected!!.healthPoints.toString())

                }
            }

            graphics.drawBitmap(Assets.attackButton, attackButtonCol * cellSide + xOffset, attackButtonRow * cellSide + yOffset)
            graphics.drawBitmap(Assets.changeButton, changeButtonCol * cellSide + xOffset, changeButtonRow * cellSide + yOffset)

        }

        if(model.state == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON){
            graphics.drawText(4 * cellSide + xOffset, cellSide + yOffset, "Choose your Ujimon")
            drawPlayerUjimonButtons()
        }

        if(model.state == UjimonModel.UjimonState.WAITING){
            if(model.lastState == UjimonModel.UjimonState.COMPUTER_TURN){
                if(chosenAttack != null)
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  chosenUjimon!!.name + " have used " + chosenAttack!!.Nombre)
                else
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  chosenUjimon!!.name + " have failed the attack.")
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have chosen an attack (HAY QUE COMPLETAR ESTO)")
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have changed your Ujimon to " + playerTrainer.ujimonSelected!!.name)
        }


        return graphics.frameBuffer
    }

    private fun drawUjimonButtons() {
        var i = 0
        var k = 0
        for(ujimon in model.ujimonInstances.ujimonArray){
            if(i < 5) {
                if(model.ujimonAlreadySelected(ujimon, playerTrainer.ujimonTeam)) {
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, BUTTON_SELECTED_COLOR)
                    graphics.drawBitmap(ujimon.buttonAsset ,(playerTeamStartColumnInt + Assets.UJIMON_SIZE_BUTTON * k)* cellSide + xOffset, buttonStartRow)
                    k++
                }
                else
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)

                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)
            }
            else {
                if (model.ujimonAlreadySelected(ujimon, playerTrainer.ujimonTeam)) {
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * (i - 5)) * cellSide + xOffset, buttonStartSecondRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, BUTTON_SELECTED_COLOR)
                    graphics.drawBitmap(ujimon.buttonAsset, (playerTeamStartColumnInt + Assets.UJIMON_SIZE_BUTTON * k) * cellSide + xOffset, buttonStartRow)
                    k++
                } else
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * (i - 5)) * cellSide + xOffset, buttonStartSecondRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)

                graphics.drawBitmap(ujimon.buttonAsset, (buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * (i - 5)) * cellSide + xOffset, buttonStartSecondRow)
            }
            i++
        }
    }

    private  fun drawPlayerUjimonButtons(){
        var i = 0
        for(ujimon in playerTrainer.ujimonTeam) {
            if(ujimon == playerTrainer.ujimonSelected)
                graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, BUTTON_SELECTED_COLOR)
            else
                graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
            graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)

            if(ujimon.dead)
                graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)
            i++
        }

        graphics.drawBitmap(Assets.backButton, battleButtonColumn, battleButtonRow)
    }

    override fun playIntroMusic() {
        TODO("Not yet implemented")
    }

    override fun playBattleMusic() {
        TODO("Not yet implemented")
    }

    override fun playHealingMusic() {
        TODO("Not yet implemented")
    }

    override fun playVictory() {
        TODO("Not yet implemented")
    }

    override fun playDefeated() {
        TODO("Not yet implemented")
    }

    override fun playAttackSound() {
        TODO("Not yet implemented")
    }

}