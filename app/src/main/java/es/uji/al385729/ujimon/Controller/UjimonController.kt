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

    private val battleButtonRow = 11 * cellSide + xOffset
    private val battleButtonColumn = 18 * cellSide + yOffset

    val playerTrainer = Trainer()
    val computerEnemy1 = Trainer()
    val computerEnemy2 = Trainer()
    val computerEnemy3 = Trainer()

    init {
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
                        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION){
                            for(i in 0..5){
                                if(correctedEventX >= buttonStartColumnInt + i * 3 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 4
                                    && correctedEventY >= buttonStartRowInt && correctedEventY < buttonStartRowInt + Assets.UJIMON_SIZE_BUTTON){
                                    val ujimonSelected = model.selectUjimon(i, 1)

                                }
                                if(correctedEventX >= buttonStartColumnInt + i * 3 && correctedEventX < buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON + i * 4
                                        && correctedEventY >= buttonStartSecondRowInt && correctedEventY < buttonStartSecondRowInt + Assets.UJIMON_SIZE_BUTTON) {
                                    val ujimonSelected = model.selectUjimon(i, 2)
                                    model.playerSelectUjimon(ujimonSelected, playerTrainer.ujimonTeam)
                                }
                            }
                        }
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
        }

        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTED){
            drawBattleButton()
        }

        return graphics.frameBuffer
    }

    private fun drawUjimonButtons() {
        for((i, ujimon) in model.ujimonInstances.ujimonArray.withIndex()){
            if(i < 5) {
                graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i + 2) * cellSide + xOffset, buttonStartRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.imageAsset,(buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i + 2) * cellSide + xOffset, buttonStartRow)
            }
            else {
                graphics.drawRect((buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i + 2) * cellSide + xOffset, buttonStartSecondRow, Assets.UJIMON_SIZE_BUTTON * cellSide, Assets.UJIMON_SIZE_BUTTON * cellSide, SELECTION_MENU_COLOR)
                graphics.drawBitmap(ujimon.imageAsset, (buttonStartColumnInt + Assets.UJIMON_SIZE_BUTTON * i + 2) * cellSide + xOffset, buttonStartSecondRow)
            }
        }
    }

    private fun drawBattleButton(){
        graphics.drawBitmap(Assets.battleButton, battleButtonColumn, battleButtonRow)
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