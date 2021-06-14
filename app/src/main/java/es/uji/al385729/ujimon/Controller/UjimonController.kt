package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import es.uji.al385729.ujimon.Assets
import es.uji.al385729.ujimon.MainActivity
import es.uji.al385729.ujimon.Model.*
import es.uji.al385729.ujimon.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import java.lang.Float.min
import java.util.concurrent.locks.Condition

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
    private var victoryId = 0
    private var defeatId = 0
    private var attackSoundId = 0
    private var powerSoundId = 0
    var musicPlayer : MediaPlayer? = null

    private val graphics : Graphics
    private val model : UjimonModel
    private val cellSide : Float = min(width.toFloat() / TOTAL_CELL_WIDTH, height.toFloat()/ TOTAL_CELL_HEIGHT)
    private val cellSideInt = cellSide.toInt()
    private val xOffset : Float = (width - TOTAL_CELL_WIDTH * cellSide) / 2.0f
    private val yOffset : Float = (height - TOTAL_CELL_HEIGHT * cellSide) / 2.0f
    private val buttonStartColumn = 7 * cellSide + xOffset
    private val buttonStartColumnInt = 6
    private val buttonStartRow = 3 * cellSide + yOffset
    private val buttonStartRowInt = 3
    private val buttonStartSecondRow = 6 * cellSide + yOffset
    private val buttonStartSecondRowInt = 6
    private val healthButtonRow = 2
    private val battleButtonRowInt = 11
    private val battleButtonColInt = 18
    private val battlePlayerUjimonRow = 7
    private val battlePlayerUjimonCol = 3
    private val battleEnemyUjimonRow =2
    private val battleEnemuUjimonCol = 15
    private val playerTeamStartColumnInt = 6
    private var ujimonChoosen = 0
    private var gameLevel = 1
    private val attackButtonCol = 11
    private val attackButtonRow = 11
    private val changeButtonCol = 16
    private val changeButtonRow = 11
    private val battleButtonRow = 11 * cellSide + yOffset
    private val battleButtonColumn = 18 * cellSide + xOffset
    private val promptRow = 3
    private val promptColumn = 2
    private var waitingTime = 0f
    private val waitingTimer = 2f
    private val prompBoxPos = 1
    private val attackBoxCol = 11
    private val attackBoxRow = 8
    private var ujimonHealed = false
    private var justClickedBattle = false
    private var playerScore = 0f
    private var startingPlayerUjiballRow = 8
    private var startingEnemyUjiballRow = 1
    private var enemyConditionCol = 22
    private var enemyConditionRow = 1
    private var playerConditionCol = 2
    private var playerConditionRow = 8
    private var actualCondition = Type.NORMAL
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
        prepareSoundMedia(contex)
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

        if (touchEvents != null) {
            for (event in touchEvents) {
                val correctedEventX = ((event.x - xOffset) / cellSide).toInt()
                val correctedEventY = ((event.y - yOffset) / cellSide).toInt()

                when (event.type) {
                    TouchHandler.TouchType.TOUCH_UP -> {
                        if (model.state == UjimonModel.UjimonState.UJIMON_SELECTION || model.state == UjimonModel.UjimonState.UJIMON_SELECTED) {
                            for (i in 0 until 5) {
                                if (correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                        && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    val ujimonSelected = model.selectUjimon(i, 1)
                                    model.playerSelectUjimon(ujimonSelected, playerTrainer.ujimonTeam)
                                    playIntroMusic()
                                }
                                if (correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                        && correctedEventY >= buttonStartSecondRowInt && correctedEventY < buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    val ujimonSelected = model.selectUjimon(i, 2)
                                    model.playerSelectUjimon(ujimonSelected, playerTrainer.ujimonTeam)
                                }
                            }

                            ujimonChoosen = 0
                            for (ujimon in playerTrainer.ujimonTeam) {
                                if (ujimon.name != "")
                                    ujimonChoosen++
                            }
                            if (ujimonChoosen == playerTrainer.ujimonTeam.size)
                                model.changeModelState(UjimonModel.UjimonState.UJIMON_SELECTED)
                            else if (model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                                model.changeModelState(UjimonModel.UjimonState.UJIMON_SELECTION)
                        }

                        if (model.state == UjimonModel.UjimonState.UJIMON_SELECTED) {
                            if (correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.BUTTONS_WIDTH
                                    && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                model.createEnemyTrainer1Team()
                                model.createEnemyTrainer2Team()
                                model.createEnemyTrainer3Team()
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                model.establishTrainersUjimon()
                                chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)
                                justClickedBattle = true

                                playBattleMusic()
                            }
                        }

                        if (model.state == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON) {
                            var ujimonSelected = false
                            for (i in 0 until 5) {
                                if (correctedEventX >= buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i
                                        && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    if (playerTrainer.ujimonTeam[i] != playerTrainer.ujimonSelected && playerTrainer.ujimonTeam[i].healthPoints > 0) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonSelected = true
                                    } else {
                                        graphics.drawText(4 * cellSide + xOffset, 12 * cellSide + yOffset, "That ujimon has no HP, he can't fight")
                                    }
                                }
                                if (correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.BUTTONS_WIDTH
                                        && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                }
                            }
                            if (ujimonSelected) {
                                model.changeModelState(UjimonModel.UjimonState.WAITING)
                            }
                        }

                        if (model.state == UjimonModel.UjimonState.PLAYER_ATTACK) {
                            chosenAttack = null
                            var attackIsChoosen = false
                            for (i in 0 until 4) {
                                if (correctedEventX >= attackButtonCol && correctedEventY == attackButtonRow + i - 2) {
                                    if (playerTrainer.ujimonSelected.ujimonAttacks[i].currentAmount > 0) {
                                        chosenAttack = model.chooseUjimonAttack(i)
                                        attackIsChoosen = true
                                        if (chosenAttack != null) {
                                            if (chosenAttack!!.type == Type.NORMAL)
                                                playNormalAttackSound()
                                            else
                                                playPowerAttackSound()
                                        }
                                        break
                                    }
                                }
                            }

                            if (chosenAttack != null) {
                                xAnimation = battleEnemuUjimonCol
                                yAnimation = battleEnemyUjimonRow
                                when (chosenAttack!!.type) {
                                    Type.FIRE -> {
                                        animation = Assets.fireAttack
                                        drawAnimation()
                                    }
                                    Type.WATER -> {
                                        animation = Assets.waterAttack
                                        drawAnimation()
                                    }
                                    Type.NORMAL -> {
                                        animation = Assets.darknessAttack
                                        drawAnimation()
                                    }
                                    Type.PLANT -> {
                                        animation = Assets.plantAttack
                                        drawAnimation()
                                    }
                                    Type.GROUND -> {
                                        animation = Assets.groundAttack
                                        drawAnimation()
                                    }
                                    Type.DARKNESS -> {
                                        animation = Assets.darknessAttack
                                        drawAnimation()
                                    }
                                }

                                model.playerAttackToEnemy(gameLevel, chosenAttack!!)
                                efectivenessMessage = model.askEffectiveness(gameLevel, chosenAttack!!)

                                if (gameLevel >= 3 && model.checkEnemyUjimonTeamDead(computerEnemy3)) {
                                    playerScore = model.countPlayerScore()
                                    model.changeModelState(UjimonModel.UjimonState.END)
                                }
                            }

                            if (attackIsChoosen && model.state != UjimonModel.UjimonState.HEALTH_HEALING && model.state != UjimonModel.UjimonState.END)
                                model.changeModelState(UjimonModel.UjimonState.WAITING)
                            else if (model.state == UjimonModel.UjimonState.HEALTH_HEALING)
                                playHealingMusic()

                        }


                        if (model.state == UjimonModel.UjimonState.PLAYER_TURN) {
                            if (!justClickedBattle && correctedEventX >= changeButtonCol && correctedEventX < changeButtonCol + Assets.BUTTONS_WIDTH
                                    && correctedEventY >= changeButtonRow && correctedEventY < changeButtonRow + Assets.UJIMON_SIZE_BUTTON) {
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                            }
                            if (correctedEventX >= attackButtonCol && correctedEventX < attackButtonCol + Assets.BUTTONS_WIDTH
                                    && correctedEventY >= attackButtonRow && correctedEventY < attackButtonRow + Assets.UJIMON_SIZE_BUTTON) {
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_ATTACK)
                            }

                            if (justClickedBattle)
                                justClickedBattle = false
                        }

                        if (model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
                            for (i in 0 until 5) {
                                if (i < 3 && correctedEventX >= buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + (Assets.UJIMON_SIZE_BUTTON + 2) * i
                                        && correctedEventY >= healthButtonRow && correctedEventY < healthButtonRow + Assets.UJIMON_SIZE_BUTTON) {
                                    if (ujimonHealed) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonHealed = false
                                        gameLevel++
                                        model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                        chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)
                                        playBattleMusic()
                                    } else {
                                        model.healUjimonSelected(i)
                                        ujimonHealed = true
                                    }
                                } else if (i >= 3 && correctedEventX >= buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i - 3) && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + (Assets.UJIMON_SIZE_BUTTON + 2) * (i - 3)
                                        && correctedEventY >= (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) && correctedEventY < (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + Assets.UJIMON_SIZE_BUTTON + 1.5f)) {
                                    if (ujimonHealed) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonHealed = false
                                        gameLevel++
                                        model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                        chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)
                                        playBattleMusic()
                                    } else {
                                        model.healUjimonSelected(i)
                                        ujimonHealed = true
                                    }
                                }
                            }
                        }

                        if (model.state == UjimonModel.UjimonState.END) {
                            if (correctedEventX >= battleButtonColInt && correctedEventX < battleButtonColInt + Assets.BUTTONS_WIDTH
                                    && correctedEventY >= battleButtonRowInt && correctedEventY < battleButtonRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                val intent = Intent(contex, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(contex, intent, null)
                            }
                        }
                    }
                }
            }
        }

        if (model.state == UjimonModel.UjimonState.COMPUTER_TURN) {
            chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)
            chosenAttack = model.chooseEnemyUjimonAttack(gameLevel)


            if (chosenAttack != null) {
                efectivenessMessage = model.askEnemyEffectiveness(chosenAttack!!)
                xAnimation = battlePlayerUjimonCol
                yAnimation = battlePlayerUjimonRow
                when (chosenAttack!!.type) {
                    Type.FIRE -> {
                        animation = Assets.fireAttack
                        drawAnimation()
                    }
                    Type.WATER -> {
                        animation = Assets.waterAttack
                        drawAnimation()
                    }
                    Type.NORMAL -> {
                        animation = Assets.darknessAttack
                        drawAnimation()
                    }
                    Type.PLANT -> {
                        animation = Assets.plantAttack
                        drawAnimation()
                    }
                    Type.GROUND -> {
                        animation = Assets.groundAttack
                        drawAnimation()
                    }
                    Type.DARKNESS -> {
                        animation = Assets.darknessAttack
                        drawAnimation()
                    }
                }

                if (chosenAttack!!.type == Type.NORMAL)
                    playNormalAttackSound()
                else
                    playPowerAttackSound()
            }
            model.changeModelState(UjimonModel.UjimonState.WAITING)
        }

        if (model.state == UjimonModel.UjimonState.PLAYER_CONDITION) {
            actualCondition = model.checkConditions(-1)
            model.changeModelState(UjimonModel.UjimonState.WAITING)
        }

        if (model.state == UjimonModel.UjimonState.COMPUTER_CONDITION) {
            actualCondition = model.checkConditions(gameLevel)
            if (model.state != UjimonModel.UjimonState.HEALTH_HEALING && model.state != UjimonModel.UjimonState.END)
                model.changeModelState(UjimonModel.UjimonState.WAITING)
        }

        if (model.state == UjimonModel.UjimonState.WAITING) {
            waitingTime += deltaTime
            if (waitingTime >= waitingTimer) {
                waitingTime = 0f

                if (model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK || model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON) {
                    chosenUjimon = model.chooseEnemyActiveUjimon(gameLevel)
                    model.changeModelState(UjimonModel.UjimonState.COMPUTER_CONDITION)
                } else if (model.lastState == UjimonModel.UjimonState.COMPUTER_CONDITION) {
                    model.changeModelState(UjimonModel.UjimonState.COMPUTER_TURN)
                }
                else if (model.lastState == UjimonModel.UjimonState.COMPUTER_TURN) {
                    if (playerTrainer.ujimonSelected.dead) {
                        if (model.checkPlayerUjimonTeamDead()) {
                            model.changeModelState(UjimonModel.UjimonState.END)
                            playerScore = model.countPlayerScore()
                        }else
                            model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                    }else
                        model.changeModelState(UjimonModel.UjimonState.PLAYER_CONDITION)

                } else if (model.lastState == UjimonModel.UjimonState.PLAYER_CONDITION) {
                    if (playerTrainer.ujimonSelected.dead) {
                        if (model.checkPlayerUjimonTeamDead()) {
                            model.changeModelState(UjimonModel.UjimonState.END)
                            playerScore = model.countPlayerScore()
                        } else
                            model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                    } else {
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
            graphics.drawBitmap(Assets.ujidex, 0f, 0f)
            graphics.drawText(7 * cellSide + xOffset, 2 * cellSide + yOffset, "Choose your Ujimon Team")
            graphics.drawText((playerTeamStartColumnInt + 3) * cellSide + xOffset, (buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON + 2) * cellSide + yOffset, "Your team")
            drawUjimonButtons()

            if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                graphics.drawBitmap( Assets.battleButton, battleButtonColInt * cellSide + xOffset, battleButtonRowInt * cellSide + yOffset)
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN || model.state == UjimonModel.UjimonState.COMPUTER_TURN || model.state == UjimonModel.UjimonState.WAITING || model.state == UjimonModel.UjimonState.PLAYER_ATTACK
                || model.state == UjimonModel.UjimonState.PLAYER_CONDITION || model.state == UjimonModel.UjimonState.COMPUTER_CONDITION){
            graphics.setTextColor(TEXT_COLOR)
            graphics.drawBitmap(Assets.battlefield, 0f,0f)
            graphics.drawBitmap(Assets.promptBox, prompBoxPos * cellSide + xOffset,prompBoxPos * cellSide + yOffset )

            drawUjimonsInBattle()
            drawConditions()

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
            graphics.drawBitmap(Assets.ujidex, 0f, 0f)
            graphics.setTextColor(MENU_TEXT_COLOR)
            graphics.drawText(playerTeamStartColumnInt * cellSide + xOffset, 11 * cellSide + yOffset, "Choose your Ujimon")
            drawPlayerUjimonButtons()
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
            drawAttacksButtons(playerTrainer.ujimonSelected)
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
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_CONDITION){
                drawConditionText(true)
            }
            else if(model.lastState == UjimonModel.UjimonState.COMPUTER_CONDITION){
                drawConditionText(false)
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK) {
                if(chosenAttack != null) {
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, "You have used : " + chosenAttack!!.Nombre)
                graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,  efectivenessMessage)
                }
                else {
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, "You have failed the attack.")
                }
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have changed your Ujimon to " + playerTrainer.ujimonSelected.name)
        }

        if(model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
            graphics.drawBitmap(Assets.ujidex, 0f, 0f)
            graphics.setTextColor(MENU_TEXT_COLOR)
            if(ujimonHealed)
                graphics.drawText(playerTeamStartColumnInt * cellSide + xOffset, 11 * cellSide + yOffset, "Choose an Ujimon to go to the next battle")
            else
                graphics.drawText(playerTeamStartColumnInt * cellSide + xOffset, 11 * cellSide + yOffset, "Choose an Ujimon to heal it")
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
                    graphics.drawBitmap(ujimon.buttonAsset ,(playerTeamStartColumnInt + Assets.UJIMON_SIZE_BUTTON * k)* cellSide + xOffset, (buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON + 2) * cellSide + yOffset)
                    k++
                }
                else
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)

                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)
            }
            else {
                if (model.ujimonAlreadySelected(ujimon, playerTrainer.ujimonTeam)) {
                    graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * (i - 5)) * cellSide + xOffset, buttonStartSecondRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, BUTTON_SELECTED_COLOR)
                    graphics.drawBitmap(ujimon.buttonAsset, (playerTeamStartColumnInt + Assets.UJIMON_SIZE_BUTTON * k) * cellSide + xOffset, (buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON + 2) * cellSide + yOffset)
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
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, BUTTON_SELECTED_COLOR)
            else
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
            graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i) * cellSide + xOffset, buttonStartRow)

            if(ujimon.dead)
                graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 0.5f) * i) * cellSide + xOffset, buttonStartRow)
            i++
        }

        if(!playerTrainer.ujimonSelected.dead)
            graphics.drawBitmap(Assets.backButton, battleButtonColumn, battleButtonRow)
    }

    private fun drawPlayerHealingButtons() {
        var i = 0
        graphics.setTextColor(HEALTH_TEXT_COLOR)
        graphics.setTextSize(14)

        for(ujimon in playerTrainer.ujimonTeam) {

            if( i < 3){
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
                graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

                if(ujimon.dead)
                    graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
            }
            else{
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset)
                graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + Assets.UJIMON_SIZE_BUTTON + 2.5f) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

                if(ujimon.dead)
                    graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset)
            }


            i++
        }
        graphics.setTextSize(20)
    }

    private fun drawEndingScreen() {
        graphics.drawBitmap(Assets.ujidex, 0f, 0f)

        if(playerScore <= 0){
            graphics.drawText(6 * cellSide + xOffset, 10 * cellSide + yOffset, "HAS PERDIDO EL COMBATE")
            playDefeated()
        }
        else{
            graphics.drawText(6 * cellSide + xOffset, 10 * cellSide + yOffset, "HAS GANADO EL COMBATE")
            playVictory()
        }

        var i = 0
        graphics.setTextColor(HEALTH_TEXT_COLOR)
        graphics.setTextSize(14)

        for(ujimon in playerTrainer.ujimonTeam) {

            if( i < 3){
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
                graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

                graphics.drawText((5 + 3*i ) * cellSide + xOffset, 11f * cellSide + yOffset,"${ujimon.name}: ${ujimon.healthPoints}")

                if(ujimon.dead)
                    graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * i) * cellSide + xOffset, healthButtonRow * cellSide + yOffset)
            }
            else{
                graphics.drawRect((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset)
                graphics.drawText((buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + Assets.UJIMON_SIZE_BUTTON + 2.5f) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

                graphics.drawText((5 + 3*(i-3) ) * cellSide + xOffset, 12f * cellSide + yOffset,"${ujimon.name}: ${ujimon.healthPoints}")

                if(ujimon.dead)
                    graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + (Assets.UJIMON_SIZE_BUTTON + 2) * (i-3)) * cellSide + xOffset, (healthButtonRow + Assets.UJIMON_SIZE_BUTTON + 1.5f) * cellSide + yOffset)
            }


            i++
        }

        graphics.setTextSize(20)
        graphics.drawText(12 * cellSide + xOffset, 12.5f * cellSide + yOffset, "Total Score: $playerScore")
        graphics.drawBitmap( Assets.replayButton, battleButtonColumn, battleButtonRow)
    }


    private  fun drawAttacksButtons(ujimon : Ujimon){
        var i = 0
        graphics.drawBitmap(Assets.attackBox, attackBoxCol * cellSide + xOffset, attackBoxRow * cellSide + yOffset)
        for(attack in ujimon.ujimonAttacks){
            chooseTextColor(attack.type)
            graphics.drawText((attackButtonCol+1) * cellSide + xOffset, (attackButtonRow+i-1) * cellSide + yOffset , attack.Nombre +  " " + attack.currentAmount + "/" + attack.totalAmount)
            i++
        }
        graphics.setTextColor(TEXT_COLOR)
    }

    private fun chooseTextColor(type : Type) {
        when(type){
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
    }

    private fun drawUjimonsInBattle() {
        graphics.drawBitmap(playerTrainer.ujimonSelected.backAsset, battlePlayerUjimonCol * cellSide + xOffset, battlePlayerUjimonRow * cellSide + yOffset)
        graphics.drawText(battlePlayerUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,(battlePlayerUjimonRow+ Assets.UJIMON_SIZE_COMBAT + 1) * cellSide + yOffset,"${playerTrainer.ujimonSelected.name} HP: ${playerTrainer.ujimonSelected.healthPoints}")
        chooseTextColor(playerTrainer.ujimonSelected.type)
        graphics.drawText(battlePlayerUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT,(battlePlayerUjimonRow + Assets.UJIMON_SIZE_COMBAT) * cellSide + yOffset,"Type: ${playerTrainer.ujimonSelected.type}")
        graphics.setTextColor(TEXT_COLOR)

        graphics.drawBitmap(chosenUjimon!!.imageAsset, battleEnemuUjimonCol * cellSide + xOffset, battleEnemyUjimonRow * cellSide + yOffset)
        graphics.drawText(battleEnemuUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT + 1,battleEnemyUjimonRow * cellSide + yOffset,"${chosenUjimon!!.name} HP: ${chosenUjimon!!.healthPoints}")
        chooseTextColor(chosenUjimon!!.type)
        graphics.drawText(battleEnemuUjimonCol * cellSide + xOffset + Assets.UJIMON_SIZE_COMBAT,(battleEnemyUjimonRow - 1) * cellSide + yOffset,"Type: ${chosenUjimon!!.type}")

        graphics.setTextColor(TEXT_COLOR)
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

    private fun drawConditions(){
        if(playerTrainer.ujimonSelected.condition.first > 0){
            when(playerTrainer.ujimonSelected.condition.second){
                Type.FIRE->{
                    graphics.drawBitmap(Assets.burned, playerConditionCol * cellSide + xOffset, playerConditionRow * cellSide + yOffset)
                }
                Type.WATER->{
                    graphics.drawBitmap(Assets.cold, playerConditionCol * cellSide + xOffset, playerConditionRow * cellSide + yOffset)
                }
                Type.PLANT->{
                    graphics.drawBitmap(Assets.trapped, playerConditionCol * cellSide + xOffset, playerConditionRow * cellSide + yOffset)
                }
                Type.DARKNESS->{
                    graphics.drawBitmap(Assets.frightened, playerConditionCol * cellSide + xOffset, playerConditionRow * cellSide + yOffset)
                }
                Type.GROUND->{
                    graphics.drawBitmap(Assets.buried, playerConditionCol * cellSide + xOffset, playerConditionRow * cellSide + yOffset)
                }
            }
        }
        if(chosenUjimon!!.condition.first > 0){
            when(chosenUjimon!!.condition.second){
                Type.FIRE->{
                    graphics.drawBitmap(Assets.burned, enemyConditionCol * cellSide + xOffset, enemyConditionRow * cellSide + yOffset)
                }
                Type.WATER->{
                    graphics.drawBitmap(Assets.cold, enemyConditionCol * cellSide + xOffset, enemyConditionRow * cellSide + yOffset)
                }
                Type.PLANT->{
                    graphics.drawBitmap(Assets.trapped, enemyConditionCol * cellSide + xOffset, enemyConditionRow * cellSide + yOffset)
                }
                Type.DARKNESS->{
                    graphics.drawBitmap(Assets.frightened, enemyConditionCol * cellSide + xOffset, enemyConditionRow * cellSide + yOffset)
                }
                Type.GROUND->{
                    graphics.drawBitmap(Assets.buried, enemyConditionCol * cellSide + xOffset, enemyConditionRow * cellSide + yOffset)
                }
            }
        }
    }

    private fun drawConditionText(isPlayer : Boolean) {
        if(isPlayer){
            when(actualCondition){
                Type.FIRE->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's burned")
                }
                Type.PLANT->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset, "he's trapped")
                }
                Type.DARKNESS->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's frightened")
                }
                Type.WATER->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's cold")
                }
                Type.GROUND->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's buried")
                }
                Type.NORMAL->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, playerTrainer.ujimonSelected.name + " it's not affected")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"by any condition")
                }
            }
        }
        else {
            when(actualCondition){
                Type.FIRE->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's burned")
                }
                Type.PLANT->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset, "he's trapped")
                }
                Type.DARKNESS->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's frightened")
                }
                Type.WATER->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's cold")
                }
                Type.GROUND->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " receives damage because")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"he's buried")
                }
                Type.NORMAL->{
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, chosenUjimon!!.name + " it's not affected")
                    graphics.drawText(promptColumn * cellSide + xOffset, (promptRow+1) * cellSide + yOffset,"by any condition")
                }
            }
        }
    }

    private fun prepareSoundMedia(contex: Context) {
        val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        soundPool = SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(attributes).build()

        victoryId = soundPool.load(contex, R.raw.victory, 0)
        defeatId = soundPool.load(contex, R.raw.game_over, 0)
        attackSoundId = soundPool.load(contex, R.raw.attack_kick, 0)
        powerSoundId = soundPool.load(contex, R.raw.attack_power, 0)
    }

    override fun playIntroMusic() {
        if(musicPlayer == null) {
            musicPlayer = MediaPlayer.create(contex, R.raw.intro_song).apply {
                start()
                isLooping = true
                setVolume(0.15f, 0.15f)
            }
        }
    }

    override fun playBattleMusic() {
        if(musicPlayer != null){
            musicPlayer!!.release()

            musicPlayer = MediaPlayer.create(contex, R.raw.fight_song).apply {
                start()
                isLooping = true
                setVolume(0.15f, 0.15f)
            }
        }
    }

    override fun playHealingMusic() {
        if(musicPlayer != null){
            musicPlayer!!.release()

            musicPlayer = MediaPlayer.create(contex, R.raw.healing_song).apply {
                start()
                isLooping = true
                setVolume(0.15f, 0.15f)
            }
        }
    }

    override fun playVictory() {
        musicPlayer!!.release()
        soundPool.play(victoryId, 1f, 1f, 1, 0, 1f)
    }

    override fun playDefeated() {
        musicPlayer!!.release()
        soundPool.play(defeatId, 1f, 1f, 1, 0, 1f)
    }

    override fun playNormalAttackSound() {
        soundPool.play(attackSoundId, 0.6f, 0.8f, 0, 0, 1f)
    }

    override fun playPowerAttackSound() {
        soundPool.play(powerSoundId, 0.6f, 0.8f, 0, 0, 1f)
    }

}