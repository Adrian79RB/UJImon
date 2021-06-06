package es.uji.al385729.ujimon

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object Assets {

    const val UJIMON_SIZE_COMBAT = 5
    const val UJIMON_SIZE_BUTTON = 2
    const val BATTLEFIELD_WIDTH = 24
    const val BATTLEFIELD_HEIGHT = 14
    const val PROMPT_WIDTH = 12
    const val PROMPT_HEIGHT = 6
    var assetsCombat : Array<Bitmap?> = Array(10){null}
    var assetsFromBack : Array<Bitmap?> = Array(10){null}
    var assetsButton : Array<Bitmap?> = Array(10){null}
    var battleButton : Bitmap? = null
    var attackButton : Bitmap? = null
    var changeButton : Bitmap? = null
    var backButton : Bitmap? = null
    var deadCross : Bitmap? = null
    var battlefield : Bitmap? = null
    var promptBox : Bitmap? = null
    var attackBox : Bitmap? = null




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

        assetsCombat[4]?.recycle()
        assetsCombat[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.aracleaf), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[4]?.recycle()
        assetsButton[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.aracleaf), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[5]?.recycle()
        assetsCombat[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.bolhip), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[5]?.recycle()
        assetsButton[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.bolhip), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[6]?.recycle()
        assetsCombat[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.conder), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[6]?.recycle()
        assetsButton[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.conder), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[7]?.recycle()
        assetsCombat[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dihap), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[7]?.recycle()
        assetsButton[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dihap), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[8]?.recycle()
        assetsCombat[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.golroc), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[8]?.recycle()
        assetsButton[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.golroc), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[9]?.recycle()
        assetsCombat[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.serwat), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[9]?.recycle()
        assetsButton[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.serwat), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        battleButton?.recycle()
        battleButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.battle_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        attackButton?.recycle()
        attackButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.attack_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        changeButton?.recycle()
        changeButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.change_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        backButton?.recycle()
        backButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.back_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        deadCross?.recycle()
        deadCross = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dead_cross), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        battlefield?.recycle()
        battlefield = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.battlefield), cellSize * BATTLEFIELD_WIDTH, cellSize * BATTLEFIELD_HEIGHT, true)

        promptBox?.recycle()
        promptBox = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.prompt_box), cellSize * PROMPT_WIDTH, cellSize * PROMPT_HEIGHT, true)

        attackBox?.recycle()
       attackBox = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.attack_box), cellSize * PROMPT_WIDTH, cellSize * PROMPT_HEIGHT, true)

    }
}