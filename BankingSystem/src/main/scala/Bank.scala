import scala.collection.mutable.Map

class Bank(val allowedAttempts: Int = 3) {
  private val accountsRegistry: Map[String, Account] = Map()
  val transactionsPool: TransactionPool = new TransactionPool()
  val completedTransactions: TransactionPool = new TransactionPool()

  def processing: Boolean = !transactionsPool.isEmpty

  def transfer(from: String, to: String, amount: Double): Unit = {
    transactionsPool.add(new Transaction(from, to, amount))
  }

  def processTransactions: Unit = {
    val workers = transactionsPool.iterator
      .filter(_.getStatus == TransactionStatus.PENDING)
      .map(processSingleTransaction)
      .toList

    workers.foreach(_.join())

    val succeeded = transactionsPool.iterator.filter(_.getStatus == TransactionStatus.SUCCESS).toList
    val failed = transactionsPool.iterator.filter(_.getStatus == TransactionStatus.FAILED).toList

    succeeded.foreach { t =>
      transactionsPool.remove(t)
      completedTransactions.add(t)
    }

    failed.foreach { t =>
      if (t.canRetry) {
        t.markPending()
      } else {
        transactionsPool.remove(t)
        completedTransactions.add(t)
      }
    }

    if (!transactionsPool.isEmpty) processTransactions
  }

  private def processSingleTransaction(t: Transaction): Thread = Main.thread {
    (accountsRegistry.get(t.from), accountsRegistry.get(t.to)) match {
      case (Some(fromAccount: Account), Some(toAccount: Account)) if t.amount > 0 =>
        fromAccount.withdraw(t.amount) match {
          case Right(updatedFrom) =>
            toAccount.deposit(t.amount) match {
              case Right(updatedTo) =>
                accountsRegistry.update(t.from, updatedFrom)
                accountsRegistry.update(t.to, updatedTo)
                t.markSuccess()
              case Left(_) => t.markFailed()
            }
          case Left(_) => t.markFailed()
        }
      case _ => t.markFailed()
    }
  }

  def createAccount(initialBalance: Double): String = {
    val code = java.util.UUID.randomUUID().toString
    accountsRegistry(code) = new Account(code, initialBalance)
    code
  }

  def getAccount(code: String): Option[Account] = accountsRegistry.get(code)
}
