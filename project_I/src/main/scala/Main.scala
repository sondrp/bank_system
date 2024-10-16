import scala.io.StdIn

@main def hello(): Unit =
  println(
    "This is a simple CLI to showcase project I solutions.\nWhen promted, please select one of the options:\n\t'all' - to run all tasks.\n\t'exit' - to exit the program. \n\t A digit (1-3) - to run a specific task.\n\t A digit + letter (1-3)(a-d) - to run a specific subtask.\n"
  )
  val task = "([123])(\\w?).*".r

  while (true) {
    print("Input: ")
    val input = StdIn.readLine.trim.toLowerCase

    // hard not to love pattern matching in scala
    input match {
      case task("1", subtask) => Task1(subtask)
      case task("2", subtask) => Task2(subtask)
      case task("3", subtask) => Task3(subtask)
      case "all" =>
        Task1("")
        Task2("")
        Task3("")

      case "exit" =>
        println("Exiting program...")
        return
      case _ =>
        println(
          "Specify task with a number (1-3) followed by an optional letter (a-d)"
        )
        println("Or 'exit' to quit the program")
    }
  }
