# plusminus-check
Rich test assertions for Java

## What is it?
Fluent assertions based on JSON / string comparison instead of `equals()`, with
first-class support for loading expected values from `src/test/resources`.

```java
import static software.plusminus.check.Checks.check;

@Test
public void testProduct() {
    Product product = new Product("testName");

    check(product).is("{\"name\":\"testName\"}"); // ok
    check(product).is("some-file-in-resources-folder.json"); // ok
    check(product).is(new Product("otherName")); // fails, prints a JSON diff
}
```

## Why?

**Compare any object by structure — no `equals()` needed.**
You stop writing throwaway `equals()`/`hashCode()` just so a test can compare
DTOs, entities, or response bodies. The library serialises both sides to JSON
and diffs that.
```java
@Test
void createsUser() {
    // class User does not have properly overriden equals() and hashCode()
    // but the test still checks everything correctly

    User actual = userService.create("Alice", 30);
    check(actual).is(new User("Alice", 30)); // ok
}
```

**Pin the expected output to a resource file. Edit JSON, not Java.**
When the expected payload is large or nested (API responses, persisted
entities), keeping it inline drowns the test. Move it to
`src/test/resources/` and reference it by name.
```java
@Test
void rendersUserJson() {
    String body = controller.getUser(42).getBody();
    check(body).is("user.json");   // src/test/resources/user.json
}
```

**Ignore volatile fields without touching the expected JSON.**
IDs and timestamps make snapshot tests flaky. Pre-check them with `hasField`, or drop
them outright with `ignoringFields`, then compare the rest against a stable resource —
no placeholders, no post-processing.
```java
@Test
void persistsUser() {
    User saved = repository.save(new User("Alice", 30));

    check(toJson(saved)).isJson()
        .hasField("id", id -> id.isNumber())
        .ignoringFields("createdAt")
        .is("user.json");
}
```

**Navigate fields and collections with type-safe getters.**
No string field names, no reflection: refactors carry through. Use it when you
care about a few specific values inside a big aggregate.
```java
@Test
void calculatesOrderTotals() {
    Order order = orderService.place(cart);

    check(order)
        .field(Order::getCustomer).field(Customer::getName).is("Alice")
        .field(Order::getItems).at(0).field(Item::getPrice).is("9.99");
}
```

**Failure messages read like the call chain.**
You can find the broken field by skimming, without expanding multi-line JSON
diffs.
```text
customer.name expected:<Bob> but was:<Alice>
items[0].price expected:<9.99> but was:<5.00>
```

**One `check(x)` for every common type.**
Just `import static software.plusminus.check.Checks.check`. Primitives, `BigDecimal`, `Temporal`, `Optional`, `Map`,
arrays, collections, enums, POJOs — overloads dispatch on the argument and give you the matching
checker.

**Typed check of Collection/List? Yes.**
Java erases element types — `check(list)` gives you `ObjectCheck` elements.
`isXxxList()` asserts what the elements are and hands back a check whose `.at(i)`
returns the narrow checker that fits.
```java
@Test
void hasPositiveAmounts() {
    List<Integer> amounts = invoice.getAmounts();

    check(amounts).isNumberList(Integer.class)
        .at(0).is(c -> c.isPositive()) // c is NumberCheck<Integer>, not ObjectCheck
        .hasSize(3);
}
```

## Supported types
1. `boolean` / `Boolean`
2. `char` / `Character`
3. Whole numbers: `byte`, `Byte`, `short`, `Short`, `int`, `Integer`, `long`, `Long`, `BigInteger`
4. Decimals: `float`, `Float`, `double`, `Double`, `BigDecimal`
5. `String`
6. `Temporal` (any `java.time` type)
7. `Duration` and `Period` (the `TemporalAmount` types)
8. `Enum`
9. `java.nio.file.Path`
10. `InputStream` and `byte[]` (compared by content)
11. Containers: `Optional`, `Collection`, `List`, `SortedSet`, `Deque`, `Map`, any `Iterable`, `Iterator`, `Stream`
12. Arbitrary objects (compared via JSON / JSOG)

## Entry point
Static import `software.plusminus.check.Checks.check` in your tests. Overloads of
`check(...)` dispatch on the argument type and return a dedicated checker. To
plug in a custom factory, set it once during test bootstrap:

```java
Checks.FACTORY.set(new MyCheckFactory());
```

## Common methods

| method                       | meaning                                                              |
|:-----------------------------|:---------------------------------------------------------------------|
| `is(...)`                    | structural equality (`primitive`, `T`, `String` JSON / resource)     |
| `isLike(any)`                | structural equality, type-agnostic                                   |
| `isEqual(T)`                 | strict `equals()` comparison                                         |
| `isSame(T)`                  | reference equality (`==`)                                            |
| `isType(Class<?>)`           | exact class match                                                    |
| `isSameTypeAs(T)`            | exact class match against another instance's class                   |
| `isNull()` / `isNotNull()`   | null assertions                                                      |
| `isNot(T)`                   | negation of `is(...)`, compared the same structural way              |

Assertions return the check so they chain, except the ones that end a chain by nature:
`is(...)`, `isNull`, `isSame`, `isEqual`, `isLike`, `isZero`, `isTrue` / `isFalse`,
`isEmpty`, `containsExactly` and `allFieldsChecked`.

`isNot` is available on every checker and negates whatever `is` means for it, so it
compares structurally rather than by `equals`:

```java
check(user).isNot(new User("Bob", 30));
check(path).isNot(Paths.get("src/test"));
check(amount).isNot(0);
```

## Object checks

`check(obj)` returns an `ObjectCheck<T>`. In addition to the common methods:

| method                                            | meaning                                                        |
|:--------------------------------------------------|:---------------------------------------------------------------|
| `isInstanceOf(Class<X>)`                          | returns a typed `ObjectCheck<X>` for further chaining          |
| `isSameTypeAs(other)`                             | asserts the same runtime class as another instance             |
| `isBoolean()` / `isString()` / `isNumber()` / ... | narrows to a typed checker (see `TypeCheck`)             |
| `field(User::getName)`                            | descends via a typed method reference (returns narrowed checker) |
| `field("fieldName")`                              | descends by string name (reflective fallback)                  |
| `allFieldsChecked(coverage)`                      | asserts every field of the object has been checked             |

## Collection / Map checks

| method                          | meaning                                                                |
|:--------------------------------|:-----------------------------------------------------------------------|
| `isEmpty()` / `isNotEmpty()`    | size assertions                                                        |
| `hasSize(int)`                  | exact size                                                             |
| `contains(elements...)`         | each expected element matches a distinct actual element                |
| `contains(Consumer<E>...)`      | each `Consumer<E>` matches a distinct actual element via predicate     |
| `containsExactly(elements...)`  | actual and expected match as multisets                                 |
| `map(Function<T, R>)`           | converts every element, returning a check of the same shape over the results |
| `map(Function<T, R>, elementCheck)` | same, with an explicit element checker                             |
| `mapTo(getter)`                 | same, with the element checker chosen from the getter's return type    |
| `filter(Predicate<T>)`          | keeps the matching elements, along with the current element checker    |
| `flatMap(getter)`               | concatenates every element's collection into one check                 |
| `distinct()`                    | drops duplicates, keeping first occurrences in order                   |
| `sorted()` / `sorted(Comparator)` | reorders the elements, as on `Stream`                               |
| `allMatch(Predicate<T>)` / `noneMatch(...)` / `anyMatch(...)` | asserts a condition over every / no / at least one element |
| `hasNoDuplicates()`             | asserts no two elements are equal                                      |
| `isSorted()` / `isSortedBy(Comparator)` | asserts the elements are already in order                      |
| `first()` / `last()`            | descends into the first / last element (ordered checks only)           |
| `isStringList()` / `isNumberList()` / ... | asserts the element type and narrows the element checker     |

`map` and `mapTo` keep element order and the current path, so failures still report
the index the mismatching element came from. Both are available on `ListCheck`,
`CollectionCheck` and `ArrayCheck`.

`map` takes any `Function` — a lambda or a method reference — and gives you
`ObjectCheck<R>` elements:

```java
check(users).map(User::getName).is("Alice", "Bob");
check(users).map(u -> u.getName().trim()).contains("Alice");
```

`mapTo` takes a typed getter and — like `field(...)` — dispatches on its return type to
narrow the element checker:

```java
check(users).mapTo(User::getName).at(0).is(c -> c.startsWith("A"));   // StringCheck
check(users).mapTo(User::getAge).at(0).is(c -> c.isPositive());       // NumberCheck
check(orders).mapTo(Order::getLines).at(0).is(c -> c.hasSize(3));     // ListCheck
```

The narrowing covers the same types as `field(...)`: `String`, `Path`, every boxed and
primitive number, `boolean` / `char`, `Temporal`, `InputStream`, enums, `Optional`,
`Map`, arrays and the collection kinds. `mapTo` needs a method reference — a lambda
carries no getter type, so the overloads cannot be told apart. For a lambda with a
narrowed checker, pass the builder explicitly:

```java
check(users).map(u -> u.getName().trim(), StringCheck::new).at(0).is(c -> c.startsWith("A"));
```

`map` fails like any other check if a mapper cannot handle an element —
`element at index 1 is null` rather than a `NullPointerException`. A mapper that
accepts null keeps working, so mapping a list containing nulls is fine.

### Reshaping before asserting

`filter` and `sorted` narrow the elements down before the assertion, keeping the
element checker they already carry:

```java
check(users).filter(u -> u.isActive()).hasSize(2);
check(users).sorted(comparing(User::getName)).map(User::getName).is("Alice", "Bob");
```

Both re-index, so `at(0)` on the result is the first matching or lowest-sorted element,
not the first element of the original. `sorted()` uses natural ordering and reports
`all elements are comparable` / `all elements are non-null` instead of the
`ClassCastException` / `NullPointerException` `Stream.sorted()` would raise; pass a
`Comparator` (for instance `Comparator.nullsFirst(...)`) to handle those cases yourself.

`sorted` always returns a `ListCheck`, including when called on a `CollectionCheck` —
ordering is exactly what an unordered collection lacks, so the ordered check is the
useful result.

`flatMap` concatenates each element's collection, and `distinct` drops repeats:

```java
check(orders).flatMap(Order::getLines).hasSize(7);
check(names).distinct().is("Alice", "Bob");
```

A null collection fails — `element at index 1 has no collection` — rather than
contributing nothing, so a missing field is not left to surface later as a confusing
size mismatch. Empty collections are fine. This is the one place `Stream.flatMap`
differs: it treats a null mapped stream as empty. If null really means "no items"
in your model, filter first: `check(orders).filter(o -> o.getLines() != null)`.

`allMatch`, `noneMatch` and `hasNoDuplicates` assert but return the check itself, so
they chain like `hasSize`:

```java
check(users).allMatch(User::isActive).hasNoDuplicates().hasSize(3);
```

Duplicates — for both `distinct()` and `hasNoDuplicates()` — are decided structurally,
the same way `contains` and `containsExactly` compare elements, so they work on types
that do not implement `equals`. That differs from `Stream.distinct()`, which uses
`equals`.

`isSorted()` / `isSortedBy(Comparator)` assert an order that is already there, where
`sorted()` imposes one:

```java
check(results).isSortedBy(comparing(Result::getScore).reversed()).hasSize(10);
```

`first()` and `last()` are shorthand for `at(0)` and `at(size - 1)`, and report
`empty` rather than an index error on an empty collection. They are public on
`ListCheck` and `ArrayCheck` only — a plain `Collection` has no first element:

```java
check(events).first().is(c -> c.field(Event::getType).is("CREATED"));
```

### Narrowing an untyped collection

When the elements arrive as `Object` — from a raw JSON parse, a reflective call, a
heterogeneous list — `isXxxList()` asserts what they are and hands back a check with
the matching element checker. This is `TypeCheck`'s `isString()` / `isNumber()` applied
to elements instead of a single value:

```java
check(values).isStringList().at(0).is(c -> c.startsWith("a"));
check(values).isNumberList().at(0).is(c -> c.isPositive());
check(values).isEnumList(Status.class).at(0).is(Status.ACTIVE);
```

A mismatch reports the offending index:
`expected:<all elements are java.lang.String> but was:<element at index 1 is java.lang.Integer>`.
Null elements pass the type assertion — the element checkers are null-tolerant.

Available for `boolean`, `char`, `Number`, `Float`, `Double`, `BigDecimal`, `String`,
`Path`, `InputStream`, `Temporal` and enums, named per shape: `isStringList()` on
`ListCheck`, `isStringCollection()` on `CollectionCheck`, `isStringArray()` on
`ArrayCheck`. The three whose element type is itself generic — `isNumberXxx`,
`isTemporalXxx`, `isEnumXxx` — also take a `Class`, which both pins the element type
and lets the result be chained:

```java
check(values).isEnumList().at(0).is(Status.ACTIVE);              // does not compile
check(values).isEnumList(Status.class).at(0).is(Status.ACTIVE);  // fine
```

The no-argument form infers nothing in receiver position, so it only works when what
follows does not mention the element type (`isNumberList().hasSize(3)`).

### Iterables, iterators and streams

`check(...)` also accepts anything that only implements `Iterable`, plus `Stream` and
`Iterator`, which it drains into a list — so those can be checked once and are unusable
afterwards:

```java
check(repository.findAllLazily()).hasSize(3);          // Iterable -> CollectionCheck
check(orders.stream().map(Order::getId)).is(1, 2, 3);  // Stream   -> ListCheck
check(resultSet.iterator()).isNotEmpty();              // Iterator -> ListCheck
```

There is no `StreamCheck` or `IteratorCheck`: a one-shot sequence has to be snapshotted
before anything can be asserted about it, and once snapshotted it *is* a list. A
dedicated class would add no assertion of its own.

## Map checks

`check(map)` returns a `MapCheck<K, V>`. Beyond comparing the whole map, it descends
into entries:

| method                          | meaning                                                            |
|:--------------------------------|:-------------------------------------------------------------------|
| `is(k1, v1, ... k10, v10)`      | compares the whole map against the given pairs, up to ten of them  |
| `is(entries...)`                | same, for any number of `entry(k, v)` pairs                        |
| `isEmpty()` / `isNotEmpty()` / `hasSize(int)` | size assertions                                      |
| `containsKey(K)` / `doesNotContainKey(K)` | key presence                                             |
| `containsEntry(K, V)`           | key present and value structurally equal                           |
| `containsValue(V)`              | at least one value matches structurally                            |
| `valueAt(K)`                    | descends into the value of an entry, linked back to the map check  |

```java
check(headers)
    .hasSize(2)
    .containsEntry("Content-Type", "application/json")
    .valueAt("Location").is(c -> c.isString().startsWith("/api"));
```

The `is(...)` overloads take alternating keys and values and are typed as `K` and `V`,
so a wrong key or value type is a compile error. They stop at ten pairs, the way
`Map.of` does. Past that, or when the pairs are built rather than written out, use
`entry(...)`:

```java
import static software.plusminus.check.Checks.entry;

check(config).is(entry("host", "localhost"), entry("port", "8080"));
```

`entry(k, v)` is the same shape as Java 9's `Map.entry`, provided here because the
library targets Java 8.

Values are compared structurally, so entries holding objects without `equals` work like
everywhere else. Failures report the key in the path:
`Location expected:<...> but was:<...>`.

There is no `keys()` / `values()` — the collection checks already cover them, without a
second path convention to learn:

```java
check(headers.keySet()).contains("Content-Type", "Location");
check(headers.values()).hasSize(2);
```

## Number checks

| method                       | meaning                                                |
|:-----------------------------|:-------------------------------------------------------|
| `isPositive()` / `isNegative()` / `isZero()` | sign assertions                        |
| `isGreaterThan(Number)` / `isGreaterThanOrEqualTo(Number)` | ordering assertions       |
| `isLessThan(Number)` / `isLessThanOrEqualTo(Number)` | ordering assertions             |
| `isBetween(min, max)`        | range assertion, both bounds included                  |
| `isCloseTo(expected, tolerance)` | difference from the expected value is within the tolerance |
| `limitScale()` / `limitScale(int)`           | round decimals before comparison (default scale 4) |

Comparisons work across numeric types — `check(new BigDecimal("1.5")).isGreaterThan(1)`
is fine. `NaN` fails every ordering assertion rather than sorting anywhere in
particular; infinities compare as you would expect.

`isCloseTo` differs from `limitScale`: it compares the difference, where `limitScale`
rounds both sides first.

```java
check(total).isBetween(0, 100);
check(measured).isCloseTo(1.0, 0.05);
```

Numbers compare numerically, so `check(new BigDecimal("1.50")).is(new BigDecimal("1.5"))` passes.
A `BigDecimal` carries its scale, though, so `is(String)` compares it as a string instead — that is
the way to pin down the exact rendering:

```java
check(new BigDecimal("1.50")).is("1.50");   // ok
check(new BigDecimal("1.5")).is("1.50");    // fails: expected:<1.50> but was:<1.5>
```

`float` / `double` have no scale of their own and keep comparing numerically against a string.

## String checks

`check(string)` returns a `StringCheck`. Every assertion returns the check, so they
chain:

| method                              | meaning                                              |
|:------------------------------------|:------------------------------------------------------|
| `is(String)`                        | equality; a classpath resource reference is resolved first |
| `isEmpty()` / `isNotEmpty()`        | emptiness assertions                                  |
| `isBlank()` / `isNotBlank()`        | whitespace-only assertions                            |
| `hasLength(int)`                    | exact length                                          |
| `contains(String)` / `doesNotContain(String)` | substring assertions                        |
| `startsWith(String)` / `endsWith(String)` | prefix / suffix assertions                      |
| `matches(String regex)`             | the whole string matches, as in `String.matches`      |
| `ignoringLineEndings()`             | compares `\r\n`, `\r` and `\n` as equal, on both sides |
| `isJson()`                          | asserts the string is json and narrows to `JsonCheck` |

```java
check(response.getBody())
    .isNotBlank()
    .startsWith("{")
    .matches(".*\"status\"\\s*:\\s*\"OK\".*");
```

`isEmpty()` is the exception — there is nothing left to assert about an empty string,
so it ends the chain, as it does on collections and maps.

`ignoringLineEndings()` is what makes assertions on generated text portable — the same
test passes whether the file was produced on Windows or Unix:

```java
check(generator.render(model)).ignoringLineEndings().is("/Expected.java");
```

`PathCheck.hasContent` applies it already, so file-generation tests need no
line-ending handling of their own.

## Bytes checks

`check(byte[])` returns a `BytesCheck`, which compares content rather than individual
elements, and renders printable UTF-8 payloads as text in the failure message:

| method                          | meaning                                                |
|:--------------------------------|:--------------------------------------------------------|
| `is(byte[])`                    | content equality                                        |
| `is(String)`                    | content decoded as UTF-8; a resource reference is resolved |
| `isEmpty()` / `isNotEmpty()` / `hasSize(int)` | length assertions                         |
| `startsWith(byte[])`            | leading bytes                                           |

```java
check(exporter.toCsv(rows)).is("/expected-export.csv");
check(pdf).hasSize(1024).startsWith(new byte[] {'%', 'P', 'D', 'F'});
```

Use `check(Byte[])` when you do want element-by-element numeric assertions.

## Temporal, Duration and Period checks

`check(temporal)` of any `java.time` `Temporal` returns a `TemporalCheck`. Zoneless
values (`LocalDate`, `LocalDateTime`, ...) are resolved against the system default
time-zone, on both sides, so mixed types compare sensibly:

| method                              | meaning                                              |
|:------------------------------------|:------------------------------------------------------|
| `isRecent()` / `isRecent(Duration)`  | at or just before now (default tolerance 1 second)   |
| `isBefore(Temporal)` / `isAfter(Temporal)` | ordering assertions                            |
| `isBetween(min, max)`               | range assertion, both bounds included                 |
| `isCloseTo(Temporal, Duration)`     | within the tolerance, in either direction             |

```java
check(order.getCreatedAt()).isAfter(order.getRequestedAt());
check(saved.getUpdatedAt()).isCloseTo(Instant.now(), Duration.ofSeconds(2));
```

`Duration` and `Period` are `TemporalAmount`, not `Temporal`, so they get their own
checkers. `check(duration)` returns a `DurationCheck`:

| method                              | meaning                                              |
|:------------------------------------|:------------------------------------------------------|
| `is(Duration)` / `is(String)`       | equality; the string is ISO-8601, e.g. `"PT1M30S"`   |
| `isZero()` / `isPositive()` / `isNegative()` | sign assertions                              |
| `isLongerThan(Duration)` / `isShorterThan(Duration)` | ordering assertions            |
| `isBetween(min, max)`               | range assertion, both bounds included                 |
| `isCloseTo(Duration, Duration)`     | within the tolerance                                  |

`check(period)` returns a `PeriodCheck` with `is(Period)` / `is(String)`, `isZero()`,
`isNegative()` and `hasYears(int)` / `hasMonths(int)` / `hasDays(int)`. There are no
ordering assertions: one month and thirty days are not comparable without a reference
date.

```java
check(timer.elapsed()).isShorterThan(Duration.ofSeconds(2));
check(subscription.getTerm()).is("P1Y");
```

## Optional checks

| method                          | meaning                                                    |
|:--------------------------------|:------------------------------------------------------------|
| `isEmpty()`                     | asserts the optional is empty                               |
| `isPresent()`                   | asserts a value is present and descends into it             |
| `is(String)`                    | compares the contained value; a resource reference is resolved |
| `hasValue(T)`                   | compares the contained value, keeping its static type       |
| `isLike(any)`                   | compares the contained value against a value of any type    |

`is`, `hasValue` and `isLike` look through the optional rather than at it, so the common
case needs no unwrapping step:

```java
check(user.getNickname()).is("alice");
check(repository.findByName("Alice")).hasValue(new User("Alice", 30));
check(order.getDiscount()).isLike(10);
```

`hasValue` is the type-safe one — `hasValue(42)` on an `Optional<String>` does not compile,
where `isLike(42)` does and simply fails. It is spelled apart from `is` because `is(T)`
and the inherited `is(Optional<T>)` erase to the same signature, which Java rejects.

An empty optional fails those with `expected:<not empty> but was:<empty>`, the same
message `isPresent()` produces. Use `isPresent()` when you want the narrowed check of
the value to carry on from:

```java
check(user.getNickname()).isPresent().isString().startsWith("al");
```

`is(Optional)` still compares the optionals themselves, so
`check(actual).is(Optional.of("alice"))` works as before.

## JSON checks

`check(jsonString)` of a well-formed JSON `String` returns a `JsonCheck` for
field-aware comparison:

```java
check(jsonString)
    .ignoringFieldsOrder()
    .hasField("id", id -> id.isNumber())
    .is("expected.json");
```

| method                              | meaning                                                  |
|:------------------------------------|:----------------------------------------------------------|
| `is(String)`                        | compares the documents; the expected value may be a resource |
| `hasField(String)`                  | asserts the field is present and masks it                 |
| `hasField(String, Consumer)`        | same, checking the value separately                       |
| `doesNotHaveField(String)`          | asserts the field is absent                               |
| `ignoringFields(String...)`         | masks the fields, present or not                          |
| `ignoringFieldsOrder()`             | compares objects regardless of field order                |

Field names are dot-separated paths, so nested objects are reachable:

```java
check(body).isJson()
    .hasField("user.id", id -> id.isNumber().isPositive())
    .is("expected.json");
```

A masked field is replaced on **both** sides before the comparison, so its value does
not have to appear in the expected json at all. `ignoringFields` is the shortcut for
the volatile-value case, where there is nothing meaningful to assert:

```java
check(toJson(saved)).isJson()
    .ignoringFields("id", "createdAt", "audit.revision")
    .is("user.json");
```

Unlike `hasField`, `ignoringFields` does not require the fields to be present.

## Path checks

`check(path)` of a `java.nio.file.Path` returns a `PathCheck`. `is(String)` is
separator-agnostic: both sides are compared with `/` and `\` treated as equal,
so the expected value may use either style and the test passes on both Windows
and Unix:

```java
@Test
void targetPath() {
    Path path = generator.targetPath(TestModel.class, javaClass);
    check(path).is("src/main/java/software/plusminus/test/TestGenerated.java");
}
```

| method                              | meaning                                                    |
|:------------------------------------|:-----------------------------------------------------------|
| `is(Path)`                          | path equality                                              |
| `is(String)`                        | compares path string, `/` and `\` treated as equal         |
| `isAbsolute()` / `isRelative()`     | asserts the path kind                                      |
| `exists()` / `doesNotExist()`       | asserts presence on the file system                        |
| `isFile()` / `isDirectory()`        | asserts the path exists and is a regular file / directory  |
| `startsWith(Path)` / `startsWith(String)` | asserts the leading path elements, separator-agnostic |
| `endsWith(Path)` / `endsWith(String)`     | asserts the trailing path elements, separator-agnostic |
| `hasContent(String)`                | checks file content, ignoring line-ending differences; the expected value may be the content itself or a classpath resource reference |

`hasContent` makes file-generation tests one-liners — the expected value can be
a resource file next to the test:

```java
@Test
void generatesFile() {
    Path generated = generator.generate(TestModel.class);
    check(generated).hasContent("/TestModelController.java"); // classpath resource
}
```

`Path` is also supported everywhere other scalar types are:

```java
check(javaClass).field(JavaClass::getPath).is(c -> c.is("src/main/Test.java")); // field navigation
check(object).field("path").is(c -> c.isPath().is("src/main"));                 // isPath() narrowing
check(paths).isPathList().at(0).is(c -> c.is("src/main"));                      // typed List<Path> / Collection<Path>
check(pathArray).at(0).is(c -> c.isRelative());                                 // Path[]
```

## Exception checks

`checkException(lambda)` runs the lambda, captures anything it throws (checked
exceptions included) and returns an `ExceptionCheck` — a fluent analogue of
JUnit's `assertThrows`:

```java
import static software.plusminus.check.Checks.checkException;

@Test
void rejectsNegativeAmount() {
    checkException(() -> invoice.setAmount(-1))
        .is(IllegalArgumentException.class)
        .hasMessage("Amount must be positive");
}
```

| method                     | meaning                                                     |
|:---------------------------|:-------------------------------------------------------------|
| `is(Class)`                | exact exception class match                                  |
| `isInstanceOf(Class)`      | exception class or any subclass (like `assertThrows`)        |
| `hasMessage(String)`       | exception message equality (`null`-safe)                     |
| `hasMessageContaining(String)` | message contains the substring                           |
| `hasMessageMatching(String)`   | the whole message matches the regular expression          |
| `hasCause(Class)` / `hasNoCause()` | exact cause class match / absence of a cause          |
| `cause()`                  | descends into the cause, returning an `ExceptionCheck`       |
| `rootCause()`              | descends into the deepest cause, or the exception itself     |
| `isNotThrown()`            | asserts the lambda completed without throwing                |

Wrapped exceptions are the normal case, so the cause is reachable rather than something
you have to unwrap by hand:

```java
checkException(() -> service.load("missing"))
    .is(ServiceException.class)
    .hasMessageContaining("missing")
    .cause()
        .is(FileNotFoundException.class);

checkException(() -> service.save(entity))
    .rootCause()
        .is(SQLException.class);
```

Failures name the level they happened at, so `cause expected:<...> but was:<...>`
points at the cause rather than the wrapper.
