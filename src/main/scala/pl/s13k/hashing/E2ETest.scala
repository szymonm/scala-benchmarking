package pl.s13k.hashing

import org.openjdk.jmh.annotations._

import scala.collection.mutable
import scala.util.Random


@State(Scope.Thread)
class E2ETest {
  import E2ETest._

  var metricsStream: Array[Metric] = _
  var i: Int = _

  val datapoints = cardinality * 100

  val alerts = Alerts()

  @Setup
  def prepareMetrics: Unit = {
    metricsStream = Stream.continually(gen()).take(datapoints).toArray
    i = 0
  }

  def nextMetric(): Metric = {
    i += 1
    metricsStream(i % metricsStream.length)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def ingest(): Seq[Alert] = {
    val m = nextMetric()
    val matching = mutable.Buffer[Alert]()

    for (a <- alerts) {
      if (a.matches(m)) {
        matching.append(a)
      }
    }
    matching.toSeq
  }
}

object E2ETest {
  type MetricGenerator = () => Metric

  val cardinality = 50

  val gen = new MetricGenerator {
    override def apply(): Metric = {
      val host = Random.nextInt(cardinality)
      Metric(
        Map("dep" -> "prod", "instance" -> s"prod-katta-${host}", "katta.type" -> "hot",
          "metric" -> "cpu")
      )
    }
  }
}
