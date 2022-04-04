class Mage: Character() {
    override var damageRate = 8
    override var movementsSpeed = 2
    override var damageRadius = 4
    override var extraAbilityState = false
    override var hp = 25

    // mage's special ability is healing himself by 5hp
    override fun extraAbility() {
        if (hp<95) hp += 5 // !!! can be changed later
        else hp = 100
        extraAbilityState = true
    }


}