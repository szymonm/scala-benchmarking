package pl.s13k.consume

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Thread)
class Consumer {
  import Consumer._

  @Param(Array("10000000"))
  var size: Int = _

  var timestamps: Array[Long] = _
  var values: Array[Double] = _

  @Setup
  def setup(): Unit = {
    val rnd = new scala.util.Random(0L)
    timestamps = Array.fill(size)(rnd.nextInt())
    values = Array.fill(size)(rnd.nextDouble)
  }

  @Benchmark
  def iteratorBased(bh: Blackhole): Unit = {
    val it = iterator(timestamps, values)
    it.foreach {
      p => bh.consume(p)
    }
  }

  @Benchmark
  def consumerBased(bh: Blackhole): Unit = {
    producer(timestamps, values).consume(
      (ts, v) => {
        bh.consume(ts)
        bh.consume(v)
      }
    )

  }
}

object Consumer {
  case class Point(timestamp: Long, value: Double)

  def iterator(timestamps: Array[Long], values: Array[Double]): Iterator[Point] = {
    new Iterator[Point] {
      private var i = -1

      def hasNext = i < timestamps.length - 1
      def next(): Point = {
        i += 1
        Point(timestamps(i), values(i))
      }
    }
  }

  trait Producer {
    def consume(f: (Long, Double) => Unit): Unit
  }

  def producer(timestamps: Array[Long], values: Array[Double]): Producer = {
    new Producer {
      def consume(f: (Long, Double) => Unit): Unit = {
        var i = -1
        while (i < timestamps.length - 1) {
          i += 1
          f(timestamps(i), values(i))
        }
      }
    }
  }

}
