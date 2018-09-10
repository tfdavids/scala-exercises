package option

import org.scalatest._
import org.scalactic.Equality

class MyOptionSpec extends FlatSpec with Matchers {
  "The MySome object" should "map to a MySome object" in {
    new MySome[Int](42).map(x => x/2).asInstanceOf[MySome[Int]].x shouldEqual new MySome[Int](21).x
  }

  "The MyNone object" should "map to a MyNone object" in {
    new MyNone[Int].map(x => x/2) shouldBe a [MyNone[_]]
  }

  "The MySome object" should "flatMap to the result of the function" in {
    new MySome[Int](42).flatMap(x => new MySome(x/2)).asInstanceOf[MySome[Int]].x shouldEqual new MySome[Int](21).x
    new MySome[Int](42).flatMap(x => new MyNone[Int]()) shouldBe a [MyNone[_]]
  }

  "The MyNone object" should "flatMap to a MyNone object" in {
    new MyNone[Int]().flatMap(x => new MySome(x/2)) shouldBe a [MyNone[_]]
    new MyNone[Int]().flatMap(x => new MyNone[Int]()) shouldBe a [MyNone[_]]
  }
}
