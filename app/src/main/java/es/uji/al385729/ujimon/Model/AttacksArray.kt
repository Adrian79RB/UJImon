package es.uji.al385729.ujimon.Model

class AttacksArray() {
    public val fireAttacksArray : Array<Attack> = Array<Attack>(10){
        Attack("MagmaRiver", Type.FIRE, 400f, 1f)
        Attack("Dragon breath", Type.FIRE, 300f, 2f)
        Attack("MagmaRiver", Type.FIRE, 400f, 1f)
        Attack("Dragon breath", Type.FIRE, 300f, 2f)
        Attack("Flamethrower", Type.FIRE, 200f, 3f)
        Attack("Burning fist", Type.FIRE, 150f, 7f)
        Attack("Lash of fire", Type.FIRE, 150f, 7f)
        Attack("Rain of fire", Type.FIRE, 100f, 15f)
        Attack("Steam", Type.FIRE, 100f, 15f)
        Attack("Blaze", Type.FIRE, 100f, 15f)
        Attack("Fire dart", Type.FIRE, 75f, 20f)
        Attack("Sparks", Type.FIRE, 50f, 40f)
    }


    public val darknessAttacksArray : Array<Attack> = Array<Attack>(10){
        Attack("Pray to the dark god", Type.DARKNESS, 400f, 1f)
        Attack("Nightmare", Type.DARKNESS, 300f, 2f)
        Attack("Spiritual Beheading", Type.DARKNESS, 200f, 3f)
        Attack("Painful Illusion", Type.DARKNESS, 150f, 7f)
        Attack("Soul Crusher", Type.DARKNESS, 150f, 7f)
        Attack("Cloak of darkness", Type.DARKNESS, 100f, 15f)
        Attack("Spectral stabbing", Type.DARKNESS, 100f, 15f)
        Attack("Cursed infection", Type.DARKNESS,100f, 15f)
        Attack("Fear", Type.DARKNESS, 75f, 20f)
        Attack("Curse", Type.DARKNESS, 50f, 40f )
    }

    public val waterAttacksArray : Array<Attack> = Array<Attack>(10) {
        Attack("Tsunami", Type.WATER, 400f, 1f)
        Attack("Storm", Type.WATER, 300f, 2f)
        Attack("Drowning", Type.WATER, 200f, 3f)
        Attack("Water prison", Type.WATER, 150f, 7f)
        Attack("Whirlwind", Type.WATER, 150f, 7f)
        Attack("Watergun", Type.WATER, 100f, 15f)
        Attack("Dehydrate", Type.WATER, 100f, 15f)
        Attack("Acid rain", Type.WATER, 100f, 15f)
        Attack("Raging Wave", Type.WATER, 75f, 20f)
        Attack("Bubbles", Type.WATER, 50f, 40f)
    }

    public val plantAttacksArray : Array<Attack> = Array<Attack>(10) {
        Attack("Solar ray", Type.PLANT, 400f, 1f)
        Attack("Impaling roots", Type.PLANT, 300f, 2f)
        Attack("Sharp Leaf", Type.PLANT, 200f, 3f)
        Attack("Poisoning", Type.PLANT, 150f, 7f)
        Attack("Suffocating Undergrowth", Type.PLANT, 150f, 7f)
        Attack("Seed turret", Type.PLANT, 100f, 15f)
        Attack("Rolling trunk", Type.PLANT,100f, 15f)
        Attack("Drain", Type.PLANT, 100f, 15f)
        Attack("Spike", Type.PLANT, 75f, 20f)
        Attack("Whip", Type.PLANT, 50f, 40f)
    }

    public val groundAttacksArray : Array<Attack> = Array<Attack>(10) {
        Attack("Earthquake", Type.GROUND, 400f, 1f)
        Attack("Crushing Rock", Type.GROUND, 300f, 2f)
        Attack("Quicksands", Type.GROUND, 200f, 3f)
        Attack("Throwing rocks", Type.GROUND, 150f , 7f)
        Attack("Stone hammer", Type.GROUND, 150f, 7f)
        Attack("Sandstorm", Type.GROUND, 100f, 15f)
        Attack("Crack", Type.GROUND, 100f, 15f)
        Attack("StoneDiscs", Type.GROUND, 100f, 15f)
        Attack("Cristallized sand", Type.GROUND, 75f, 20f)
        Attack("Shards", Type.GROUND, 50f, 40f)
    }

    public val normalAttacksArray : Array<Attack> = Array<Attack>(10) {
        Attack("Tackle", Type.NORMAL, 100f, 15f)
        Attack("Scratch", Type.NORMAL, 100f, 15f)
        Attack("Hard kick", Type.NORMAL, 100f, 15f)
        Attack("Fist", Type.NORMAL, 75f, 20f)
        Attack("Bite", Type.NORMAL, 75f, 20f)
        Attack("Stomp", Type.NORMAL, 75f, 20f)
    }
}