package es.uji.al385729.ujimon.Model

import es.uji.al385729.ujimon.Assets

class UjimonInstances {
    val maglugInstance = Ujimon(1000f, "Maglug", Assets.maglugAsset, false, Type.FIRE)
    val obshoInstance = Ujimon(1000f, "Obsho", Assets.obshoAsset, false, Type.DARKNESS)
    val redashInstance = Ujimon(1000f, "Redash", Assets.redashAsset, false, Type.PLANT)
    val sworsthInstance = Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS)

    init {
        maglugInstance.ujimonTolerance[0] = Tolerance(Type.DARKNESS, 0.5f)
        maglugInstance.ujimonTolerance[1] = Tolerance(Type.GROUND, 1.5f)
    }
}
