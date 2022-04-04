class GameController() {
    private lateinit var playground: Array<Int>
    private var playGroundSize: Int = 9
    private var players = ArrayList<Player>()
    private var numOfPlayers: Int = 2
    private lateinit var posOfPlayers: Array<Int>

    // the main method of the class
    fun play(){
        getInfo()
        startGame()
        endGame()
    }

    private fun startGame() {
        var turnSign = 1

        setPositions()

        while(areAlive()){
            drawPlayground()
            showProperties()
            if (turnSign>0)
                takeTurn(players[0])
            else takeTurn(players[1])
            turnSign *= -1
        }

    }

    private fun takeTurn(player: Player) {
        var actionCode: Int

        println("Turn of ${player.nick}!")
        actionCode = chooseAction(player)
        if (actionCode==Constants.extraAbilityCode) {
            player.useExtraAbility()

            if (player.className == Constants.warriorClass)
                makeDamage(player, player.character.getRadius())

            player.setExtraAbilityState(false)
        } else {
            move(player, actionCode)
            makeDamage(player, player.radius)
        }

    }

    private fun makeDamage(player: Player, radius: Int) {
        val curPosOfPlayer = posOfPlayers[players.indexOf(player)]

        for (cell in playground.indices) {
            if (cell in curPosOfPlayer - radius..curPosOfPlayer + radius) {
                if (cell!=curPosOfPlayer && playground[cell]!=0)
                    players[posOfPlayers.indexOf(cell)].character.hp -= player.damRate
            }
        }

    }

    private fun move(player: Player, actionCode: Int) {
        val movementSpeed = player.movementSpeed
        val curPosOfPlayer = posOfPlayers[players.indexOf(player)]

        // left direction
        if (actionCode == Constants.moveInLeftDirectionCode) {

            if (playground[curPosOfPlayer - movementSpeed] != 0)
                changePosition(player, curPosOfPlayer - movementSpeed - 1)
            else    changePosition(player, curPosOfPlayer - movementSpeed)

        } else if (actionCode == Constants.moveInRightDirectionCode) { // right direction

            if (playground[curPosOfPlayer + movementSpeed] != 0)
                changePosition(player, curPosOfPlayer + movementSpeed + 1)
            else    changePosition(player, curPosOfPlayer + movementSpeed)

        }
    }

    private fun changePosition(player: Player, newPos: Int) {
        playground[posOfPlayers[players.indexOf(player)]] = 0
        playground[newPos] = player.playerID

        posOfPlayers[players.indexOf(player)] = newPos
    }

    private fun chooseAction(player: Player): Int {
        var response: String?
        var actionCode: Int
        var curPosOfPlayer = posOfPlayers[players.indexOf(player)]

        println("Please enter the action number: ")
        println("1 - Move left")
        println("2 - Move right")
        println("3 - Extra ability")


        while (true) {
            response = readLine()

            if (!isOkayToConvertToInt(response)) {
                println(Constants.invalidInputError + Constants.improperNumValueError)
                continue
            } else if (response!!.toInt()<1 || response!!.toInt()>3){
                println(Constants.invalidInputError + Constants.improperNumValueError)
                continue
            } else {
                actionCode = when (response!!.toInt()) {
                    1 -> Constants.moveInLeftDirectionCode
                    2 -> Constants.moveInRightDirectionCode
                    else -> Constants.extraAbilityCode
                }

                if ( (actionCode==Constants.moveInLeftDirectionCode && curPosOfPlayer - player.movementSpeed < 0) ||
                            (actionCode==Constants.moveInRightDirectionCode && curPosOfPlayer + player.movementSpeed >= playGroundSize) )  {
                    println(Constants.invalidInputError + Constants.noCellAvailableError)
                    continue
                } else if ((actionCode==Constants.moveInLeftDirectionCode && playground[curPosOfPlayer - player.movementSpeed] != 0 &&
                                curPosOfPlayer - player.movementSpeed == 0) ||
                            (actionCode==Constants.moveInRightDirectionCode && playground[curPosOfPlayer + player.movementSpeed] != 0 &&
                                    curPosOfPlayer + player.movementSpeed == playGroundSize-1)) {
                        println(Constants.invalidInputError + Constants.noCellAvailableError)
                        continue
                }
            }
            break
        }

        return actionCode
    }

    private fun showProperties() {
        for (player in players) {
            println("${player.nick}: ${player.character.getHealPoints()} HP")
        }
        println()
    }

    private fun endGame() {
        lateinit var winner: Player

        for (player in players){
            if (player.character.hp > 0)
                winner = player
        }
        println("Congratulation to the WINNER ${winner.nick}!")

    }

    private fun setPositions() {
        posOfPlayers = arrayOf(0, playGroundSize-1);

        playground[posOfPlayers[0]] = players[0].playerID
        playground[posOfPlayers[1]] = players[1].playerID
    }

    private fun areAlive(): Boolean {
        for (player in players){
            if (player.character.hp <= 0)
                return false
        }
        return true
    }

    private fun drawPlayground() {
        // first row with playerID-s
        for (pos in playground.indices) {
            if (pos in posOfPlayers)
                print("${playground[pos]}")
            else print(" ")
        }
        println()

        // implementing the second row with range characters
        var isInBothRanges: Boolean
        var isInRangeOfPlayer1: Boolean
        var isInRangeOfPlayer2: Boolean

        for (pos in playground.indices) {
            isInBothRanges = pos in posOfPlayers[0]-players[0].radius..posOfPlayers[0]+players[0].radius &&
                    pos in posOfPlayers[1]-players[1].radius..posOfPlayers[1]+players[1].radius
            isInRangeOfPlayer1 = pos in posOfPlayers[0]-players[0].radius..posOfPlayers[0]+players[0].radius
            isInRangeOfPlayer2 = pos in posOfPlayers[1]-players[1].radius..posOfPlayers[1]+players[1].radius

            if (isInBothRanges) {
                print("*")
            } else if (isInRangeOfPlayer1){
                print("~")
            } else if (isInRangeOfPlayer2){
                print("=")
            } else  print("-")
        }

        println()
    }

    private fun setPlayground() {
        playground = Array<Int>(playGroundSize) { 0 }
    }

    private fun getInfo() {
        getNicksAndClasses()
        getPlaygroundSize()
        setPlayground()
    }

    private fun getPlaygroundSize() {
        val minSize = 9
        var response: String?

        while (true) {
            println("Please enter the size of the playground: (>=$minSize)")
            response = readLine()

            if (!isOkayToConvertToInt(response)) {
                println(Constants.invalidInputError + Constants.improperNumValueError)
            } else if (response!!.toInt()<9) {
                println(Constants.invalidInputError + "Please enter a size which is >=9!")
                continue
            } else {
                playGroundSize = response!!.toInt()
                return
            }
        }
    }

    private fun getNicksAndClasses() {
        var nicksGot = 0
        var nick: String?
        var nicks: ArrayList<String> = ArrayList()

        while (nicksGot<numOfPlayers) {
            println("Please enter a nick name for player №${nicksGot+1}")
            nick = readLine()
            if (nick==null || nick?.length==0) continue

            nicks.add(nick)
            nicksGot++
        }

        var classChosen: String?
        for (nick in nicks) {
            println("Player №${nicks.indexOf(nick)+1}:")
            println("Please choose the class to play:")

            while (true) {
                println("1 - Warrior")
                println("2 - Mage")

                classChosen = readLine()
                if (!isOkayToConvertToInt(classChosen)) {
                    println(Constants.invalidInputError + Constants.improperNumValueError)
                    continue
                }

                when (classChosen!!.toInt()){
                    1 -> players.add(Player(nick, nicks.indexOf(nick)+1, Constants.warriorClass))
                    2 -> players.add(Player(nick, nicks.indexOf(nick)+1, Constants.mageClass))
                    else -> {
                        println(Constants.invalidInputError + Constants.improperNumValueError)
                        continue
                    }
                }
                break
            }
        }
        println()
    }

    private fun isOkayToConvertToInt(input: String?): Boolean {
        try {
            numOfPlayers = input!!.toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }
}