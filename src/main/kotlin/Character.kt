abstract class Character {

    abstract var hp: Int

    abstract val damageRate: Int

    abstract val movementsSpeed: Int

    abstract var damageRadius: Int

    abstract var extraAbilityState: Boolean

    abstract fun extraAbility()

    fun getRadius(): Int {
        return damageRadius
    }

    fun getSpeed(): Int {
        return movementsSpeed
    }

    fun getDamRate(): Int {
        return damageRate
    }

    @JvmName("getExtraAbilityState1")
    fun getExtraAbilityState(): Boolean {
        return extraAbilityState
    }

    @JvmName("setExtraAbilityState1")
    fun setExtraAbilityState(value: Boolean) {
        if (value==false) {
            damageRadius /= 2
        }
        extraAbilityState = value
    }

    fun getHealPoints(): Int {
        return hp
    }

}