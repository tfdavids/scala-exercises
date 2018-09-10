package option

trait MyOption[+A] {
  def map[B](f: A => B): MyOption[B] = ???
  def flatMap[B](f: A => MyOption[B]): MyOption[B] = ???
}

class MySome[A](val x: A) extends MyOption[A] {
  override def map[B](f: A => B): MyOption[B] = {
    new MySome[B](f(x))
  }

  override def flatMap[B](f: A => MyOption[B]): MyOption[B] = {
    f(x)
  }
}

class MyNone[A] extends MyOption[A] {
  override def map[B](f: A => B): MyOption[B] = {
    new MyNone[B]()
  }

  override def flatMap[B](f: A => MyOption[B]): MyOption[B] = {
    new MyNone[B]()
  }
}
