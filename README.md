## Running benchmarks

`mvn install` to compile and install.

`java -jar target/benchmarks.jar -l` to list available benchmarks

Run ingest benchmark with 4 warmup iteratios and 1 fork.
`java -jar target/benchmarks.jar -f 1 -wi 4 pl.s13k.hashing.E2ETest.ingest`
