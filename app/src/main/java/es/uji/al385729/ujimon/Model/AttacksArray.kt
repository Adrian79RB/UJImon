package es.uji.al385729.ujimon.Model

class AttacksArray() {
    val fireAttacksArray : Array<Attack> = Array<Attack>(10){ Attack("", Type.FIRE, 0f, 0f, 0f) }
    val darknessAttacksArray : Array<Attack> = Array<Attack>(10){ Attack("", Type.DARKNESS, 0f, 0f, 0f) }
    val waterAttacksArray : Array<Attack> = Array<Attack>(10) { Attack("", Type.WATER, 0f, 0f, 0f) }
    val plantAttacksArray : Array<Attack> = Array<Attack>(10) { Attack("", Type.PLANT, 0f, 0f, 0f) }
    val groundAttacksArray : Array<Attack> = Array<Attack>(10) { Attack("", Type.GROUND, 0f, 0f, 0f) }
    val normalAttacksArray : Array<Attack> = Array<Attack>(10) { Attack("", Type.NORMAL, 0f, 0f,0f) }
    init {
        fireAttacksArray[0] = Attack("MagmaRiver", Type.FIRE, 400f, 1f, 1f)
        fireAttacksArray[1] = Attack("Dragon breath", Type.FIRE, 300f, 2f, 2f)
        fireAttacksArray[2] = Attack("Flamethrower", Type.FIRE, 200f, 3f, 3f)
        fireAttacksArray[3] = Attack("Burning fist", Type.FIRE, 150f, 7f, 7f)
        fireAttacksArray[4] = Attack("Lash of fire", Type.FIRE, 150f, 7f, 7f)
        fireAttacksArray[5] = Attack("Rain of fire", Type.FIRE, 100f, 15f, 15f)
        fireAttacksArray[6] = Attack("Steam", Type.FIRE, 100f, 15f, 15f)
        fireAttacksArray[7] = Attack("Blaze", Type.FIRE, 100f, 15f, 15f)
        fireAttacksArray[8] = Attack("Fire dart", Type.FIRE, 75f, 20f, 20f)
        fireAttacksArray[9] = Attack("Sparks", Type.FIRE, 50f, 40f, 40f)

        darknessAttacksArray[0] =  Attack("Pray to the dark god", Type.DARKNESS, 400f, 1f,1f)
        darknessAttacksArray[1] =  Attack("Nightmare", Type.DARKNESS, 300f, 2f, 2f)
        darknessAttacksArray[2] =  Attack("Spiritual Beheading", Type.DARKNESS, 200f, 3f, 3f)
        darknessAttacksArray[3] =  Attack("Painful Illusion", Type.DARKNESS, 150f, 7f,7f)
        darknessAttacksArray[4] =  Attack("Soul Crusher", Type.DARKNESS, 150f, 7f, 7f)
        darknessAttacksArray[5] =  Attack("Cloak of darkness", Type.DARKNESS, 100f, 15f, 15f)
        darknessAttacksArray[6] =  Attack("Spectral stabbing", Type.DARKNESS, 100f, 15f, 15f)
        darknessAttacksArray[7] =  Attack("Cursed infection", Type.DARKNESS,100f, 15f, 15f)
        darknessAttacksArray[8] =  Attack("Fear", Type.DARKNESS, 75f, 20f, 20f)
        darknessAttacksArray[9] =  Attack("Curse", Type.DARKNESS, 50f, 40f, 40f )

        waterAttacksArray[0] = Attack("Tsunami", Type.WATER, 400f, 1f, 1f)
        waterAttacksArray[1] = Attack("Storm", Type.WATER, 300f, 2f, 2f)
        waterAttacksArray[2] = Attack("Drowning", Type.WATER, 200f, 3f, 3f)
        waterAttacksArray[3] = Attack("Water prison", Type.WATER, 150f, 7f, 7f)
        waterAttacksArray[4] = Attack("Whirlwind", Type.WATER, 150f, 7f, 7f)
        waterAttacksArray[5] = Attack("Watergun", Type.WATER, 100f, 15f, 15f)
        waterAttacksArray[6] = Attack("Dehydrate", Type.WATER, 100f, 15f, 15f)
        waterAttacksArray[7] = Attack("Acid rain", Type.WATER, 100f, 15f, 15f)
        waterAttacksArray[8] = Attack("Raging Wave", Type.WATER, 75f, 20f, 20f)
        waterAttacksArray[9] = Attack("Bubbles", Type.WATER, 50f, 40f, 40f)

        plantAttacksArray[0] = Attack("Solar ray", Type.PLANT, 400f, 1f, 1f)
        plantAttacksArray[1] = Attack("Impaling roots", Type.PLANT, 300f, 2f, 2f)
        plantAttacksArray[2] = Attack("Sharp Leaf", Type.PLANT, 200f, 3f, 3f)
        plantAttacksArray[3] = Attack("Poisoning", Type.PLANT, 150f, 7f, 7f)
        plantAttacksArray[4] = Attack("Suffocating Undergrowth", Type.PLANT, 150f, 7f, 7f)
        plantAttacksArray[5] = Attack("Seed turret", Type.PLANT, 100f, 15f, 15f)
        plantAttacksArray[6] = Attack("Rolling trunk", Type.PLANT,100f, 15f, 15f)
        plantAttacksArray[7] = Attack("Drain", Type.PLANT, 100f, 15f, 15f)
        plantAttacksArray[8] = Attack("Spike", Type.PLANT, 75f, 20f, 20f)
        plantAttacksArray[9] = Attack("Whip", Type.PLANT, 50f, 40f, 40f)

        groundAttacksArray[0] = Attack("Earthquake", Type.GROUND, 400f, 1f, 1f)
        groundAttacksArray[1] = Attack("Crushing Rock", Type.GROUND, 300f, 2f, 2f)
        groundAttacksArray[2] = Attack("Quicksands", Type.GROUND, 200f, 3f, 3f)
        groundAttacksArray[3] = Attack("Throwing rocks", Type.GROUND, 150f , 7f, 7f)
        groundAttacksArray[4] = Attack("Stone hammer", Type.GROUND, 150f, 7f, 7f)
        groundAttacksArray[5] = Attack("Sandstorm", Type.GROUND, 100f, 15f, 15f)
        groundAttacksArray[6] = Attack("Crack", Type.GROUND, 100f, 15f, 15f)
        groundAttacksArray[7] = Attack("StoneDiscs", Type.GROUND, 100f, 15f, 15f)
        groundAttacksArray[8] = Attack("Cristallized sand", Type.GROUND, 75f, 20f, 20f)
        groundAttacksArray[9] = Attack("Shards", Type.GROUND, 50f, 40f, 40f)

        normalAttacksArray[0] = Attack("Tackle", Type.NORMAL, 100f, 15f, 15f)
        normalAttacksArray[1] = Attack("Scratch", Type.NORMAL, 100f, 15f, 15f)
        normalAttacksArray[2] = Attack("Hard kick", Type.NORMAL, 100f, 15f, 15f)
        normalAttacksArray[3] = Attack("Fist", Type.NORMAL, 75f, 20f, 20f)
        normalAttacksArray[4] = Attack("Bite", Type.NORMAL, 75f, 20f, 20f)
        normalAttacksArray[5] = Attack("Stomp", Type.NORMAL, 75f, 20f, 20f)
    }
}