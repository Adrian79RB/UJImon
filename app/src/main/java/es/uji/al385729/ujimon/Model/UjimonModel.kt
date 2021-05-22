package es.uji.al385729.ujimon.Model

import android.widget.TextView
import es.uji.al385729.ujimon.Assets

class UjimonModel(val playerTrainer : Trainer, val enemyTrainer1 : Trainer, val enemyTrainer2: Trainer, val enemyTrainer3 : Trainer, val soundPlayer : SoundPlayer) {

    interface SoundPlayer{
        fun playIntroMusic()
        fun playBattleMusic()
        fun playHealingMusic()
        fun playVictory()
        fun playDefeated()
        fun playAttackSound()
    }

    enum class UjimonState {
        UJIMON_SELECTION,
        UJIMON_SELECTED,
        PLAYER_TOURN,
        PLAYER_ATTACK,
        PLAYER_CHOOSE_UJIMON,
        COMPUTER_TOURN,
        HEALTH_HEALING,
        END
    }

    var state = UjimonState.UJIMON_SELECTION
    var lastState = UjimonState.UJIMON_SELECTION

    fun changeModelState(newState : UjimonState) {
        lastState = state
        state = newState
    }

    fun initializeAttacks(){
        //Habra que meterlas en array supongo
        //Type fire
        var atkMagmaRiver = Attack("MagmaRiver", Type.FIRE, 400f, 1f);
        var atkDragonBreath = Attack("Dragon breath", Type.FIRE, 300f, 2f)
        var atkFlamethrower = Attack("Flamethrower", Type.FIRE, 200f, 3f)
        var atkBurningFist = Attack("Burning fist", Type.FIRE, 150f, 7f)
        var atkLashOfFire = Attack("Lash of fire", Type.FIRE, 150f, 7f)
        var atkRainOfFire = Attack("Rain of fire", Type.FIRE, 100f, 15f)//Si hacemos estado alterados este podria quemar
        var atkSteam = Attack("Steam", Type.FIRE, 100f, 15f)
        var atkBlaze = Attack("Blaze", Type.FIRE, 100f, 15f);
        var atkFireDart = Attack("Fire dart", Type.FIRE, 75f, 20f);
        var atkSparks = Attack("Sparks", Type.FIRE, 50f, 40f)

        //Type Darknes
        var atkPrayToTheDarkGod = Attack("Pray to the dark god", Type.DARKNESS, 400f, 1f)
        var atkNightmare = Attack("Nightmare", Type.DARKNESS, 300f, 2f)
        var atkSpiritualBeheading = Attack("Spiritual Beheading", Type.DARKNESS, 200f, 3f)
        var atkPainfulIllusion = Attack("Painful Illusion", Type.DARKNESS, 150f, 7f)
        var atkSoulCrusher  = Attack("Soul Crusher", Type.DARKNESS, 150f, 7f)
        var atkCloakOfDarkness = Attack("Cloak of darkness", Type.DARKNESS, 100f, 15f)
        var atkSpectralStabbing = Attack("Spectral stabbing", Type.DARKNESS, 100f, 15f)
        var atkCursedInfection = Attack("Cursed infection", Type.DARKNESS,100f, 15f)
        var atkFear = Attack("Fear", Type.DARKNESS, 75f, 20f)
        var atkCurse : Attack = Attack("Curse", Type.DARKNESS, 50f, 40f)

        //Type Water
        var atkTsunami = Attack("Tsunami", Type.WATER, 400f, 1f)
        var atkStorm = Attack("Storm", Type.WATER, 300f, 2f)
        var atkDrowning = Attack("Drowning", Type.WATER, 200f, 3f)
        var atkWaterPrison = Attack("Water prison", Type.WATER, 150f, 7f)
        var atkWhirlwind = Attack("Whirlwind", Type.WATER, 150f, 7f)
        var atkWatergun = Attack("Watergun", Type.WATER, 100f, 15f)
        var atkDehydrate = Attack("Dehydrate", Type.WATER, 100f, 15f)
        var atkAcidRain = Attack("Acid rain", Type.WATER, 100f, 15f)
        var atkRagingWave = Attack("Raging Wave", Type.WATER, 75f, 20f)
        var atkBubbles = Attack("Bubbles", Type.WATER, 50f, 40f)

        //Type Plant
        var atkSolarRay = Attack("Solar ray", Type.PLANT, 400f, 1f)
        var atkImpalingRoots = Attack("Impaling roots", Type.PLANT, 300f, 2f)
        var atkSharpLeaf = Attack("Sharp Leaf", Type.PLANT, 200f, 3f)
        var atkPoisoning = Attack("Poisoning", Type.PLANT, 150f, 7f)
        var atkSuffocatingUndergrowth = Attack("Suffocating Undergrowth", Type.PLANT, 150f, 7f)
        var atkSeedTurret = Attack("Seed turret", Type.PLANT, 100f, 15f)
        var atkRollingTrunk = Attack("Rolling trunk", Type.PLANT,100f, 15f)
        var atkDrain = Attack("Drain", Type.PLANT, 100f, 15f)
        var atkSpike = Attack("Spike", Type.PLANT, 75f, 20f)
        var atkWhip = Attack("Whip", Type.PLANT, 50f, 40f)

        //Type Ground
        var atkEarthquake = Attack("Earthquake", Type.GROUND, 400f, 1f)
        var atkCrushingRock = Attack("Crushing Rock", Type.GROUND, 300f, 2f)
        var atkQuicksands = Attack("Quicksands", Type.GROUND, 200f, 3f)
        var atkFallingRocks = Attack("Throwing rocks", Type.GROUND, 150f , 7f)
        var atkStoneHammer = Attack("Stone hammer", Type.GROUND, 150f, 7f)
        var atkSandstorm = Attack("Sandstorm", Type.GROUND, 100f, 15f)
        var atkCrack = Attack("Crack", Type.GROUND, 100f, 15f)
        var atkStoneDiscs = Attack("StoneDiscs", Type.GROUND, 100f, 15f)
        var atkCristallizedSand = Attack("Cristallized sand", Type.GROUND, 75f, 20f)
        var atkShards = Attack("Shards", Type.GROUND, 50f, 40f)

        //Type Normal
        var atkTackle = Attack("Tackle", Type.NORMAL, 100f, 15f)
        var atkScratch = Attack("Scratch", Type.NORMAL, 100f, 15f)
        var atkHardKick = Attack("Hard kick", Type.NORMAL, 100f, 15f)
        var atkFist = Attack("Fist", Type.NORMAL, 75f, 20f)
        var atkBite = Attack("Bite", Type.NORMAL, 75f, 20f)
        var atkStomp = Attack("Stomp", Type.NORMAL, 75f, 20f)
    }

    fun initializeUjimon(){
        var maglugInstance : Ujimon = Ujimon(1000f,"Maglug", Assets.maglugAsset, false, Type.FIRE)
        var obshoInstance : Ujimon = Ujimon(1000f, "Obsho", Assets.obshoAsset, false, Type.DARKNESS)
        var redashInstance : Ujimon = Ujimon(1000f, "Redash", Assets.redashAsset, false, Type.PLANT);
        var sworsthInstance : Ujimon = Ujimon(1000f, "Sworsth", Assets.sworsthAsset, false, Type.DARKNESS);
    }
}
