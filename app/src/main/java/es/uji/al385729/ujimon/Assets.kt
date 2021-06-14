package es.uji.al385729.ujimon

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.SpriteSheet

object Assets {

    const val UJIMON_SIZE_COMBAT = 5
    const val UJIMON_SIZE_BUTTON = 2
    const val BUTTONS_WIDTH = 4
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
    var replayButton : Bitmap? = null
    var ujiball : Bitmap? = null
    var emptyujiball : Bitmap? = null
    var ujidex : Bitmap? = null
    var burned : Bitmap? = null
    var cold : Bitmap? = null
    var frightened : Bitmap? = null
    var trapped : Bitmap? = null
    var buried : Bitmap? = null

    private var plant : SpriteSheet? = null
    var plantAttack : AnimatedBitmap? = null

    private var water : SpriteSheet? = null
    var waterAttack : AnimatedBitmap? = null

    private var ground : SpriteSheet? = null
    var groundAttack : AnimatedBitmap? = null

    private var darkness : SpriteSheet? = null
    var darknessAttack : AnimatedBitmap? = null

    private var fire : SpriteSheet? = null
    var fireAttack : AnimatedBitmap? = null



    fun loadDrawableAssets(context: Context) {
        val resources: Resources = context.resources
        if(plant == null){
            val plantSheet = BitmapFactory.decodeResource(resources, R.drawable.plan_atack)
            plant = SpriteSheet(plantSheet, 192, 192)
        }
        if(water == null){
            val waterSheet = BitmapFactory.decodeResource(resources, R.drawable.water_attack)
            water = SpriteSheet(waterSheet, 192, 192)
        }
        if(ground == null){
            val groundSheet = BitmapFactory.decodeResource(resources, R.drawable.ground_attack)
            ground = SpriteSheet(groundSheet, 192, 192)
        }
        if(darkness == null){
            val darknessSheet = BitmapFactory.decodeResource(resources, R.drawable.darkness_attack)
            darkness = SpriteSheet(darknessSheet, 192, 192)
        }
        if(fire == null){
            val fireSheet = BitmapFactory.decodeResource(resources, R.drawable.fire_attack)
            fire = SpriteSheet(fireSheet, 192, 192)
        }
    }


    fun createResizedAssets(context: Context, cellSize : Int){
        val resources : Resources = context.resources

        assetsCombat[0]?.recycle()
        assetsCombat[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.maglug), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[0]?.recycle()
        assetsButton[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.maglug_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[1]?.recycle()
        assetsCombat[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.obsho), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[1]?.recycle()
        assetsButton[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.obsho_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[2]?.recycle()
        assetsCombat[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.redash), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[2]?.recycle()
        assetsButton[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.redash_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[3]?.recycle()
        assetsCombat[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sworsth), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[3]?.recycle()
        assetsButton[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.sworsth_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[4]?.recycle()
        assetsCombat[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.aracleaf), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[4]?.recycle()
        assetsButton[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.aracleaf_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[5]?.recycle()
        assetsCombat[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.bolhip), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[5]?.recycle()
        assetsButton[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.bolhip_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[6]?.recycle()
        assetsCombat[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.conder), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[6]?.recycle()
        assetsButton[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.conder_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[7]?.recycle()
        assetsCombat[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dihap), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[7]?.recycle()
        assetsButton[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dihap_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        assetsCombat[8]?.recycle()
        assetsCombat[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.golroc), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[8]?.recycle()
        assetsButton[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.golroc_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        assetsCombat[9]?.recycle()
        assetsCombat[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.serwat), cellSize * UJIMON_SIZE_COMBAT, cellSize * UJIMON_SIZE_COMBAT, true)
        assetsButton[9]?.recycle()
        assetsButton[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.serwat_button), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)


        battleButton?.recycle()
        battleButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.battle_button), cellSize * BUTTONS_WIDTH, cellSize * UJIMON_SIZE_BUTTON, true)

        attackButton?.recycle()
        attackButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.attack_button), cellSize * BUTTONS_WIDTH, cellSize * UJIMON_SIZE_BUTTON, true)

        changeButton?.recycle()
        changeButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.change_button), cellSize * BUTTONS_WIDTH, cellSize * UJIMON_SIZE_BUTTON, true)

        backButton?.recycle()
        backButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.back_button), cellSize * BUTTONS_WIDTH, cellSize * UJIMON_SIZE_BUTTON, true)

        replayButton?.recycle()
        replayButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.replay_button), cellSize * BUTTONS_WIDTH, cellSize* UJIMON_SIZE_BUTTON, true)

        deadCross?.recycle()
        deadCross = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dead_cross), cellSize * UJIMON_SIZE_BUTTON, cellSize * UJIMON_SIZE_BUTTON, true)

        battlefield?.recycle()
        battlefield = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.battlefield), cellSize * BATTLEFIELD_WIDTH, cellSize * BATTLEFIELD_HEIGHT, true)

        promptBox?.recycle()
        promptBox = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.prompt_box), cellSize * PROMPT_WIDTH, cellSize * PROMPT_HEIGHT, true)

        attackBox?.recycle()
       attackBox = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.attack_box), cellSize * PROMPT_WIDTH, cellSize * PROMPT_HEIGHT, true)

        ujiball?.recycle()
        ujiball = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.ujiball), cellSize, cellSize, true)

        emptyujiball?.recycle()
        emptyujiball = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.empty_ujiball), cellSize, cellSize, true)

        burned?.recycle()
        burned = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.burned), cellSize, cellSize, true)

        cold?.recycle()
        cold = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cold), cellSize, cellSize, true)

        frightened?.recycle()
        frightened = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.frightened), cellSize, cellSize, true)

        trapped?.recycle()
        trapped = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.trapped), cellSize, cellSize, true)

        buried?.recycle()
        buried = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.buried), cellSize, cellSize, true)

        ujidex?.recycle()
        ujidex = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.ujidex), cellSize * BATTLEFIELD_WIDTH, cellSize * BATTLEFIELD_HEIGHT, true )

        val plantAttackFrames = ArrayList<Bitmap>()
        plantAttack?.recycle()
        for(row in 0 until 1){
            plant?.let { plantAttackFrames.addAll(it.getScaledRow(row, 5, cellSize* UJIMON_SIZE_COMBAT, cellSize* UJIMON_SIZE_COMBAT)) }
        }
        plantAttack = AnimatedBitmap(1.0f, false, *plantAttackFrames.toTypedArray())


        val waterAttackFrames = ArrayList<Bitmap>()
        waterAttack?.recycle()
        for(row in 0 until 1){
            water?.let { waterAttackFrames.addAll(it.getScaledRow(row, 5, cellSize* UJIMON_SIZE_COMBAT, cellSize* UJIMON_SIZE_COMBAT)) }
        }
        waterAttack = AnimatedBitmap(1.0f, false, *waterAttackFrames.toTypedArray())


        val groundAttackFrames = ArrayList<Bitmap>()
        groundAttack?.recycle()
        for(row in 0 until 1){
            ground?.let { groundAttackFrames.addAll(it.getScaledRow(row, 5, cellSize* UJIMON_SIZE_COMBAT, cellSize* UJIMON_SIZE_COMBAT)) }
        }
        groundAttack = AnimatedBitmap(1.0f, false, *groundAttackFrames.toTypedArray())


        val darknessAttackFrames = ArrayList<Bitmap>()
        darknessAttack?.recycle()
        for(row in 0 until 1){
            darkness?.let { darknessAttackFrames.addAll(it.getScaledRow(row, 4, cellSize* UJIMON_SIZE_COMBAT, cellSize* UJIMON_SIZE_COMBAT)) }
        }
        darknessAttack = AnimatedBitmap(1.0f, false, *darknessAttackFrames.toTypedArray())


        val fireAttackFrames = ArrayList<Bitmap>()
        fireAttack?.recycle()
        for(row in 0 until 2){
            fire?.let { fireAttackFrames.addAll(it.getScaledRow(row, 5, cellSize* UJIMON_SIZE_COMBAT, cellSize* UJIMON_SIZE_COMBAT)) }
        }
        fireAttack = AnimatedBitmap(1.0f, false, *fireAttackFrames.toTypedArray())

    }
}