// from assignment 3 task 1
// proc {QuadraticEquation A B C ?RealSol ?X1 ?X2}. X1 and X2 should bind to the real
// solution(s) to the quadratic equation. RealSol binds to true if there exists a real solution, false otherwise.
def QuadraticEquation(A: Double, B: Double, C: Double): (Boolean, Option[Double], Option[Double]) = {
  val D: Double = B * B - 4 * A * C

  if (D > 0) {
    val X1: Double = (-B + Math.sqrt(D)) / (2 * A)
    val X2: Double = (-B - Math.sqrt(D)) / (2 * A)
    (true, Some(X1), Some(X2))  // Real solutions
  } else if (D == 0) {
    val X1: Double = -B / (2 * A)
    (true, Some(X1), Some(X1))  // One real solution
  } else {
    (false, None, None)  // No real solutions
  }
}

// From assignment 3 task 
// Write a function fun {RightFold List Op U}, which goes through a list recursively and, through the use
// of a combining operation Op, accumulates and returns a result. U is the neutral elements for the operation.
def sumOFArray2(arr: Array[Int]): Int = {
  arr.foldRight(0)((x, y) => x + y)
}

def lengthOFArray(arr: Array[Int]): Int = {
  arr.foldRight(0)((x, y) => 1 + y)
}

// Used to showcase Task 2 (when running Main.scala).
object Task2 {

  def apply(letter: String) = letter match {
    case "a" => a()
    case "b" => b()
    case "" =>
      a()
      b()
    case other => println("Task 2 only has subtasks a and b")
  }

  def a(): Unit = {
    println("a)")
    println("QuadraticEquation X1, X2, and RealSol, when A = 2, B = 1 and C = -1?")
    println(QuadraticEquation(2, 1, -1))	// should print X1 = -1.0, X2 = 1.0, and RealSol = true
    println("QuadraticEquation X1, X2, and RealSol, when A = 2, B = 1 and C = 2?")
    println(QuadraticEquation(2, 1, 2))	// should print X1 = -1.0, X2 = 1.0, and RealSol = false

    var array = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(s"Array: ${array.mkString(" ")}")
    println("Sum of Array: " + sumOFArray2(array))
    println("Length of Array: " + lengthOFArray(array))
    println("\n---------------\n")
  }
  
  def b(): Unit = {
    println("b)")
    println("Scala is a lot easier to write than oz. It has built in functions, type inference, and most importantly more familiar.")
    println("\n---------------\n")
  }
}