@main def hello(): Unit =
  println("Creating a array and printing this")
  // val a1: Array[Int]
  var arr = new Array[Int](51)
  for (i <- 0 until 50+1) {
    arr(i) = i
  }
  println(arr.mkString(", "))
  println("sum of array func: ")
  println(sumOFArray(arr))

  println("recursive sum func: ")
  println(recursiveSumOfArray(arr))


  println("fibonacci func of the 1 st  = (should be 1)")
  println(fibonacci(1))

  println("fibonacci func of the 2 nd  (should be 1)= ")
  println(fibonacci(2))

  println("fibonacci func of the 3 th  (should be 2)= ")
  println(fibonacci(3))

  println("fibonacci func of the 10 th (should be 55) = ")
  println(fibonacci(10))

  println("fibonacci func of the 20 th (should be 6765) = ")
  println(fibonacci(20))

// val a1: Array[Int](50)
def sumOFArray(arr: Array[Int]): Int = {
  var sum = 0
  for (i <- 0 until arr.length) {
    sum += arr(i)
  }
  sum
}

def recursiveSumOfArray(arr: Array[Int]): Int = {
  if (arr.length == 1) {
    arr(0)
  } else {
    arr(0) + recursiveSumOfArray(arr.slice(1, arr.length))
  }
}

def fibonacci(n: Int): BigInt = {
  if (n == 0) {
    0
  }
  else if(n == 1) {
    1
  }
  else {
    // fibonacci(n - 1) + fibonacci(n - 2)
    fibonacci(n - 1) + fibonacci(n - 2)
  }
}