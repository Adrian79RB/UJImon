package es.uji.al385729.ujimon

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object Assets {

    const val UJIMON_SIZE = 5;
    var maglugAsset : Bitmap? = null
    var obshoAsset : Bitmap? = null
    var redashAsset : Bitmap? = null
    var sworsthAsset:Bitmap? = null

    fun createResizedAssets(context: Context, cellSize : Int){
        val resources : Resources = context.resources

        maglugAsset?.recycle()
        maglugAsset = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.maglug), cellSize * UJIMON_SIZE, cellSize * UJIMON_SIZE, true)

        obshoAsset?.recycle()
        obshoAsset = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.obsho), cellSize * UJIMON_SIZE, cellSize * UJIMON_SIZE, true)

        redashAsset?.recycle()
        redashAsset = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.redash), cellSize * UJIMON_SIZE, cellSize * UJIMON_SIZE, true)

        sworsthAsset?.recycle()
        sworsthAsset = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sworsth), cellSize * UJIMON_SIZE, cellSize * UJIMON_SIZE, true)


    }
}