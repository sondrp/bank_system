import collection.mutable.Map
import scala.util.Random

class Bank(val allowedAttempts: Integer = 3) {

  private val accountsRegistry: Map[String, Account] = Map()
  val transactionsPool: TransactionPool = new TransactionPool()
  val completedTransactions: TransactionPool = new TransactionPool()

  def processing: Boolean = !transactionsPool.isEmpty

  def transfer(from: String, to: String, amount: Double): Unit = {
    val newTransaction = new Transaction(from, to, amount)
    transactionsPool.add(newTransaction)
  }

  def processTransactions: Unit = {
    Thread.sleep(1000)
    while (processing) {

      val workers = transactionsPool.iterator.toList
        .filter(t => t.getStatus() == TransactionStatus.PENDING)
        .map(processSingleTransaction)

      workers.foreach(_.start)
      workers.foreach(_.join)

      transactionsPool.iterator.toList
        .filter(_.getStatus() == TransactionStatus.SUCCESS)
        .foreach { t =>
          transactionsPool remove t
          completedTransactions add t
        }
      transactionsPool.iterator.toList
        .filter(_.getStatus() == TransactionStatus.FAILED)
        .foreach { t =>
          if (t canContinue) {
            t.setStatus(TransactionStatus.PENDING)
          } else {
            transactionsPool remove t
            completedTransactions add t
          }
        }
    }
  }

  private def processSingleTransaction(t: Transaction): Thread = {
    new Thread(new Runnable {
      override def run(): Unit = {
        t.incrementAttempts()

        if (t.getStatus() != TransactionStatus.PENDING) return

        (for {
          from <- getAccount(t.from)
          to <- getAccount(t.to)
          withdraw <- from.withdraw(t.amount).toOption
          deposit <- to.deposit(t.amount).toOption
        } yield {
          // All values have been extracted successfully
          // Update both accounts
          this.synchronized {
            accountsRegistry.update(withdraw.code, withdraw)
            accountsRegistry.update(deposit.code, deposit)

            t.setStatus(TransactionStatus.SUCCESS)
          }
        }).getOrElse {
          // If any of the Option or Either operations failed, set status to FAILED
          t.setStatus(TransactionStatus.FAILED)
        }
      }
    })
  }

  def createAccount(initialBalance: Double): String = {
    var code = ""
    do {
      code = f"${Random.nextInt(10000)}%04d" // Random 4 digit code
    } while (getAccount(code).isDefined) // Make sure code is unique

    val account = new Account(code, initialBalance)
    accountsRegistry += (code -> account)
    code
  }

  // Get Option[Account] by account code
  def getAccount(code: String) = accountsRegistry get code
}
