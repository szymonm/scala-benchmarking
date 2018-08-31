package pl.s13k

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

@State(Scope.Thread)
class Min {

  var a: Array[Int] = _

  @Setup
  def prepare: Unit = {a = Array.tabulate(10000)(n => n % 1001)}

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  def hipsterMin(): Int = {
    a.foldLeft(Int.MaxValue) { _.min(_) }
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  def oldschoolMin(): Int = {
    var min = Int.MaxValue
    var i = 0

    while (i < a.length) {
      min = if (a(i) < min) { a(i) } else { min }
      i += 1
    }
    min
  }
}
