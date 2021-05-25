package es.uji.al385729.ujimon.Model

import android.graphics.Bitmap
import kotlin.random.Random

class Ujimon(var healthPoints: Float, val name: String, val imageAsset: Bitmap?, var dead: Boolean, val type : Type) {
    var ujimonAttacks : Array<Attack> = Array<Attack>(4){ Attack ("", Type.NORMAL, 0f, 0f) }
    var ujimonTolerance : Array<Tolerance> = Array<Tolerance>(5){ Tolerance(Type.NORMAL,0f) }
    var attacksArray  = AttacksArray()


    fun establishAttacks(){
        when(type){
            Type.FIRE->{
                var attack1 = Random.nextInt(0,9)
                var attack2 : Int;
                do{
                    attack2 = Random.nextInt(0,9)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.fireAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.fireAttacksArray[attack2]
            }
            Type.DARKNESS->{
                var attack1 = Random.nextInt(0,9)
                var attack2 : Int;
                do{
                    attack2 = Random.nextInt(0,9)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.darknessAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.darknessAttacksArray[attack2]
            }
            Type.WATER->{
                var attack1 = Random.nextInt(0,9)
                var attack2 : Int;
                do{
                    attack2 = Random.nextInt(0,9)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.waterAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.waterAttacksArray[attack2]
            }
            Type.PLANT->{
                var attack1 = Random.nextInt(0,9)
                var attack2 : Int;
                do{
                    attack2 = Random.nextInt(0,9)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.plantAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.plantAttacksArray[attack2]
            }
            Type.GROUND->{
                var attack1 = Random.nextInt(0,9)
                var attack2 : Int;
                do{
                    attack2 = Random.nextInt(0,9)
                }
                while (attack2 == attack1)
                ujimonAttacks[0] = attacksArray.groundAttacksArray[attack1]
                ujimonAttacks[1] = attacksArray.groundAttacksArray[attack2]
            }
        }
        var attack3 = Random.nextInt(0,5)
        var attack4 : Int;
        do{
            attack4 = Random.nextInt(0,5)
        }
        while (attack4==attack3)
        ujimonAttacks[2] = attacksArray.normalAttacksArray[attack3]
        ujimonAttacks[3] = attacksArray.normalAttacksArray[attack4]

    }

}