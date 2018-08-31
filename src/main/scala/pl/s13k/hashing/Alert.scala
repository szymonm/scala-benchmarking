package pl.s13k.hashing

import scala.util.Random

case class Alert(hash: Int) {
  def matches(m: Metric): Boolean = {
    math.sqrt(math.abs(m.dims.hashCode())).toInt % Alerts.hashingPool == hash
  }
}

object Alerts {
  val hashingPool = 500
  val size = 50

  val random = new Random

  def apply(): Seq[Alert] = (0 to size).map(_ => Alert(random.nextInt(size)))
}
