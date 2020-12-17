/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1) // 2
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Threads(1)
public class MyBenchmark {

    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
    }

    private static final String userJson = readResoucesAsString("/user.json");

    private static final TypeReference<User> typeReferenceUser = new TypeReference<User>() {
    };
    private static final ObjectReader objectReaderUser = objectMapper.readerFor(typeReferenceUser);

    @Benchmark
    public void measure_readValue_class_JavaMap() throws JsonProcessingException {
        Map map = objectMapper.readValue(userJson, Map.class);
    }

    @Benchmark
    public void measure_readValue_class_VavrMap() throws JsonProcessingException {
        io.vavr.collection.Map map = objectMapper.readValue(userJson, io.vavr.collection.Map.class);
    }

    @Benchmark
    public void measure_readTree() throws JsonProcessingException {
        JsonNode userNode = objectMapper.readTree(userJson);
    }

    @Benchmark
    public void measure_readValue_class_User() throws JsonProcessingException {
        User user = objectMapper.readValue(userJson, User.class);
    }

    @Benchmark
    public void measure_readValue_class_UserWithProperties() throws JsonProcessingException {
        UserWithProperties user = objectMapper.readValue(userJson, UserWithProperties.class);
    }

    @Benchmark
    public void measure_readValue_TypeReference_User() throws JsonProcessingException {
        User user = objectMapper.readValue(userJson, typeReferenceUser);
    }

    @Benchmark
    public void measure_readValue_ObjectReader_User() throws JsonProcessingException {
        Object value = objectReaderUser.readValue(userJson);
    }

    public static String readResoucesAsString(String path) {
        try (InputStream is = MyBenchmark.class.getResourceAsStream(path)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class User {

        public String name;
        public String sureName;
        public List<Address> addresses;
        public Map<String, Object> etc;
    }

    static class UserWithProperties {

        @JsonProperty("name")
        public String name;
        @JsonProperty("sureName")
        public String sureName;
        @JsonProperty("addresses")
        public List<Address> addresses;
        @JsonProperty("etc")
        public Map<String, Object> etc;
    }

    static class Address {

        public String street;
        public Long zipCode;
        public String city;
    }
}