// 1a)
val arrayByLoop = () => {
  val arr = new Array[Int](51)
  for (i <- 0 until 50 + 1) {
    arr(i) = i
  }
  arr
}

// 1b)
val sumOFArray = (arr: Array[Int]) => {
  var sum = 0
  for (i <- 0 until arr.length) {
    sum += arr(i)
  }
  sum
}

// 1c)
val recursiveSumOfArray: Array[Int] => Int = (arr: Array[Int]) =>
  recursiveSumOfList(arr.toList)

// Turn the array to list because it is much more fun
// Match cases are weird in Scala: I don't have to define (or use) the arguments of the function.
// Scala just knows
val recursiveSumOfList: List[Int] => Int = {
  case Nil          => 0
  case head :: tail => head + recursiveSumOfList(tail)
}

// 1d)
val fibonacci: BigInt => BigInt = {
  case 0 => 0
  case 1 => 1
  case x => fibonacci(x - 1) + fibonacci(x - 2)

}

// Used to showcase Task 1 (when running Main.scala).
object Task1 {

  def apply(letter: String) = letter match {
    case "a" => a()
    case "b" => b()
    case "c" => c()
    case "d" => d()
    case "" =>
      a()
      b()
      c()
      d()
    case other => println("Task 1 only has subtasks a-d")
  }

  def a(): Unit = {
    println("a)")
    val arr = arrayByLoop()
    print("Made array of 50 elements: ")
    println(arr mkString " ")
    println("\n---------------\n")
  }

  def b(): Unit = {
    println("b)")
    val arr = (0 to 50).toArray
    val sum = sumOFArray(arr)
    println(s"Sum of array 0 to 50: $sum")
    println("\n---------------\n")
  }

  def c(): Unit = {
    println("c)")
    val arr = (0 to 50).toArray
    print("recursive sum func: ")
    println(recursiveSumOfArray(arr))
    println("\n---------------\n")
  }

  def d(): Unit = {
    println("d)")
    print("fibonacci(1) (should be 1) = ")
    println(fibonacci(1))

    print("fibonacci(2) (should be 1) = ")
    println(fibonacci(2))

    print("fibonacci(3) (should be 2) = ")
    println(fibonacci(3))

    print("fibonacci(10) (should be 55) = ")
    println(fibonacci(10))

    print("fibonacci(20) (should be 6765) = ")
    println(fibonacci(20))

    println(
      "\nThe difference between Int and BigInt is size. Ints are limited to be a 32 bit signed integer. BigInts are unlimited in size, but will have " +
        "some overhead and be slower."
    )
    println("\n---------------\n")
  }

}
