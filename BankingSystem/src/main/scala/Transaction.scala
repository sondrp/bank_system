import scala.collection.mutable

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionPool {
  private val pool = mutable.Queue[Transaction]()

  def remove(t: Transaction): Boolean = pool.dequeueFirst(_ == t).isDefined
  def isEmpty: Boolean = pool.isEmpty
  def size: Integer = pool.size
  def add(t: Transaction): Boolean = {
    pool.enqueue(t)
    true
  }
  def iterator: Iterator[Transaction] = pool.iterator
}

class Transaction(val from: String, val to: String, val amount: Double, val retries: Int = 3) {
  private var status: TransactionStatus.Value = TransactionStatus.PENDING
  private var attempts = 0

  def getStatus: TransactionStatus.Value = status

  def markSuccess(): Unit = { status = TransactionStatus.SUCCESS }
  def markFailed(): Unit = { status = TransactionStatus.FAILED }
  def markPending(): Unit = { status = TransactionStatus.PENDING }
  def incrementAttempts(): Unit = { attempts += 1 }
  def canRetry: Boolean = attempts < retries
}