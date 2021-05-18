package es.uji.al385729.ujimon

import android.content.pm.ActivityInfo
import android.util.DisplayMetrics
import es.uji.al385729.ujimon.Controller.UjimonController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class UjimonGameActivity : GameActivity() {
    override fun buildGameController(): IGameController {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val displayMetrics = DisplayMetrics()

        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return UjimonController(displayMetrics.widthPixels,
                displayMetrics.heightPixels, applicationContext)
    }
}