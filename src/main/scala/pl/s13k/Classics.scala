package pl.s13k

import org.openjdk.jmh.annotations._

@State(Scope.Thread)
class Classics {
  var start: java.lang.Long = 0L

  val limit = 100000000

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  def naiveSum(): Long = {
    var sum = start
    for (i <- 0.to(limit)) {
      sum += i
    }
    sum
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  def smarterSum(): Long = {
    var sum: Long = start
    for (i <- 0.to(limit)) {
      sum += i
    }
    sum
  }
}
