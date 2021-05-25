package es.uji.al385729.ujimon

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object Assets {

    const val UJIMON_SIZE_COMBAT = 5
    const val UJIMON_SIZE_BUTTON = 2
    var assetsCombat : Array<Bitmap?> = Array(10){null}
    var assetsFromBack : Array<Bitmap?> = Array(10){null}
    var assetsButton : Array<Bitmap?> = Array(10){null}


    fun createResizedAssets(context: Context, cellSize : Int){
        val resources : Resources = context.resources

        assetsCombat[0]?.recycle()
        assetsCombat[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.maglug), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[0]?.recycle()
        assetsButton[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.maglug), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[1]?.recycle()
        assetsCombat[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.obsho), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[1]?.recycle()
        assetsButton[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.obsho), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[2]?.recycle()
        assetsCombat[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.redash), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[2]?.recycle()
        assetsButton[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.redash), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[3]?.recycle()
        assetsCombat[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sworsth), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[3]?.recycle()
        assetsButton[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sworsth), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

    }
}