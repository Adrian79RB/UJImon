package es.uji.al385729.ujimon.Model

data class Attack(val Nombre : String, val type : Type, val damage : Float, val totalAmount : Float, var actualAmount : Float) {
}