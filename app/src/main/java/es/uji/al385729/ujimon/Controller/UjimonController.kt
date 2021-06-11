package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.SoundPool
import es.uji.al385729.ujimon.Assets
import es.uji.al385729.ujimon.Model.*
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
        private val TEXT_COLOR = Color.rgb(0xF8, 0xFB, 0xF9)
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
    private val battlePlayerUjimonRow = 7
    private val battlePlayerUjimonCol = 3
    private val battleEnemyUjimonRow =2
    private val battleEnemuUjimonCol = 15
    private val playerTeamStartColumnInt = 14
    private var ujimonChoosen = 0
    private var gameLevel = 1
    private val attackButtonCol = 11
    private val attackButtonRow = 11
    private val changeButtonCol = 14
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
                        if(model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
                            chosenAttack = null
                            for (i in 0 until 4){
                                if(correctedEventX >= attackButtonCol && correctedEventY >= attackButtonRow+i-2 && correctedEventY < attackButtonRow + i+1){
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
                                when(gameLevel){
                                    1->{
                                        computerEnemy1.ujimonSelected!!.recieveAttack(chosenAttack!!)
                                        if(computerEnemy1.ujimonSelected!!.dead){
                                            if(model.checkEnemyUjimonTeamDead(computerEnemy1))
                                                model.changeModelState(UjimonModel.UjimonState.HEALTH_HEALING)
                                            else{
                                                for(ujimon in computerEnemy1.ujimonTeam){
                                                    if(!ujimon.dead && ujimon.type != Type.NORMAL){
                                                        computerEnemy1.ujimonSelected = ujimon
                                                        break
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    2->{
                                        computerEnemy2.ujimonSelected!!.recieveAttack(chosenAttack!!)
                                        if(computerEnemy2.ujimonSelected!!.dead){
                                            if(model.checkEnemyUjimonTeamDead(computerEnemy2))
                                                model.changeModelState(UjimonModel.UjimonState.HEALTH_HEALING)
                                            else {
                                                for(ujimon in computerEnemy2.ujimonTeam){
                                                    if(!ujimon.dead && ujimon.type != Type.NORMAL){
                                                        computerEnemy2.ujimonSelected = ujimon
                                                        break
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    3->{
                                        computerEnemy3.ujimonSelected!!.recieveAttack(chosenAttack!!)
                                        if(computerEnemy3.ujimonSelected!!.dead){
                                            if(model.checkEnemyUjimonTeamDead(computerEnemy3))
                                                model.changeModelState(UjimonModel.UjimonState.HEALTH_HEALING)
                                            else {
                                                for(ujimon in computerEnemy3.ujimonTeam){
                                                    if(!ujimon.dead && ujimon.type != Type.NORMAL){
                                                        computerEnemy3.ujimonSelected = ujimon
                                                        break
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if(model.state != UjimonModel.UjimonState.HEALTH_HEALING)
                                    model.changeModelState(UjimonModel.UjimonState.WAITING)
                            }

                        }
                        if(model.state == UjimonModel.UjimonState.PLAYER_TURN){
                            if(correctedEventX >= changeButtonCol && correctedEventX< changeButtonCol + Assets.UJIMON_SIZE_BUTTON
                                && correctedEventY >= changeButtonRow && correctedEventY < changeButtonRow + Assets.UJIMON_SIZE_BUTTON){
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                            }
                            if(correctedEventX >= attackButtonCol && correctedEventX< attackButtonCol + Assets.UJIMON_SIZE_BUTTON
                                    && correctedEventY >= attackButtonRow && correctedEventY < attackButtonRow + Assets.UJIMON_SIZE_BUTTON){
                                model.changeModelState(UjimonModel.UjimonState.PLAYER_ATTACK)
                            }
                        }

                        if(model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
                            for (i in 0 until 5) {
                                if (correctedEventX >= buttonStartColumnInt + i * 2 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 2
                                        && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    if (ujimonHealed) {
                                        playerTrainer.ujimonSelected = playerTrainer.ujimonTeam[i]
                                        ujimonHealed = false
                                        gameLevel++
                                        if(gameLevel > 3)
                                            model.changeModelState(UjimonModel.UjimonState.END)
                                        else
                                            model.changeModelState(UjimonModel.UjimonState.PLAYER_TURN)
                                    }
                                    else {
                                        playerTrainer.ujimonTeam[i].healHealthPoints()
                                        playerTrainer.recoverTeamEnergy()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(model.state == UjimonModel.UjimonState.COMPUTER_TURN){
            chosenAttack = null
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
                else if(model.lastState == UjimonModel.UjimonState.COMPUTER_TURN) {
                    if(playerTrainer.ujimonSelected!!.dead){
                        if(model.checkPlayerUjimonTeamDead())
                            model.changeModelState(UjimonModel.UjimonState.END)
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
        graphics.setTextColor(TEXT_COLOR)


        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION || model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
            graphics.drawText(4 * cellSide + xOffset, cellSide + yOffset, "Choose your Ujimon Team")
            drawUjimonButtons()

            if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED)
                graphics.drawBitmap( Assets.battleButton, battleButtonColInt * cellSide + xOffset, battleButtonRowInt * cellSide + yOffset)
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN || model.state == UjimonModel.UjimonState.COMPUTER_TURN || model.state == UjimonModel.UjimonState.WAITING || model.state == UjimonModel.UjimonState.PLAYER_ATTACK){
            graphics.drawBitmap(Assets.battlefield, 0f,0f)
            graphics.drawBitmap(Assets.promptBox, prompBoxPos * cellSide + xOffset,prompBoxPos * cellSide + yOffset )
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

            if(model.state != UjimonModel.UjimonState.PLAYER_ATTACK) {
                graphics.drawBitmap(Assets.attackButton, attackButtonCol * cellSide + xOffset, attackButtonRow * cellSide + yOffset)
                graphics.drawBitmap(Assets.changeButton, changeButtonCol * cellSide + xOffset, changeButtonRow * cellSide + yOffset)
            }


        }

        if(model.state == UjimonModel.UjimonState.PLAYER_TURN){
            graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset, "Make your move")
        }

        if(model.state == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON){
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
                if(chosenAttack != null)
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  chosenUjimon!!.name + " have used " + chosenAttack!!.Nombre)
                else
                    graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  chosenUjimon!!.name + " have failed the attack.")
            }
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_ATTACK)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have used : " + chosenAttack!!.Nombre)
            else if(model.lastState == UjimonModel.UjimonState.PLAYER_CHOOSE_UJIMON)
                graphics.drawText(promptColumn * cellSide + xOffset, promptRow * cellSide + yOffset,  "You have changed your Ujimon to " + playerTrainer.ujimonSelected!!.name)
        }

        if(model.state == UjimonModel.UjimonState.HEALTH_HEALING) {
            graphics.setTextColor(TEXT_COLOR)
            if(ujimonHealed)
                graphics.drawText(4 * cellSide + xOffset, 10 * cellSide + yOffset, "Choose an Ujimon to go to the next battle")
            else
                graphics.drawText(4 * cellSide + xOffset, 10 * cellSide + yOffset, "Choose an Ujimon to heal it")
            drawPlayerHealingButtons()
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

    private fun drawPlayerHealingButtons() {
        var i = 0
        for(ujimon in playerTrainer.ujimonTeam) {

            graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
            graphics.drawBitmap(ujimon.buttonAsset,(buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)
            graphics.setTextColor(HEALTH_TEXT_COLOR)
            graphics.drawText((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, (buttonStartRowInt + 1) * cellSide + yOffset, "HP: " + ujimon.healthPoints + " / 1000.0")

            if(ujimon.dead)
                graphics.drawBitmap(Assets.deadCross, (buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i) * cellSide + xOffset, buttonStartRow)
            i++
        }
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



    private fun drawAnimation(){
        animation?.let {  graphics.drawBitmap(it.currentFrame, xAnimation * cellSide + xOffset, yAnimation * cellSide + yOffset) }
        animation?.restart()
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