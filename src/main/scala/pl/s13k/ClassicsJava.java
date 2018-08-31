package pl.s13k;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class ClassicsJava {
    Long start  = 0L;

    int limit = 1000000;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public long javaSum(){
        Long sum = start;
        for (int i = 0; i < limit;
             i++){
            sum += i;
        }
        return sum;
    }
}