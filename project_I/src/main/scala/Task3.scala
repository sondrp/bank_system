import java.util.concurrent.atomic.AtomicInteger
import scala.io.StdIn

// 3a)
def createThread(f: () => Unit): Thread = {
  new Thread(new Runnable {
    def run(): Unit = f()
  })
}

// Used to showcase Task 3 (when running Main.scala).
object Task3 {

  def apply(letter: String) = letter match {
    case "a" => a()
    case "b" => b()
    case "c" => c()
    case "" =>
      a()
      b()
      c()
    case other => println("Task 3 only has subtasks a, b, and c")
  }

  def a(): Unit = {
    println("a)")

    val f: () => Unit = () => {
      println(
        "Printed using a separate thread. This can be seen often by the fact that this text appears below the dotted line."
      )
    }
    val thread: Thread = createThread(f)
    thread.start()

    println("\n---------------\n") // this is the dotted line
  }

  def b(): Unit = {
    println("b)")
    print("Run the broken code? (y/n): ")
    val input: String = StdIn.readLine.trim.toLowerCase
    if (input.charAt(0) == 'y') {
      ConcurrencyTroublesBroken()
    }

    println("The code is supposed to have two threads alter global variables, and have the main thread print their sum. This sum should not change over time.")
    println("The code is not working as described above. Over time, the total sum will decrease.")
    println("This is due to race conditions. Both threads reading the same variable at the same time. They alter the variable, and write it back.")
    println("Race conditions can cause applications to lose information. This would be terrible for financial software for example.")
    println("\n---------------\n")
  }

  def c(): Unit = {
    println("c)")
    println("Two solutions to the problem: (i) Use semaphores. Make the code section where the read/write operation happen into a critical section. " +
      "Now only one thread can work here at once. This is done with synchronized functions in scala. (ii) Make the integers atomic. " +
      "This will allow the threads to not read dirty data.")

    print("Run the synchronized code? (y/n): ")
    val input1: String = StdIn.readLine.trim.toLowerCase
    if (input1.charAt(0) == 'y') {
      ConcurrencyTroublesFixed1()
    }
    print("Run the atomic code? (y/n): ")
    val input2: String = StdIn.readLine.trim.toLowerCase
    if (input2.charAt(0) == 'y') {
      ConcurrencyTroublesFixed2()
    }
    println("\n---------------\n")
  }
}

// Code from exercise
object ConcurrencyTroublesBroken {
  private var value1: Int = 1000
  private var value2: Int = 0
  private var sum: Int = 0

  def moveOneUnit(): Unit = {
    value1 -= 1
    value2 += 1
    if(value1 == 0) {
      value1 = 1000
      value2 = 0
    }
  }

  def updateSum(): Unit = {
    sum = value1 + value2
  }
  
  def execute(): Unit = {
    while(true) {
      moveOneUnit()
      updateSum()
      Thread.sleep(50)
    }
  }

  def apply(): Unit = {
    for (i <- 1 to 2) {
      val thread = new Thread {
        override def run = execute()
      }
      thread.start()
    }
    
    while(true) {
      updateSum()
      println(sum + " [" + value1 + " " + value2 + "]")
      Thread.sleep(100)
    }
  }
}

object ConcurrencyTroublesFixed1 {
  private var value1: Int = 1000
  private var value2: Int = 0
  private var sum: Int = 0

  def moveOneUnit(): Unit = synchronized {
    value1 -= 1
    value2 += 1
    if (value1 == 0) {
      value1 = 1000
      value2 = 0
    }
    Thread.sleep(50)
  }

  def updateSum(): Unit = synchronized {
    sum = value1 + value2
  }

  def execute(): Unit = {
    while true do {
      moveOneUnit()
      updateSum()
      Thread.sleep(100)
    }
  }

  def apply(): Unit = {
    for (i <- 1 to 2) {
      val thread = new Thread {
        override def run: Unit = execute()
      }
      thread.start()
    }

    while (true) {
      updateSum()
      println(sum + " [" + value1 + " " + value2 + "]")
      Thread.sleep(100)
    }
  }
}

object ConcurrencyTroublesFixed2 {
  private val value1: AtomicInteger = new AtomicInteger(1000)
  private val value2: AtomicInteger = new AtomicInteger(0)
  private val sum: AtomicInteger = new AtomicInteger(0)

  def moveOneUnit(): Unit = {
    println("moving da unit")
    value1.decrementAndGet() // Decrement value1 atomically
    value2.incrementAndGet() // Increment value2 atomically
    if (value1.get() == 0) {
      value1.set(1000)
      value2.set(0)
    }
    Thread.sleep(100)
  }

  def updateSum(): Unit = {
    sum.set(value1.get() + value2.get()) // Update sum atomically
  }

  def execute(): Unit = {
    println("execute start")
    while true do {
      moveOneUnit()
    }
    println("here!")
    updateSum()
    Thread.sleep(100)
  }

  def apply(): Unit = {
    for (i <- 1 to 2) {
      val thread = new Thread {
        override def run: Unit = execute()
      }
      thread.start()
    }

    while (true) {
      Thread.sleep(1000)
      updateSum()
      println(sum.get() + " [" + value1.get() + " " + value2.get() + "]")
    }
  }
}