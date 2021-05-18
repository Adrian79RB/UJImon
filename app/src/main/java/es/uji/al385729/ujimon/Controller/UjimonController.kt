package es.uji.al385729.ujimon.Controller

import android.content.Context
import android.graphics.Bitmap
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class UjimonController(val width : Int,
                        val heightSpan : Int, val contex : Context) : IGameController {
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        TODO("Not yet implemented")
    }

}