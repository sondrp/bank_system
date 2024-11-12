
class Account(val code : String, val balance: Double) {
    
  def withdraw(amount: Double): Either[String, Account] = {
    if (amount <= 0) Left("Amount must be positive")
    else if (amount > balance) Left("Insufficient funds")
    else Right(new Account(code, balance - amount))
  }

  def deposit(amount: Double): Either[String, Account] = {
    if (amount <= 0) Left("Amount must be positive")
    else Right(new Account(code, balance + amount))
  }
}
