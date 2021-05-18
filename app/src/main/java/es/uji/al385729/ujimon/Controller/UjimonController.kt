package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import es.uji.al385729.ujimon.Model.UjimonModel
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class UjimonController(val width : Int,
                        val height : Int, val contex : Context) : IGameController {

    companion object {
        private val TOTAL_CELL_WIDTH = 24
        private val TOTAL_CELL_HEIGHT = 14
        private val BACKGROUND_COLOR = Color.rgb(0xff, 0xff, 0xff)
        private val SELECTION_MENU_COLOR = Color.rgb(0xC5, 0xC0, 0x24)
    }

    private val graphics : Graphics
    private val model : UjimonModel

    init {

    }
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
    }

}