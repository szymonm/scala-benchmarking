package pl.s13k.hashing

import scala.util.Random

case class Alert(hash: Int) {
  def matches(m: Metric): Boolean = {
    Thread.sleep(50)
    math.abs(m.dims.hashCode()) % Alerts.hashingPool == hash
  }
}

object Alerts {
  val hashingPool = 5000
  val size = 100

  val random = new Random

  def apply(): Seq[Alert] = (0 to size).map(_ => Alert(random.nextInt(size)))
}
