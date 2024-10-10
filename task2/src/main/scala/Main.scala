@main def hello(): Unit =
  println("QuadraticEquation X1, X2, and RealSol, when A = 2, B = 1 and C = -1?")
  println(QuadraticEquation(2, 1, -1))	// should print X1 = -1.0, X2 = 1.0, and RealSol = true
  println("QuadraticEquation X1, X2, and RealSol, when A = 2, B = 1 and C = 2?")
  println(QuadraticEquation(2, 1, 2))	// should print X1 = -1.0, X2 = 1.0, and RealSol = false

  
  

// from ass 3 task 1
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