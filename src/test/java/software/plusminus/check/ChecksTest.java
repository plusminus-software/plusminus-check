/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.plusminus.check;

import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static software.plusminus.check.Assertions.assertFail;

/**
 * Main entry point tests.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:OperatorWrap")
public class ChecksTest {

    @Test
    public void integerSuccess() {
        Checks.check(1).is(1);
    }

    @Test
    public void integerFail() {
        assertFail(() -> Checks.check(1).is(2), 1, 2);
    }

    @Test
    public void numberListSuccess() {
        Checks.check(Arrays.asList(1, 2, 3)).is(1, 2, 3);
    }

    @Test
    public void numberListFail() {
        assertFail(() -> Checks.check(Arrays.asList(1, 2, 3)).is(1, 2, 4),
                "[1,2,3]",
                "[1,2,4]");
    }

    @Test
    public void stringSuccess() {
        Checks.check("test").is("test");
    }

    @Test
    public void stringFail() {
        assertFail(() -> Checks.check("test").is("fail"), "test", "fail");
    }

    @Test
    public void stringListSuccess() {
        Checks.check(Arrays.asList("one", "two", "three")).is("one", "two", "three");
    }

    @Test
    public void stringListFail() {
        Runnable check = () -> Checks.check(Arrays.asList("one", "two", "three"))
                .is("one", "two", "four"); 
        assertFail(check,
                "[\"one\",\"two\",\"three\"]",
                "[\"one\",\"two\",\"four\"]");
    }
    
    @Test
    public void enumSuccess() {
        Checks.check(TestEnum.ONE).is(TestEnum.ONE);
    }
    
    @Test
    public void enumFail() {
        Runnable check = () -> Checks.check(TestEnum.ONE)
                .is(TestEnum.TWO);
        assertFail(check, "ONE", "TWO");
    }

    @Test
    public void objectSuccess() {
        Checks.check(one()).is(one());
    }

    @Test
    public void objectFail() {
        Runnable check = () -> Checks.check(one()).is(two());
        assertFail(check, 
                "{\n" +
                        "  \"string\": \"one\",\n" +
                        "  \"integer\": 1\n" +
                        "}", 
                "{\n" +
                        "  \"string\": \"two\",\n" +
                        "  \"integer\": 2\n" +
                        "}");
    }

    @Test
    public void collectionSuccess() {
        Checks.check(Arrays.asList(one(), two())).is(one(), two());
    }

    @Test
    public void collectionFail() {
        Runnable check = () -> Checks.check(Arrays.asList(one(), two())).is(one(), one());
        assertFail(check,
                "[\n" +
                        "  {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"string\": \"two\",\n" +
                        "    \"integer\": 2\n" +
                        "  }\n" +
                        "]",
                "[\n" +
                        "  {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  }\n" +
                        "]");
    }

    @Test
    public void mapSuccess() {
        Checks.check(toMap("one", one(), "two", two()))
                .is(toMap("one", one(), "two", two()));
    }

    @Test
    public void mapFail() {
        Runnable check = () -> Checks.check(toMap("one", one(), "two", two()))
                .is(toMap("one", one(), "two", one()));
        assertFail(check,
                "{\n" +
                        "  \"one\": {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  },\n" +
                        "  \"two\": {\n" +
                        "    \"string\": \"two\",\n" +
                        "    \"integer\": 2\n" +
                        "  }\n" +
                        "}",
                "{\n" +
                        "  \"one\": {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  },\n" +
                        "  \"two\": {\n" +
                        "    \"string\": \"one\",\n" +
                        "    \"integer\": 1\n" +
                        "  }\n" +
                        "}");
    }

    private TestClass one() {
        TestClass one = new TestClass();
        one.string = "one";
        one.integer = 1;
        return one;
    }

    private TestClass two() {
        TestClass two = new TestClass();
        two.string = "two";
        two.integer = 2;
        return two;
    }

    private Map<Object, Object> toMap(Object... keyValues) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i = i + 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }
        return map;
    }
    
    @Data
    private static class TestClass {
        private String string;
        private Integer integer;
    }
}