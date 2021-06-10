package es.uji.al385729.ujimon.Model

class Trainer() {
    var ujimonTeam : Array<Ujimon> = Array(5){Ujimon(0f, "", null, null,false, Type.NORMAL)}
    var ujimonSelected : Ujimon? = null

    fun recoverTeamEnergy() {
        for(ujimon in ujimonTeam){
            ujimon.recoverEnergy()
        }
    }
}