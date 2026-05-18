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
import software.plusminus.check.fixtures.TestEnum;
import software.plusminus.check.util.JsonUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static software.plusminus.check.Checks.checkOf;
import static software.plusminus.check.helper.Assertions.assertFail;

/**
 * Main entry point tests.
 *
 * @author Taras Shpek
 */
@SuppressWarnings({"java:S2699", "checkstyle:OperatorWrap"})
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
                JsonUtil.pretty("[1,2,3]"),
                JsonUtil.pretty("[1,2,4]"));
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
    public void listSuccess() {
        Checks.check(Arrays.asList("one", "two", "three")).is("one", "two", "three");
    }

    @Test
    public void listFail() {
        Runnable check = () -> Checks.check(Arrays.asList("one", "two", "three"))
                .is("one", "two", "four");
        assertFail(check,
                JsonUtil.pretty("[\"one\",\"two\",\"three\"]"),
                JsonUtil.pretty("[\"one\",\"two\",\"four\"]"));
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


    @Test
    public void enumCollectionReturnsCollectionCheck() {
        Collection<Color> coll = new HashSet<>(Arrays.asList(Color.RED, Color.GREEN));
        CollectionCheck<Color, Collection<Color>, EnumCheck<Color>> check = checkOf(() -> coll);
        check.hasSize(2);
    }

    @Test
    public void enumListReturnsOrderedCheck() {
        List<Color> list = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE);
        OrderedCollectionCheck<Color, List<Color>, EnumCheck<Color>> check = checkOf(() -> list);
        check.hasSize(3).contains(Color.RED);
    }

    @Test
    public void temporalCollectionReturnsCollectionCheck() {
        Collection<LocalDate> coll = new HashSet<>(Arrays.asList(LocalDate.of(2020, 1, 1)));
        CollectionCheck<LocalDate, Collection<LocalDate>, TemporalCheck<LocalDate>> check = checkOf(() -> coll);
        check.hasSize(1);
    }

    @Test
    public void temporalListReturnsOrderedCheck() {
        List<LocalDate> list = Arrays.asList(LocalDate.of(2020, 1, 1), LocalDate.of(2021, 1, 1));
        OrderedCollectionCheck<LocalDate, List<LocalDate>, TemporalCheck<LocalDate>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void stringListReturnsOrderedCheck() {
        List<String> list = Arrays.asList("a", "b", "c");
        OrderedCollectionCheck<String, List<String>, StringCheck> check = checkOf(() -> list);
        check.hasSize(3).contains("a");
    }

    @Test
    public void stringListElementUsesStringCheck() {
        List<String> list = Arrays.asList("One");
        checkOf(() -> list).contains(c -> c.is("one.txt"));
    }

    @Test
    public void stringListFail() {
        List<String> list = Arrays.asList("a", "b");
        assertFail(() -> checkOf(() -> list).hasSize(3), "size is 2", "size is 3");
    }

    @Test
    public void stringCollectionReturnsCollectionCheck() {
        Collection<String> coll = new HashSet<>(Arrays.asList("a", "b"));
        CollectionCheck<String, Collection<String>, StringCheck> check = checkOf(() -> coll);
        check.hasSize(2).contains("a");
    }

    @Test
    public void booleanListReturnsOrderedCheck() {
        List<Boolean> list = Arrays.asList(true, false, true);
        OrderedCollectionCheck<Boolean, List<Boolean>, BooleanCheck> check = checkOf(() -> list);
        check.hasSize(3).contains(true);
    }

    @Test
    public void booleanListElementUsesBooleanCheck() {
        List<Boolean> list = Arrays.asList(true);
        checkOf(() -> list).contains(BooleanCheck::isTrue);
    }

    @Test
    public void booleanListFail() {
        List<Boolean> list = Arrays.asList(true, false);
        assertFail(() -> checkOf(() -> list).hasSize(3), "size is 2", "size is 3");
    }

    @Test
    public void booleanCollectionReturnsCollectionCheck() {
        Collection<Boolean> coll = new HashSet<>(Arrays.asList(true, false));
        CollectionCheck<Boolean, Collection<Boolean>, BooleanCheck> check = checkOf(() -> coll);
        check.hasSize(2).contains(true);
    }

    @Test
    public void characterList() {
        List<Character> list = Arrays.asList('a', 'b');
        OrderedCollectionCheck<Character, List<Character>, CharacterCheck> check = checkOf(() -> list);
        check.hasSize(2).contains('a');
    }

    @Test
    public void byteList() {
        List<Byte> list = Arrays.asList((byte) 1, (byte) 2);
        OrderedCollectionCheck<Byte, List<Byte>, NumberCheck<Byte>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void shortList() {
        List<Short> list = Arrays.asList((short) 1, (short) 2);
        OrderedCollectionCheck<Short, List<Short>, NumberCheck<Short>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void integerList() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        OrderedCollectionCheck<Integer, List<Integer>, NumberCheck<Integer>> check = checkOf(() -> list);
        check.hasSize(3).contains(2);
    }

    @Test
    public void integerListElementUsesNumberCheck() {
        List<Integer> list = Arrays.asList(1);
        checkOf(() -> list).contains(NumberCheck::isPositive);
    }

    @Test
    public void longList() {
        List<Long> list = Arrays.asList(1L, 2L);
        OrderedCollectionCheck<Long, List<Long>, NumberCheck<Long>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void bigIntegerList() {
        List<BigInteger> list = Arrays.asList(BigInteger.ONE, BigInteger.TEN);
        OrderedCollectionCheck<BigInteger, List<BigInteger>, NumberCheck<BigInteger>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void floatList() {
        List<Float> list = Arrays.asList(1.0f, 2.0f);
        OrderedCollectionCheck<Float, List<Float>, DecimalCheck<Float>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void doubleList() {
        List<Double> list = Arrays.asList(1.0d, 2.0d);
        OrderedCollectionCheck<Double, List<Double>, DecimalCheck<Double>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void doubleListElementUsesDecimalCheck() {
        List<Double> list = Arrays.asList(1.234567d);
        checkOf(() -> list).contains(c -> c.limitScale(2).is(1.23d));
    }

    @Test
    public void bigDecimalList() {
        List<BigDecimal> list = Arrays.asList(BigDecimal.ONE, BigDecimal.TEN);
        OrderedCollectionCheck<BigDecimal, List<BigDecimal>, DecimalCheck<BigDecimal>> check = checkOf(() -> list);
        check.hasSize(2);
    }

    @Test
    public void listOfLists() {
        List<List<String>> nested = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c"));
        OrderedCollectionCheck<List<String>, List<List<String>>,
                CollectionCheck<String, List<String>, ObjectCheck<String>>> check = checkOf(() -> nested);
        check.hasSize(2).contains(inner -> inner.hasSize(2));
    }

    @Test
    public void listOfListsFail() {
        List<List<String>> nested = Arrays.asList(Arrays.asList("a"));
        assertFail(() -> checkOf(() -> nested).hasSize(2), "size is 1", "size is 2");
    }

    @Test
    public void listOfMaps() {
        List<Map<String, String>> maps = Arrays.asList(
                Collections.singletonMap("k1", "v1"),
                Collections.singletonMap("k2", "v2"));
        OrderedCollectionCheck<Map<String, String>, List<Map<String, String>>, MapCheck<String, String>>
                check = checkOf(() -> maps);
        check.hasSize(2).contains(inner -> inner.hasSize(1));
    }

    @Test
    public void listOfMapsFail() {
        List<Map<String, String>> maps = Arrays.asList(Collections.singletonMap("k", "v"));
        assertFail(() -> checkOf(() -> maps).hasSize(2), "size is 1", "size is 2");
    }

    @Test
    public void listOfOptionals() {
        List<Optional<String>> opts = Arrays.asList(Optional.of("a"), Optional.empty());
        checkOf(() -> opts)
                .hasSize(2)
                .contains(OptionalCheck::isEmpty);
    }

    @Test
    public void listOfCollections() {
        List<Collection<String>> nested = Arrays.asList(
                new HashSet<>(Arrays.asList("a", "b")),
                new HashSet<>(Arrays.asList("c")));
        checkOf(() -> nested).hasSize(2).contains(inner -> inner.hasSize(2));
    }

    @Test
    public void collectionOfLists() {
        Collection<List<String>> nested = new HashSet<>(Arrays.asList(
                Arrays.asList("a"),
                Arrays.asList("b", "c")));
        checkOf(() -> nested).hasSize(2);
    }

    @Test
    public void collectionOfMaps() {
        Collection<Map<String, String>> maps = new HashSet<>(Arrays.asList(
                Collections.singletonMap("k", "v")));
        checkOf(() -> maps).hasSize(1);
    }

    @Test
    public void collectionOfOptionals() {
        Collection<Optional<String>> opts = new HashSet<>(Arrays.asList(Optional.of("a")));
        checkOf(() -> opts).hasSize(1);
    }
    
    @Data
    private static class TestClass {
        private String string;
        private Integer integer;
    }

    private enum Color { RED, GREEN, BLUE }
}