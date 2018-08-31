package pl.s13k.hashing

import org.openjdk.jmh.annotations._

import scala.collection.mutable
import scala.util.Random


@State(Scope.Thread)
class E2ETest {
  import E2ETest._

  var metricsStream: Array[Metric] = _

  val limit = cardinality * 10

  val alerts = Alerts()

  @Setup
  def prepareMetrics: Unit = metricsStream = Stream.continually(gen()).take(limit).toArray

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  def ingest(): Seq[(Metric, Alert)] = {
    val matching = mutable.Buffer[(Metric, Alert)]()

    for {m <- metricsStream} {
      for (a <- alerts) {
        if (a.matches(m)) {
          matching.append((m, a))
        }
      }
    }
    matching
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
