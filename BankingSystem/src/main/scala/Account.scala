import scala.util.Random
class Account(val code: String, val balance: Double) {

  def withdraw(amount: Double): Either[String, Account] =
    if (amount < 0)
      Left("Attempted to withdraw a negative number")
    else if (balance < amount)
      Left("Attempted to withdraw more than available funds.")
    else
      Right(new Account(code, balance - amount))

  def deposit(amount: Double): Either[String, Account] = 
    if (amount < 0) 
        Left("Attempted to deposit a negative number")
    else
        Right(new Account(code, balance + amount))
}