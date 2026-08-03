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

import org.junit.Test;
import software.plusminus.check.fixtures.TestEnum;
import software.plusminus.check.fixtures.TestObject;
import software.plusminus.check.types.AbstractCheck;
import software.plusminus.check.types.ArrayCheck;
import software.plusminus.check.types.BooleanCheck;
import software.plusminus.check.types.BytesCheck;
import software.plusminus.check.types.CharacterCheck;
import software.plusminus.check.types.CollectionCheck;
import software.plusminus.check.types.DecimalCheck;
import software.plusminus.check.types.DurationCheck;
import software.plusminus.check.types.EnumCheck;
import software.plusminus.check.types.IterableCheck;
import software.plusminus.check.types.ListCheck;
import software.plusminus.check.types.MapCheck;
import software.plusminus.check.types.NullableBooleanCheck;
import software.plusminus.check.types.NullableCharacterCheck;
import software.plusminus.check.types.NullableDecimalCheck;
import software.plusminus.check.types.NullableNumberCheck;
import software.plusminus.check.types.NumberCheck;
import software.plusminus.check.types.ObjectCheck;
import software.plusminus.check.types.OptionalCheck;
import software.plusminus.check.types.PathCheck;
import software.plusminus.check.types.PeriodCheck;
import software.plusminus.check.types.StringCheck;
import software.plusminus.check.types.TemporalCheck;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static software.plusminus.check.Checks.check;

/**
 * Main entry point tests.
 *
 * @author Taras Shpek
 */
@SuppressWarnings({
    "java:S2699",
    "unchecked",
    "PMD.CouplingBetweenObjects",
    "PMD.CyclomaticComplexity",
    "PMD.ExcessiveClassLength",
    "PMD.ExcessivePublicCount"})
public class ChecksTest {

    // --- Scalars ---

    @Test
    public void booleanCheck() {
        boolean actual = true;
        verify(() -> check(actual), BooleanCheck.class);
    }

    @Test
    public void nullableBooleanCheck() {
        Boolean actual = Boolean.TRUE;
        verify(() -> check(actual), NullableBooleanCheck.class);
    }

    @Test
    public void characterCheck() {
        char actual = 'a';
        verify(() -> check(actual), CharacterCheck.class);
    }

    @Test
    public void nullableCharacterCheck() {
        Character actual = 'a';
        verify(() -> check(actual), NullableCharacterCheck.class);
    }

    @Test
    public void byteCheck() {
        byte actual = 1;
        verify(() -> check(actual), NumberCheck.class);
    }

    @Test
    public void nullableByteCheck() {
        Byte actual = 1;
        verify(() -> check(actual), NullableNumberCheck.class);
    }

    @Test
    public void shortCheck() {
        short actual = 1;
        verify(() -> check(actual), NumberCheck.class);
    }

    @Test
    public void nullableShortCheck() {
        Short actual = 1;
        verify(() -> check(actual), NullableNumberCheck.class);
    }

    @Test
    public void intCheck() {
        int actual = 1;
        verify(() -> check(actual), NumberCheck.class);
    }

    @Test
    public void nullableIntegerCheck() {
        Integer actual = 1;
        verify(() -> check(actual), NullableNumberCheck.class);
    }

    @Test
    public void longCheck() {
        long actual = 1L;
        verify(() -> check(actual), NumberCheck.class);
    }

    @Test
    public void nullableLongCheck() {
        Long actual = 1L;
        verify(() -> check(actual), NullableNumberCheck.class);
    }

    @Test
    public void bigIntegerCheck() {
        BigInteger actual = BigInteger.ONE;
        verify(() -> check(actual), NullableNumberCheck.class);
    }

    @Test
    public void floatCheck() {
        float actual = 1.0f;
        verify(() -> check(actual), DecimalCheck.class);
    }

    @Test
    public void nullableFloatCheck() {
        Float actual = 1.0f;
        verify(() -> check(actual), NullableDecimalCheck.class);
    }

    @Test
    public void doubleCheck() {
        double actual = 1.0d;
        verify(() -> check(actual), DecimalCheck.class);
    }

    @Test
    public void nullableDoubleCheck() {
        Double actual = 1.0d;
        verify(() -> check(actual), NullableDecimalCheck.class);
    }

    @Test
    public void bigDecimalCheck() {
        BigDecimal actual = BigDecimal.ONE;
        verify(() -> check(actual), NullableDecimalCheck.class);
    }

    @Test
    public void stringCheck() {
        String actual = "a";
        verify(() -> check(actual), StringCheck.class);
    }

    @Test
    public void pathCheck() {
        Path actual = Paths.get("src/main");
        verify(() -> check(actual), PathCheck.class);
    }

    @Test
    public void durationCheck() {
        Duration actual = Duration.ofSeconds(1);
        verify(() -> check(actual), DurationCheck.class);
    }

    @Test
    public void periodCheck() {
        Period actual = Period.ofDays(1);
        verify(() -> check(actual), PeriodCheck.class);
    }

    @Test
    public void temporalCheck() {
        LocalDate actual = LocalDate.now();
        verify(() -> check(actual), TemporalCheck.class);
    }

    @Test
    public void optionalCheck() {
        Optional<String> actual = Optional.of("x");
        verify(() -> check(actual), OptionalCheck.class);
    }

    @Test
    public void collectionCheck() {
        Collection<String> actual = Collections.singleton("a");
        verify(() -> check(actual), CollectionCheck.class);
    }

    @Test
    public void listCheck() {
        List<String> actual = Collections.singletonList("a");
        verify(() -> check(actual), ListCheck.class);
    }

    @Test
    public void sortedSetCheck() {
        SortedSet<String> actual = new TreeSet<>(Collections.singleton("a"));
        verify(() -> check(actual), ListCheck.class);
    }

    @Test
    public void dequeCheck() {
        Deque<String> actual = new ArrayDeque<>(Collections.singletonList("a"));
        verify(() -> check(actual), ListCheck.class);
    }

    @Test
    public void iterableCheck() {
        Iterable<String> actual = () -> Collections.singletonList("a").iterator();
        verify(() -> check(actual), IterableCheck.class);
    }

    @Test
    public void streamCheck() {
        verify(() -> check(Stream.of("a")), ListCheck.class);
    }

    @Test
    public void nullStreamCheck() {
        verify(() -> check((Stream<String>) null), ListCheck.class);
    }

    @Test
    public void iteratorCheck() {
        verify(() -> check(Collections.singletonList("a").iterator()), ListCheck.class);
    }

    @Test
    public void nullIteratorCheck() {
        verify(() -> check((Iterator<String>) null), ListCheck.class);
    }

    @Test
    public void nullIterableCheck() {
        verify(() -> check((Iterable<String>) null), IterableCheck.class);
    }

    @Test
    public void mapCheck() {
        Map<String, Integer> actual = Collections.singletonMap("a", 1);
        verify(() -> check(actual), MapCheck.class);
    }

    @Test
    public void enumCheck() {
        TestEnum actual = TestEnum.ONE;
        verify(() -> check(actual), EnumCheck.class);
    }

    @Test
    public void objectCheck() {
        TestObject actual = new TestObject("a", 1);
        verify(() -> check(actual), ObjectCheck.class);
    }

    // --- Arrays ---

    @Test
    public void booleanArrayCheck() {
        boolean[] actual = {true};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableBooleanArrayCheck() {
        Boolean[] actual = {true};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void charArrayCheck() {
        char[] actual = {'a'};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableCharArrayCheck() {
        Character[] actual = {'a'};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void bytesCheck() {
        byte[] actual = {1};
        verify(() -> check(actual), BytesCheck.class);
    }

    @Test
    public void nullableByteArrayCheck() {
        Byte[] actual = {1};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void shortArrayCheck() {
        short[] actual = {1};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableShortArrayCheck() {
        Short[] actual = {1};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void intArrayCheck() {
        int[] actual = {1};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableIntegerArrayCheck() {
        Integer[] actual = {1};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void longArrayCheck() {
        long[] actual = {1L};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableLongArrayCheck() {
        Long[] actual = {1L};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void bigIntegerArrayCheck() {
        BigInteger[] actual = {BigInteger.ONE};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void floatArrayCheck() {
        float[] actual = {1.0f};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableFloatArrayCheck() {
        Float[] actual = {1.0f};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void doubleArrayCheck() {
        double[] actual = {1.0d};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void nullableDoubleArrayCheck() {
        Double[] actual = {1.0d};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void bigDecimalArrayCheck() {
        BigDecimal[] actual = {BigDecimal.ONE};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void stringArrayCheck() {
        String[] actual = {"a"};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void pathArrayCheck() {
        Path[] actual = {Paths.get("src/main")};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void temporalArrayCheck() {
        LocalDate[] actual = {LocalDate.now()};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void enumArrayCheck() {
        TestEnum[] actual = {TestEnum.ONE};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void optionalArrayCheck() {
        Optional<String>[] actual = (Optional<String>[]) new Optional[]{Optional.of("x")};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void mapArrayCheck() {
        Map<String, String>[] actual = (Map<String, String>[])
                new Map[]{Collections.singletonMap("k", "v")};
        verify(() -> check(actual), ArrayCheck.class);
    }

    @Test
    public void objectArrayCheck() {
        TestObject[] actual = {new TestObject("a", 1)};
        verify(() -> check(actual), ArrayCheck.class);
    }

    private <T extends AbstractCheck<?>> void verify(Supplier<T> check, Class<T> type) {
        assertEquals(check.get().getClass(), type);
    }
}
