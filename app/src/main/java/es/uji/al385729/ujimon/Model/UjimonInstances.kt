package es.uji.al385729.ujimon.Model

import es.uji.al385729.ujimon.Assets

class UjimonInstances {
    val ujimonArray: Array<Ujimon> = Array<Ujimon>(10) {
        Ujimon(0f, "", null, null, false, Type.NORMAL)
    }

    init {
        ujimonArray[0] = Ujimon(1000f, "Maglug", Assets.assetsCombat[0], Assets.assetsButton[0], false, Type.FIRE)
        ujimonArray[0].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.5f)
        ujimonArray[0].ujimonTolerance[1] = Tolerance(Type.GROUND, 1.5f)
        ujimonArray[0].ujimonTolerance[2] = Tolerance(Type.WATER, 1.7f)
        ujimonArray[0].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.3f)
        ujimonArray[0].ujimonTolerance[4] = Tolerance(Type.FIRE, 1f)

        ujimonArray[1] = Ujimon(1000f, "Obsho", Assets.assetsCombat[1], Assets.assetsButton[1], false, Type.DARKNESS)
        ujimonArray[1].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1f)
        ujimonArray[1].ujimonTolerance[1] = Tolerance(Type.GROUND,0.5f)
        ujimonArray[1].ujimonTolerance[2] = Tolerance(Type.WATER, 1.3f)
        ujimonArray[1].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.3f)
        ujimonArray[1].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.7f)

        ujimonArray[2] = Ujimon(1000f, "Redash", Assets.assetsCombat[2], Assets.assetsButton[2], false, Type.PLANT)
        ujimonArray[2].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1.8f)
        ujimonArray[2].ujimonTolerance[1] = Tolerance(Type.GROUND, 0.1f)
        ujimonArray[2].ujimonTolerance[2] = Tolerance(Type.WATER, 0.5f)
        ujimonArray[2].ujimonTolerance[3] = Tolerance(Type.PLANT, 1f)
        ujimonArray[2].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.5f)

        ujimonArray[3] = Ujimon(1000f, "Sworsth", Assets.assetsCombat[3], Assets.assetsButton[3], false, Type.DARKNESS)
        ujimonArray[3].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1f)
        ujimonArray[3].ujimonTolerance[1] = Tolerance(Type.GROUND , 0.2f)
        ujimonArray[3].ujimonTolerance[2] = Tolerance(Type.WATER, 1.3f)
        ujimonArray[3].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.6f)
        ujimonArray[3].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.8f)

        ujimonArray[4] = Ujimon(1000f, "1", Assets.assetsCombat[0], Assets.assetsButton[0],false, Type.FIRE)
        ujimonArray[5] = Ujimon(1000f, "2", Assets.assetsCombat[1], Assets.assetsButton[1],false, Type.DARKNESS)
        ujimonArray[6] = Ujimon(1000f, "3", Assets.assetsCombat[2], Assets.assetsButton[2],false, Type.PLANT)
        ujimonArray[7] = Ujimon(1000f, "4", Assets.assetsCombat[3], Assets.assetsButton[3],false, Type.DARKNESS)
        ujimonArray[8] = Ujimon(1000f, "5", Assets.assetsCombat[0], Assets.assetsButton[0],false, Type.FIRE)
        ujimonArray[9] = Ujimon(1000f, "6", Assets.assetsCombat[1], Assets.assetsButton[1], false, Type.DARKNESS)
    }
}
