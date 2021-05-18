package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.SoundPool
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


    init {
        graphics = Graphics(width, height)
        model = UjimonModel()
    }
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
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