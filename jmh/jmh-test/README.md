# jmh-test (JAVA Microbenchmark Suite - Jackson deserialization)

Small Project used to measure the time jackson needs to deserialize a json string into a
JAVA object using different approaches.
The test is located in class [MyBenchmark](src/main/java/org/sample/MyBenchmark.java).

To find as accurate measure results as possible, i used the [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh).

## Build the project
```
mvn clean verify
```

## Run the benchmark
```
java -jar target/benchmarks.jar
```

## Result on my machine (MacBook i5)
```
Benchmark                                               Mode  Cnt  Score   Error  Units
MyBenchmark.measure_readTree                            avgt    4  2,422 ± 0,758  us/op
MyBenchmark.measure_readValue_ObjectReader_Map          avgt    4  2,152 ± 1,037  us/op  <-- fastest
MyBenchmark.measure_readValue_TypeReference_Map         avgt    4  2,322 ± 0,826  us/op
MyBenchmark.measure_readValue_class_JavaMap             avgt    4  2,371 ± 0,334  us/op
MyBenchmark.measure_readValue_class_User                avgt    4  2,258 ± 0,565  us/op
MyBenchmark.measure_readValue_class_UserWithProperties  avgt    4  2,257 ± 0,268  us/op
MyBenchmark.measure_readValue_class_VavrMap             avgt    4  2,736 ± 0,295  us/op  <-- slowest
```

## links
* [jaxenter - Aus der Java-Trickkiste: Microbenchmarking](https://jaxenter.de/aus-der-java-trickkiste-microbenchmarking-24155)
* [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)