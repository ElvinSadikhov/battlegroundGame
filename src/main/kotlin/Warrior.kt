class Warrior: Character() {
    override var damageRate = 5
    override var movementsSpeed = 4
    override var damageRadius = 2
    override var extraAbilityState = false
    override var hp = 25


    // warrior's special ability is an ability to damage opponent in radius*2 distance (one time)
    override fun extraAbility() {
        extraAbilityState = true
        damageRadius *= 2
    }

}