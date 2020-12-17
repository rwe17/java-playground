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
MyBenchmark.measure_readTree                            avgt    5  2,281 ± 0,369  us/op
MyBenchmark.measure_readValue_ObjectReader_User         avgt    5  1,924 ± 0,248  us/op
MyBenchmark.measure_readValue_TypeReference_User        avgt    5  2,031 ± 0,040  us/op
MyBenchmark.measure_readValue_class_JavaMap             avgt    5  2,290 ± 0,108  us/op
MyBenchmark.measure_readValue_class_User                avgt    5  2,080 ± 0,327  us/op
MyBenchmark.measure_readValue_class_UserWithProperties  avgt    5  2,010 ± 0,121  us/op
MyBenchmark.measure_readValue_class_VavrMap             avgt    5  2,393 ± 0,045  us/op
```

## links
* [jaxenter - Aus der Java-Trickkiste: Microbenchmarking](https://jaxenter.de/aus-der-java-trickkiste-microbenchmarking-24155)
* [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh)