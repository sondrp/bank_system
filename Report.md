# TDT4165 - Programming Languages

Date: 18/10/2024  
Authors: Emiel Eij and Sondre Pedersen

<div style="page-break-after: always"></div>

## Running the Code

To run the code, navigate to the folder `project_I/` and run the following command in your terminal: `sbt run`. Then, type the command `all` to run all the code that is delivered for this assignment.

## Task 1: Scala Introduction

***Q. Use BigInt instead of Int. What is the difference between these two data types?***

A. The difference between `Int` and `BigInt` is size. `Int` is limited to a 32-bit signed integer, while `BigInt` is unlimited in size, but it comes with some overhead and is slower in comparison.

## Task 2: Higher-Order Programming in Scala

***Q. Refer to Assignment 3 of this course, which is about Higher-Order Programming. Implement Task 1 and Task 4 of that assignment in Scala. Which differences did you find, if any?***

A. For the `QuadraticEquation`, the Oz procedure uses output arguments `(?RealSol, ?X1, ?X2)`, while the Scala version returns a tuple containing `(Boolean, Option[Double], Option[Double])`.

For the `RightFold` functions, Scala offers an operator for lists where you can pass the parameters like this:  
`array.foldRight(0)((x, y) => x + y)` and `array.foldRight(0)((x, y) => 1 + y)`.

In Oz, you need to manually implement the `foldRight` functionality.

## Task 3: Concurrency in Scala

***Q. What is this code supposed to do?***  
A. The code is supposed to have two threads modify global variables, and the main thread prints their sum. This sum should not change over time.

***Q. Is it working as expected?***  
A. The code is not working as described above. Over time, the total sum will decrease.

***Q. What do you think is happening?***  
A. This is due to race conditions. Both threads read the same variable at the same time, modify it, and then write it back, resulting in inconsistent behavior.

***Q. Would it be possible to experience the same behavior in Oz? Why or why not?***  
A. Yes, when mutable state (such as cells) is shared between threads, a race condition can occur in Oz as well.

***Q. Give an example of how this behavior could impact a real application.***  
A. Race conditions can cause applications to lose or corrupt data. For example, in financial software, race conditions could lead to incorrect calculations, potentially causing financial losses.

***Q. Modify the code so that it is thread-safe. You can do it in different ways in Scala. Can you find at least two different ways to fix the above code?***

A. Two ways to fix the code:
1. By using atomic variables.
2. By synchronizing the threads to control access to the shared variable.