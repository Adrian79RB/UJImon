package es.uji.al385729.ujimon.Model

class Trainer() {
    var ujimonTeam : Array<Ujimon> = Array(5){Ujimon(0f, "", null, null, null, true, Type.NORMAL, Pair(0, Type.NORMAL))}
    var ujimonSelected : Ujimon = Ujimon(0f, "", null, null, null, true, Type.NORMAL, Pair(0, Type.NORMAL))
}