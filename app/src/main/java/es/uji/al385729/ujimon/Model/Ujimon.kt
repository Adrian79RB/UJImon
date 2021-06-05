package es.uji.al385729.ujimon.Model

import android.graphics.Bitmap
import kotlin.random.Random

class Ujimon(var healthPoints: Float, val name: String, val imageAsset: Bitmap?, val buttonAsset : Bitmap?, var dead: Boolean, val type : Type) {
    var ujimonAttacks : Array<Attack> = Array<Attack>(4){ Attack ("", Type.NORMAL, 0f, 0f, 0f) }
    var ujimonTolerance : Array<Tolerance> = Array<Tolerance>(5){ Tolerance(Type.NORMAL,0f) }
    var attacksArray  = AttacksArray()


    fun establishAttacks(){
        when(type){
            Type.FIRE->{
                val attack1 = Random.nextInt(0,10)
                var attack2 : Int
                do{
                    attack2 = Random.nextInt(0,10)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.fireAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.fireAttacksArray[attack2]
            }
            Type.DARKNESS->{
                val attack1 = Random.nextInt(0,10)
                var attack2 : Int
                do{
                    attack2 = Random.nextInt(0,10)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.darknessAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.darknessAttacksArray[attack2]
            }
            Type.WATER->{
                val attack1 = Random.nextInt(0,10)
                var attack2 : Int
                do{
                    attack2 = Random.nextInt(0,10)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.waterAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.waterAttacksArray[attack2]
            }
            Type.PLANT->{
                val attack1 = Random.nextInt(0,10)
                var attack2 : Int
                do{
                    attack2 = Random.nextInt(0,10)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.plantAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.plantAttacksArray[attack2]
            }
            Type.GROUND->{
                val attack1 = Random.nextInt(0,10)
                var attack2 : Int
                do{
                    attack2 = Random.nextInt(0,10)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.groundAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.groundAttacksArray[attack2]
            }
        }
        val attack3 = Random.nextInt(0,6)
        var attack4 : Int
        do{
            attack4 = Random.nextInt(0,6)
        }
        while (attack4==attack3)
        ujimonAttacks[2] = attacksArray.normalAttacksArray[attack3]
        ujimonAttacks[3] = attacksArray.normalAttacksArray[attack4]

    }

    fun recieveAttack(attack : Attack) {
        if(attack.type == Type.NORMAL)
            healthPoints -= attack.damage
        else{
            for (tolerance in ujimonTolerance) {
                if (tolerance.type == attack.type)
                    healthPoints -= attack.damage * tolerance.amount
            }
        }

        if(healthPoints <= 0)
            dead = true
    }

}