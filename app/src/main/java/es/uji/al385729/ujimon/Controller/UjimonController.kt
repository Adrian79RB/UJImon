package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.core.content.ContextCompat
import es.uji.al385729.ujimon.Assets
import es.uji.al385729.ujimon.MainActivity
import es.uji.al385729.ujimon.Model.*
import es.uji.al385729.ujimon.R
import es.uji.vj1229.framework.AnimatedBitmap
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
        private val TEXT_COLOR = Color.rgb(0xF8,0xFB,0xF9)
        private val MENU_TEXT_COLOR = Color.rgb(0x8F, 0x21, 0xDA)
        private val GROUND_TEXT_COLOR = Color.rgb(0x42, 0x31, 0x0A)
        private val FIRE_TEXT_COLOR = Color.rgb(0xF2, 0x38, 0x13)
        private val WATER_TEXT_COLOR = Color.rgb(0x24, 0xA1, 0xED)
        private val DARK_TEXT_COLOR = Color.rgb(0x16, 0x16, 0x17)
        private val PLANT_TEXT_COLOR = Color.rgb(0x0F, 0xED, 0x34)
        private val NORMAL_TEXT_COLOR = Color.rgb(0x8C, 0x8C, 0x8C)
        private val HEALTH_TEXT_COLOR = Color.rgb(0x6F, 0xCE, 0x7F)
    }

    private lateinit var soundPool: SoundPool
    private var introMusicId = 0
    private var battleMusicId = 0
    private var healingMusicId = 0
    private var victoryId = 0
    private var defeatId = 0
    private var attackSoundId = 0
    private var powerSoundId = 0

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
    private val healthButtonRow = 6
    private val battleButtonRowInt = 11
    private val battleButtonColInt = 17
    private val battlePlayerUjimonRow = 7
    private val battlePlayerUjimonCol = 3
    private val battleEnemyUjimonRow =2
    private val battleEnemuUjimonCol = 15
    private val playerTeamStartColumnInt = 14
    private var ujimonChoosen = 0
    private var gameLevel = 1
    private val attackButtonCol = 11
    private val attackButtonRow = 11
    private val changeButtonCol = 16
    private val changeButtonRow = 11
    private val battleButtonRow = 11 * cellSide + xOffset
    private val battleButtonColumn = 18 * cellSide + yOffset
    private val promptRow = 3
    private val promptColumn = 2
    private var waitingTime = 0f
    private val waitingTimer = 2f
    private val prompBoxPos = 1
    private val attackBoxCol = 11
    private val attackBoxRow = 8
    private var ujimonHealed = false
    private var playerScore = 0f
    private var startingPlayerUjiballRow = 8
    private var startingEnemyUjiballRow = 1
    var efectivenessMessage = ""

    val playerTrainer = Trainer()
    val computerEnemy1 = Trainer()
    val computerEnemy2 = Trainer()
    val computerEnemy3 = Trainer()
    var chosenAttack : Attack? = null
    var chosenUjimon : Ujimon? = null


    private var animation: AnimatedBitmap? = null
    private var xAnimation : Int = 0
    private var yAnimation : Int = 0



    init {
        Assets.loadDrawableAssets(contex)
        Assets.createResizedAssets(contex, cellSideInt)
        prepareSoundPool(contex)
        graphics = Graphics(width, height)
        model = UjimonModel(playerTrainer, computerEnemy1, computerEnemy2, computerEnemy3, this)
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        animation?.apply {
            if (isEnded)
                animation = null
            else
                update(deltaTime)
        }

        if(touchEvents != null){
            for (event in touchEvents){
                val correctedEventX = ((event.x - xOffset)/cellSide).toInt()
                val correctedEventY = ((event.y - yOffset)/cellSide).toInt()

                when(event.type){
                    TouchHandler.TouchType.TOUCH_UP -> {


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
                                else if(correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.BUTTONS_WIDTH
                                        && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonColumn + Assets.UJIMON_SIZE_BUTTON){
                                    model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                }
                            }
                            if(ujimonSelected){
                                model.changeModelState(UjimonModel.UjimonState.WAITING)
                            }
                        }

                        if(model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
                            chosenAttack = null
                            for (i in 0 until 4){
                                if(correctedEventX >= attackButtonCol && correctedEventY == attackButtonRow+i-2){
                                    if(playerTrainer.ujimonSelected!!.ujimonAttacks[i].currentAmount > 0){
                                        chosenAttack = playerTrainer.ujimonSelected!!.ujimonAttacks[i]
                                        playerTrainer.ujimonSelected!!.ujimonAttacks[i].currentAmount-=1
                                    }
                                }
                            }

                            if(chosenAttack != null) {
                                xAnimation = battleEnemuUjimonCol
                                yAnimation = battleEnemyUjimonRow
                                when(chosenAttack!!.type){
                                    Type.FIRE->{
                                        animation = Assets.fireAttack
                                        drawAnimation()
                                    }
                                    Type.WATER->{
                                        animation = Assets.waterAttack
                                        drawAnimation()
                                    }
                                    Type.NORMAL->{
                                        animation = Assets.darknessAttack
                                        drawAnimation()
                                    }
                                    Type.PLANT->{
                                        animation = Assets.plantAttack
                                        drawAnimation()
                                    }
                                    Type.GROUND->{
                                        animation = Assets.groundAttack
                                        drawAnimation()
                                    }
                                    Type.DARKNESS->{
                                        animation = Assets.darknessAttack
                                        drawAnimation()
                                    }
                                }

                                model.playerAttackToEnemy(gameLevel, chosenAttack!!)
                                efectivenessMessage = model.askEffectiveness(gameLevel, chosenAttack!!)

                                if(model.state != UjimonModel.UjimonState.HEALTH_HEALING)
                                    model.changeModelState(UjimonModel.UjimonState.WAITING)
                            }

                        }

                        if(model.state == UjimonModel.UjimonState.PLAYER_TURN){
                            if(correctedEventX >= changeButtonCol && correctedEventX< changeButtonCol + Assets.BUTTONS_WIDTH)
                            if(!soundPool.equals(battleMusicId))
                                playBattleMusic()

                            if(correctedEventX >= changeButtonCol && correctedEventX< changeButtonCol + Assets.UJIMON_SIZE_BUTTON
                                && correctedEventY >= changeButtonRow && correctedEventY < changeButtonRow + Assets.UJIMON_SIZE_BUTTON){
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                            }
                            if(correctedEventX >= attackButtonCol && correctedEventX< attackButtonCol + Assets.BUTTONS_WIDTH
                                    && correctedEventY >= attackButtonRow && correctedEventY < attackButtonRow + Assets.UJIMON_SIZE_BUTTON){
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_ATTACK)
                            }
                        }

                        if(model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
                            if(!soundPool.equals(healingMusicId))
                                playHealingMusic()


                            for (i in 0 until 5) {
                                if (correctedEventX >= buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + (Assets.UJIMON_SIZE_BUTTON + 2) * i
                                        && correctedEventY >= healthButtonRow && correctedEventY < healthButtonRow + Assets.UJIMON_SIZE_BUTTON) {
                                    if (ujimonHealed) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonHealed = false
                                        gameLevel++
                                        if(gameLevel > 3){
                                            model.changeModelState(UjimonModel.UjimonState.END)
                                            playerScore = model.countPlayerScore()
                                        }
                                        else
                                            model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                    }
                                    else {
                                        model.healUjimonSelected(i)
                                        ujimonHealed = true
                                    }
                                }
                            }
                        }

                        if(model.state == UjimonModel.UjimonState.END) {
                            if(correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.UJIMON_SIZE_BUTTON
                                    && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                val intent = Intent(contex, MainActivity::class.java)
                                ContextCompat.startActivity(contex, intent, null)
                            }
                        }

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
                            if(correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.BUTTONS_WIDTH
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
                    }
                }
            }
        }

        if(model.state == UjimonModel.UjimonState.COMPUTER_TURN){
            chosenAttack = model.chooseEnemyUjimonAttack(gameLevel)
            chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)


            if(chosenAttack != null) {
                efectivenessMessage = model.askEnemyEffectiveness(chosenAttack!!)
                xAnimation = battlePlayerUjimonCol
                yAnimation = battlePlayerUjimonRow
                when(chosenAttack!!.type){
                    Type.FIRE->{
                        animation = Assets.fireAttack
                        drawAnimation()
                    }
                    Type.WATER->{
                        animation = Assets.waterAttack
                        drawAnimation()
                    }
                    Type.NORMAL->{
                        animation = Assets.darknessAttack
                        drawAnimation()
                    }
                    Type.PLANT->{
                        animation = Assets.plantAttack
                        drawAnimation()
                    }
                    Type.GROUND->{
                        animation = Assets.groundAttack
                        drawAnimation()
                    }
                    Type.DARKNESS->{
                        animation = Assets.darknessAttack
                        drawAnimation()
                    }
                }
            }

            model.changeModelState(UjimonModel.UjimonState.WAITING)
        }

        if(model.state == UjimonModel.UjimonState.WAITING){
            waitingTime += deltaTime
            if(waitingTime >= waitingTimer){
                waitingTime = 0f

                if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK || model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON){
                    model.changeModelState(UjimonModel.UjimonState.COMPUTER_TURN)
                }
                else if(model.lastState == UjimonModel.UjimonState.COMPUTER_TURN) {
                    if(playerTrainer.ujimonSelected!!.dead){
                        if(model.checkPlayerUjimonTeamDead()){
                            model.changeModelState(UjimonModel.UjimonState.END)
                            playerScore = model.countPlayerScore()
                        }
                        else
                            model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                    }
                    else{
                        model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                    }
                }
            }
        }
    }


    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        graphics.setTextColor(MENU_TEXT_COLOR)
        graphics.setTextSize(20)

        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION || model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
            graphics.drawText(4 * cellSide + xOffset, cellSide + yOffset, "Choose your Ujimon Team")
            drawUjimonButtons()

            if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                graphics.drawBitmap( Assets.battleButton, battleButtonColInt * cellSide + xOffset, battleButtonRowInt * cellSide + yOffset)
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN || model.state == UjimonModel.UjimonState.COMPUTER_TURN || model.state == UjimonModel.UjimonState.WAITING || model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
            graphics.setTextColor(TEXT_COLOR)
            graphics.drawBitmap(Assets.battlefield, 0f,0f)
            graphics.drawBitmap(Assets.promptBox, prompBoxPos * cellSide + xOffset,prompBoxPos * cellSide + yOffset )

            drawUjimonsInBattle()

            when(gameLevel){
                1->{
                    drawUjiballs(playerTrainer, computerEnemy1)
                }
                2->{
                    drawUjiballs(playerTrainer, computerEnemy2)
                }
                3->{
                    drawUjiballs(playerTrainer, computerEnemy3)
                }
            }

            if(model.state == UjimonModel.UjimonState.PLAYER_TURN) {
                graphics.drawBitmap(Assets.attackButton, attackButtonCol * cellSide + xOffset, attackButtonRow * cellSide + yOffset)
                graphics.drawBitmap(Assets.changeButton, changeButtonCol * cellSide + xOffset, changeButtonRow * cellSide + yOffset)
            }
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN){
            graphics.setTextColor(TEXT_COLOR)
            graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, "Make your move")
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON){
            graphics.setTextColor(MENU_TEXT_COLOR)
            graphics.drawText(4 * cellSide + xOffset, cellSide + yOffset, "Choose your Ujimon")
            drawPlayerUjimonButtons()
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
            drawAttacksButtons(playerTrainer.ujimonSelected!!)
        }

        if(model.state == UjimonModel.UjimonState.WAITING){
            if(animation!=null){
                graphics.drawBitmap(animation?.currentFrame, xAnimation * cellSide + xOffset, yAnimation * cellSide + yOffset)
            }
            if(model.lastState == UjimonModel.UjimonState.COMPUTER_TURN){
                if(chosenAttack != null) {
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " have used " + chosenAttack!!.Nombre)
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset, efectivenessMessage)
                }
                else
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  chosenUjimon!!.name + " have failed the attack.")
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK) {
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, "You have used : " + chosenAttack!!.Nombre)
                graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,  efectivenessMessage)
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have changed your Ujimon to " + playerTrainer.ujimonSelected!!.name)
        }

        if(model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
            graphics.setTextColor(MENU_TEXT_COLOR)
            if(ujimonHealed)
                graphics.drawText(4 * cellSide + xOffset, 4 * cellSide + yOffset, "Choose an Ujimon to go to the next battle")
            else
                graphics.drawText(4 * cellSide + xOffset, 4 * cellSide + yOffset, "Choose an Ujimon to heal it")
            drawPlayerHealingButtons()
        }

        if(model.state == UjimonModel.UjimonState.END) {
            drawEndingScreen()
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

        if(!playerTrainer.ujimonSelected!!.dead)
            graphics.drawBitmap(Assets.backButton, battleButtonColumn, battleButtonRow)
    }

    private fun drawPlayerHealingButtons() {
        var i = 0
        graphics.setTextColor(HEALTH_TEXT_COLOR)
        graphics.setTextSize(14)

        for(ujimon in playerTrainer.ujimonTeam) {

            graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
            graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
            graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

            if(ujimon.dead)
                graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
            i++
        }
        graphics.setTextSize(20)
    }

    private fun drawEndingScreen() {
        if(playerScore <= 0){
            graphics.drawText(7 * cellSide + xOffset, 6 * cellSide + yOffset, "HAS PERDIDO EL COMBATE")
        }
        else{
            graphics.drawText(7 * cellSide + xOffset, 6 * cellSide + yOffset, "HAS GANADO EL COMBATE")
        }

        var i = 0
        graphics.setTextColor(HEALTH_TEXT_COLOR)
        graphics.setTextSize(14)

        for(ujimon in playerTrainer.ujimonTeam) {

            graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
            graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, buttonStartRow)
            graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, (buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON + 1) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

            if(ujimon.dead)
                graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, buttonStartRow)

            graphics.drawText(9 * cellSide + xOffset, (7 + i) * cellSide + yOffset,"${ujimon.name}: ${ujimon.healthPoints}")
            i++
        }

        graphics.setTextSize(20)
        graphics.drawText(9 * cellSide + xOffset, 13 * cellSide + yOffset, "Total Score: $playerScore")
        graphics.drawBitmap( Assets.replayButton, battleButtonColumn, battleButtonRow)
    }


    private  fun drawAttacksButtons(ujimon : Ujimon){
        var i = 0
        graphics.drawBitmap(Assets.attackBox, attackBoxCol * cellSide + xOffset, attackBoxRow * cellSide + yOffset)
        for(attack in ujimon.ujimonAttacks){
            when(attack.type){
                Type.FIRE->{
                    graphics.setTextColor(FIRE_TEXT_COLOR)
                }
                Type.WATER->{
                    graphics.setTextColor(WATER_TEXT_COLOR)
                }
                Type.DARKNESS->{
                    graphics.setTextColor(DARK_TEXT_COLOR)
                }
                Type.GROUND->{
                    graphics.setTextColor(GROUND_TEXT_COLOR)
                }
                Type.PLANT->{
                    graphics.setTextColor(PLANT_TEXT_COLOR)
                }
                Type.NORMAL->{
                    graphics.setTextColor(NORMAL_TEXT_COLOR)
                }
            }
            graphics.drawText((attackButtonCol+1) * cellSide + xOffset, (attackButtonRow+i-1) * cellSide + yOffset , attack.Nombre +  " " + attack.currentAmount + "/" + attack.totalAmount)
            i++
        }
        graphics.setTextColor(TEXT_COLOR)
    }

    private fun drawUjimonsInBattle() {
        graphics.drawBitmap(playerTrainer.ujimonSelected!!.imageAsset, battlePlayerUjimonCol * cellSide + xOffset, battlePlayerUjimonRow * cellSide + yOffset)
        graphics.drawText(battlePlayerUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,(battlePlayerUjimonRow+ Assets.UJIMON_SIZE_COMBAT + 1) * cellSide + yOffset,playerTrainer.ujimonSelected!!.name + " HP: " + playerTrainer.ujimonSelected!!.healthPoints.toString() )
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
    }

    private fun drawAnimation(){
        animation?.let {  graphics.drawBitmap(it.currentFrame, xAnimation * cellSide + xOffset, yAnimation * cellSide + yOffset) }
        animation?.restart()
    }

    private fun drawUjiballs(player : Trainer, enemy : Trainer){
        var i = 0
        var j = 0
        for(ujimon in player.ujimonTeam){
            if(!ujimon.dead){
                graphics.drawBitmap(Assets.ujiball, 1 * cellSide + xOffset, (startingPlayerUjiballRow +i ) * cellSide + yOffset)
            }
            else{
                graphics.drawBitmap(Assets.emptyujiball, 1 * cellSide + xOffset, (startingPlayerUjiballRow +i ) * cellSide + yOffset)
            }
            i++
        }
        for(ujimon in enemy.ujimonTeam){
            if(!ujimon.dead){
                graphics.drawBitmap(Assets.ujiball, 21 * cellSide + xOffset, (startingEnemyUjiballRow +j ) * cellSide + yOffset)
            }
            else{
                graphics.drawBitmap(Assets.emptyujiball, 21 * cellSide + xOffset, (startingEnemyUjiballRow +j ) * cellSide + yOffset)
            }
            j++
        }
    }

    private fun prepareSoundPool(contex: Context) {
        val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        soundPool = SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(attributes).build()

        introMusicId = soundPool.load(contex, R.raw.intro_song, 0)
        battleMusicId = soundPool.load(contex, R.raw.fight_song, 0)
        healingMusicId = soundPool.load(contex, R.raw.healing_song, 0)
        victoryId = soundPool.load(contex, R.raw.victory, 0)
        defeatId = soundPool.load(contex, R.raw.game_over, 0)
        attackSoundId = soundPool.load(contex, R.raw.attack_kick, 0)
        powerSoundId = soundPool.load(contex, R.raw.attack_power, 0)
    }

    override fun playIntroMusic() {
        soundPool.play(introMusicId, 1f, 1f, 0, 1, 1f)
    }

    override fun playBattleMusic() {
        soundPool.play(battleMusicId, 1f, 1f, 0, 1, 1f)
    }

    override fun playHealingMusic() {
        soundPool.play(healingMusicId, 1f, 1f, 0, 1, 1f)
    }

    override fun playVictory() {
        soundPool.play(victoryId, 1f, 1f, 0, 0, 1f)
    }

    override fun playDefeated() {
        soundPool.play(defeatId, 1f, 1f, 0, 0, 1f)
    }

    override fun playNormalAttackSound() {
        soundPool.play(attackSoundId, 1f, 1f, 0, 0, 1f)
    }

    override fun playPowerAttackSound() {
        soundPool.play(powerSoundId, 1f, 1f, 0, 0, 1f)
    }

}