package es.uji.al385729.ujimon.Model

import android.graphics.Bitmap

class UJImon(var healthPoints: Float, val name: String, val imageAsset: Bitmap?, var dead: Boolean, val type : Type) {
    var ujimonAttacks : Array<Attack> = Array<Attack>(4){ Attack ("", Type.NORMAL, 0f, 0f) }
    var ujimonTolerance : Array<Tolerance> = Array<Tolerance>(5){ Tolerance(Type.NORMAL,0f) }

}