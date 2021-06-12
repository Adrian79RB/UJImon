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

        ujimonArray[4] = Ujimon(1000f, "Aracleaf", Assets.assetsCombat[4], Assets.assetsButton[4],false, Type.PLANT)
        ujimonArray[4].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1.4f)
        ujimonArray[4].ujimonTolerance[1] = Tolerance(Type.GROUND , 0.7f)
        ujimonArray[4].ujimonTolerance[2] = Tolerance(Type.WATER, 0.2f)
        ujimonArray[4].ujimonTolerance[3] = Tolerance(Type.PLANT, 1f)
        ujimonArray[4].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.8f)

        ujimonArray[5] = Ujimon(1000f, "Bolhip", Assets.assetsCombat[5], Assets.assetsButton[5],false, Type.GROUND)
        ujimonArray[5].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1.4f)
        ujimonArray[5].ujimonTolerance[1] = Tolerance(Type.GROUND , 1f)
        ujimonArray[5].ujimonTolerance[2] = Tolerance(Type.WATER, 0.2f)
        ujimonArray[5].ujimonTolerance[3] = Tolerance(Type.PLANT, 1.7f)
        ujimonArray[5].ujimonTolerance[4] = Tolerance(Type.FIRE, 0.8f)

        ujimonArray[6] = Ujimon(1000f, "Conder", Assets.assetsCombat[6], Assets.assetsButton[6],false, Type.WATER)
        ujimonArray[6].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.6f)
        ujimonArray[6].ujimonTolerance[1] = Tolerance(Type.GROUND , 1.8f)
        ujimonArray[6].ujimonTolerance[2] = Tolerance(Type.WATER, 1f)
        ujimonArray[6].ujimonTolerance[3] = Tolerance(Type.PLANT, 1.4f)
        ujimonArray[6].ujimonTolerance[4] = Tolerance(Type.FIRE, 0.2f)

        ujimonArray[7] = Ujimon(1000f, "Dihap", Assets.assetsCombat[7], Assets.assetsButton[7],false, Type.FIRE)
        ujimonArray[7].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.7f)
        ujimonArray[7].ujimonTolerance[1] = Tolerance(Type.GROUND , 1.7f)
        ujimonArray[7].ujimonTolerance[2] = Tolerance(Type.WATER, 1.3f)
        ujimonArray[7].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.2f)
        ujimonArray[7].ujimonTolerance[4] = Tolerance(Type.FIRE, 1f)

        ujimonArray[8] = Ujimon(1000f, "Golroc", Assets.assetsCombat[8], Assets.assetsButton[8],false, Type.GROUND)
        ujimonArray[8].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1.2f)
        ujimonArray[8].ujimonTolerance[1] = Tolerance(Type.GROUND , 1f)
        ujimonArray[8].ujimonTolerance[2] = Tolerance(Type.WATER, 0.7f)
        ujimonArray[8].ujimonTolerance[3] = Tolerance(Type.PLANT, 1.7f)
        ujimonArray[8].ujimonTolerance[4] = Tolerance(Type.FIRE, 0.2f)

        ujimonArray[9] = Ujimon(1000f, "Serwat", Assets.assetsCombat[9], Assets.assetsButton[9], false, Type.WATER)
        ujimonArray[9].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.7f)
        ujimonArray[9].ujimonTolerance[1] = Tolerance(Type.GROUND , 1.4f)
        ujimonArray[9].ujimonTolerance[2] = Tolerance(Type.WATER, 1f)
        ujimonArray[9].ujimonTolerance[3] = Tolerance(Type.PLANT, 1.8f)
        ujimonArray[9].ujimonTolerance[4] = Tolerance(Type.FIRE, 0.2f)
    }
}
