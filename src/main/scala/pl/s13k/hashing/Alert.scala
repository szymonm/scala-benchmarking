package pl.s13k.hashing

import scala.util.Random

case class Alert(hash: Int) {
  def matches(m: Metric): Boolean = {
    Alerts.expensiveComputation(m.dims.hashCode()) % Alerts.hashingPool == hash
  }
}

object Alerts {
  // an alert matches a metric with probability 1%
  val hashingPool = 5000
  val size = 50

  val random = new Random

  def apply(): Seq[Alert] = (0 to size).map(_ => Alert(random.nextInt(size)))

  def expensiveComputation(i: Int): Int = {
    val steps = 100
    var res = math.abs(i)
    for (_ <- 1.to(steps)) {
      res = 7 * math.sqrt(res).toInt
    }
    res
  }
}
