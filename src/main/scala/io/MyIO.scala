package io

import scala.io.StdIn

trait MyIO[A] {
  def map[B](f: A => B): MyIO[B] = ???
  def flatMap[B](f: A => MyIO[B]): MyIO[B] = ???
}

case class MyPrintLine(x: String) extends MyIO[Unit] {
  override def map[B](f: Unit => B): MyIO[B] =
    Result(f())

  override def flatMap[B](f: Unit => MyIO[B]): MyIO[B] =
    f()
}

object MyPrintLine {
  def apply(x: String): MyPrintLine = {
    println(x)
    new MyPrintLine(x)
  }
}

case class MyReadLine(read: String = "") extends MyIO[String] {
  override def map[B](f: String => B): MyIO[B] =
    Result[B](f(read))

  override def flatMap[B](f: String => MyIO[B]): MyIO[B] =
    f(read)
}

object MyReadLine {
  def apply(): MyReadLine = {
    val read = StdIn.readLine()
    new MyReadLine(read)
  }
}

case class Result[A](a: A) extends MyIO[A] {
  override def map[B](f: A => B): MyIO[B] =
    Result[B](f(a))

  override def flatMap[B](f: A => MyIO[B]): MyIO[B] =
    f(a)
}

object Test {
  def test(): Unit = {
    for {
      _     <- MyPrintLine("First name:")
      first <- MyReadLine()
      _     <- MyPrintLine("Last name:")
      last  <- MyReadLine()
      name   = first + " " + last
      _     <- MyPrintLine("Name: " + name)
    } yield ()
  }
}
