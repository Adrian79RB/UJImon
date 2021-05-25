package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.SoundPool
import es.uji.al385729.ujimon.Model.Trainer
import es.uji.al385729.ujimon.Model.Type
import es.uji.al385729.ujimon.Model.Ujimon
import es.uji.al385729.ujimon.Model.UjimonModel
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
    private val ujimonToChoose = Array<Ujimon>(10) { Ujimon(0f, "", null, true, Type.NORMAL) }

    val playerTrainer = Trainer()
    val computerEnemy1 = Trainer()
    val computerEnemy2 = Trainer()
    val computerEnemy3 = Trainer()

    init {
        graphics = Graphics(width, height)
        model = UjimonModel(playerTrainer, computerEnemy1, computerEnemy2, computerEnemy3, this)
        fillUjimonArray()
    }

    private fun fillUjimonArray() {
        ujimonToChoose[0] = model.ujimonInstances.maglugInstance
        ujimonToChoose[1] = model.ujimonInstances.obshoInstance
        ujimonToChoose[2] = model.ujimonInstances.redashInstance
        ujimonToChoose[3] = model.ujimonInstances.sworsthInstance
        ujimonToChoose[4] = model.ujimonInstances.maglugInstance
        ujimonToChoose[5] = model.ujimonInstances.obshoInstance
        ujimonToChoose[6] = model.ujimonInstances.redashInstance
        ujimonToChoose[7] = model.ujimonInstances.sworsthInstance
        ujimonToChoose[8] = model.ujimonInstances.maglugInstance
        ujimonToChoose[9] = model.ujimonInstances.obshoInstance
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if(touchEvents != null){
            for (event in touchEvents){
                val correctedEventX = ((event.x - xOffset)/cellSide).toInt()
                val correctedEventY = ((event.y - yOffset)/cellSide).toInt()

                when(event.type){
                    TouchHandler.TouchType.TOUCH_UP -> {
                        if(model.state == UjimonModel.UjimonState.UJIMON_SELECTION){
                            for(i in 1..2)
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

        return graphics.frameBuffer
    }

    private fun drawUjimonButtons() {
        val i = 0
        for(ujimon in ujimonToChoose){
            graphics.drawRect(buttonStartColumn * cellSide + xOffset, buttonStartRow)
        }
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