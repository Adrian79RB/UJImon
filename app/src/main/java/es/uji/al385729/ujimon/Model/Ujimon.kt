package es.uji.al385729.ujimon.Model

import android.graphics.Bitmap
import es.uji.al385729.ujimon.Controller.UjimonController
import java.util.concurrent.locks.Condition
import kotlin.random.Random

class Ujimon(var healthPoints: Float, val name: String, val imageAsset: Bitmap?, val buttonAsset : Bitmap?, var dead: Boolean, val type : Type, var condition : Pair<Int, Type>) {
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
                    healthPoints = Math.round(healthPoints - (attack.damage * tolerance.amount)).toFloat()
            }
        }

        if(healthPoints <= 0) {
            dead = true
            healthPoints = 0f
        }
        if(attack.type!=Type.NORMAL){
            if(condition.second == Type.NORMAL){
                val probability = Random.nextFloat()
                if(probability<0.5){
                    condition = Pair(5, attack.type)
                }
            }
        }

    }

    fun receiveConditionDamage(){
        for (tolerance in ujimonTolerance) {
            if (tolerance.type ==condition.second)
                healthPoints = Math.round(healthPoints - (30 * tolerance.amount)).toFloat()
        }
        if(condition.second!=Type.NORMAL){
            condition = Pair(condition.first-1, condition.second)
            if(condition.first==0)
                condition = Pair(0, Type.NORMAL)
        }
        if(healthPoints <= 0) {
            dead = true
            healthPoints = 0f
        }

    }

    fun attackEffectiveness(attack : Attack): String {
        var message  = ""
        if(attack.type == Type.NORMAL)
            message = ""
        else {
            for (tolerance in ujimonTolerance) {
                if (tolerance.type == attack.type) {
                    if (tolerance.amount > 1f) {
                        message = "It was very effective"
                    } else if (tolerance.amount < 1f) {
                       message = "It wasn't very effective"
                    } else {
                        message = ""
                    }
                }
            }
        }
        return message
    }

    fun healHealthPoints() {
        healthPoints = 1000.0f

        if(dead)
            dead = false
    }

    fun recoverEnergy() {
        for(attack in ujimonAttacks){
            attack.currentAmount = attack.totalAmount
        }
    }

}