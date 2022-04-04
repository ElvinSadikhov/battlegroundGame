class Player(val nick: String, val playerID: Int, val className: String) {
    var character: Character

    init {
        if (className == Constants.warriorClass)
            character = Warrior()
        else
            character = Mage()
    }

    var radius: Int = character.getRadius()
    var movementSpeed: Int = character.getSpeed()
    var damRate: Int = character.getDamRate()


    private fun setCharacter(characterName: String) {
        character = if (characterName==Constants.warriorClass) Warrior() else Mage()
    }

    fun setExtraAbilityState(value: Boolean) {
        character.setExtraAbilityState(value)
    }

    fun useExtraAbility() {
        character.extraAbility()
    }

}
