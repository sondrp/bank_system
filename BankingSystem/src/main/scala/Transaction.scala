import java.util.concurrent.atomic.AtomicReference
import scala.collection.mutable.ArrayBuffer
object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionPool {
  var transactions: AtomicReference[ArrayBuffer[Transaction]] =
    new AtomicReference[ArrayBuffer[Transaction]](
      ArrayBuffer.empty[Transaction]
    )

  // Remove and the transaction from the pool
  def remove(t: Transaction) = {
    this.synchronized {
      val prevSize = size
      transactions.get() -= t
      val newSize = size
      prevSize - newSize == 1
    }
  }

  def isEmpty = transactions.get() isEmpty

  // Check pool size
  def size = transactions.get() size

  def add(t: Transaction): Boolean = {
    this.synchronized {
      val prevSize = size
      transactions.get() += t
      val newSize = size
      newSize - prevSize == 1
    }
  }

  def iterator = transactions.get() iterator
}

class Transaction(
    val from: String,
    val to: String,
    val amount: Double,
    val retries: Int = 3
) {

  private var status: TransactionStatus.Value = TransactionStatus.PENDING
  private var attempts = 0

  def getStatus() = status

  def setStatus(newStatus: TransactionStatus.Value) = {
    status = newStatus
  }

  def incrementAttempts() = {
    attempts = attempts + 1
  }

  def canContinue(): Boolean = {
    attempts > 15
  }
}
