package tel.discord.rtab.minigames

import scala.io.StdIn._

object MinigameTest {
  private val games = Map[String, MiniGame] (
    "double" -> new DoubleTrouble(),
    "00"     -> new DoubleZeroes(),
    "bumper" -> new BumperGrab(),
    "shut"   -> new ShutTheBox(),
    "deal"   -> new DealOrNoDeal(),
    "triple" -> new TriplePlay(),
    "coin"   -> new CoinFlip(),
    "sbr"    -> new SuperBonusRound()
  )

  def main(args: Array[String]): Unit = {
    askForNextGame()
  }

  private def askForNextGame(): Unit = {
    println("Minigames available for testing: " + games.keys.mkString(", "))
    print("Choose a game to test (q to quit): ")
    val choice = readLine().toLowerCase
    if(choice == "q") ()
    else games.get(choice) match {
      case None =>
        println ("Game not found")
        askForNextGame()
      case Some(game) =>
        println ("Have a bot play for you? (Y/N)")
        val botChoice = readLine().toLowerCase
        if(botChoice == "y")
          runBotGame(game)
        else
          runGame(game)
        askForNextGame()
    }
  }

  private def runGame(game: MiniGame): Unit = {
    game.initialiseGame().forEach(println)
    while(!game.isGameOver) {
      print("> ")
      game.playNextTurn(readLine()).forEach(println)
    }
    println(s"Game over. Total winnings: ${game.getMoneyWon}")
  }

  private def runBotGame(game:MiniGame): Unit = {
    game.initialiseGame().forEach(println)
    while(!game.isGameOver) {
      val botPick = game.getBotPick
      println("> " + botPick)
      game.playNextTurn(game.getBotPick).forEach(println)
    }
    println(s"Game over. Total winnings: ${game.getMoneyWon}")
  }
}
