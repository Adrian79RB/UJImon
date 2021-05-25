package es.uji.al385729.ujimon.Model

import es.uji.al385729.ujimon.Assets

class UjimonInstances {
    val ujimonArray: Array<Ujimon> = Array<Ujimon>(10) {
        Ujimon(1000f, "Maglug", Assets.maglugAsset, false, Type.FIRE)
        Ujimon(1000f, "Obsho", Assets.obshoAsset, false, Type.DARKNESS)
        Ujimon(1000f, "Redash", Assets.redashAsset, false, Type.PLANT)
        Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS)
        Ujimon(1000f, "Maglug", Assets.maglugAsset, false, Type.FIRE)
        Ujimon(1000f, "Obsho", Assets.obshoAsset, false, Type.DARKNESS)
        Ujimon(1000f, "Redash", Assets.redashAsset, false, Type.PLANT)
        Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS)
        Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS)
    }

    init {
        ujimonArray[0].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.5f)
        ujimonArray[0].ujimonTolerance[1] = Tolerance(Type.GROUND, 1.5f)
        ujimonArray[0].ujimonTolerance[2] = Tolerance(Type.WATER, 1.7f)
        ujimonArray[0].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.3f)
        ujimonArray[0].ujimonTolerance[4] = Tolerance(Type.FIRE, 1f)

        ujimonArray[1].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1f)
        ujimonArray[1].ujimonTolerance[1] = Tolerance(Type.GROUND,0.5f)
        ujimonArray[1].ujimonTolerance[2] = Tolerance(Type.WATER, 1.3f)
        ujimonArray[1].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.3f)
        ujimonArray[1].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.7f)

        ujimonArray[2].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1.8f)
        ujimonArray[2].ujimonTolerance[1] = Tolerance(Type.GROUND, 0.1f)
        ujimonArray[2].ujimonTolerance[2] = Tolerance(Type.WATER, 0.5f)
        ujimonArray[2].ujimonTolerance[3] = Tolerance(Type.PLANT, 1f)
        ujimonArray[2].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.5f)

        ujimonArray[3].ujimonTolerance[0] = Tolerance(Type.DARKNESS, 1f)
        ujimonArray[3].ujimonTolerance[1] = Tolerance(Type.GROUND , 0.2f)
        ujimonArray[3].ujimonTolerance[2] = Tolerance(Type.WATER, 1.3f)
        ujimonArray[3].ujimonTolerance[3] = Tolerance(Type.PLANT, 0.6f)
        ujimonArray[3].ujimonTolerance[4] = Tolerance(Type.FIRE, 1.8f)
    }
}
