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
IDs and timestamps make snapshot tests flaky. Pre-check them with `hasField`,
then compare the rest against a stable resource — no placeholders, no
post-processing.
```java
@Test
void persistsUser() {
    User saved = repository.save(new User("Alice", 30));

    check(toJson(saved)).isJson()
        .hasField("id", id -> id.isNumber())
        .hasField("createdAt", t  -> t.isLike(LocalDate.now()))
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
`checkOf(supplier)` reads the element type from the supplier interface, so
`.at(i)` returns the narrow checker that fits.
```java
@Test
void hasPositiveAmounts() {
    List<Integer> amounts = invoice.getAmounts();

    checkOf(() -> amounts)            // typed as IntegerListSupplier
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
7. `Enum`
8. `java.nio.file.Path`
9. `InputStream` (compared by content)
10. Containers: `Optional`, `Collection`, `List`, `SortedSet`, `Deque`, `Map`
11. Arbitrary objects (compared via JSON / JSOG)

## Entry point
Static import `software.plusminus.check.Checks.check` in your tests. Overloads of
`check(...)` dispatch on the argument type and return a dedicated checker. To
plug in a custom factory, set it once during test bootstrap:

```java
Checks.factory(new MyCheckFactory());
```

## Common methods

| method                       | meaning                                                              |
|:-----------------------------|:---------------------------------------------------------------------|
| `is(...)`                    | structural equality (`primitive`, `T`, `String` JSON / resource)     |
| `isLike(any)`                | structural equality, type-agnostic                                   |
| `isEqual(T)`                 | strict `equals()` comparison                                         |
| `isSame(T)`                  | reference equality (`==`)                                            |
| `isType(Class<?>)`           | exact class match                                                    |
| `isNull()` / `isNotNull()`   | null assertions                                                      |

## Object checks

`check(obj)` returns an `ObjectCheck<T>`. In addition to the common methods:

| method                                            | meaning                                                        |
|:--------------------------------------------------|:---------------------------------------------------------------|
| `isInstanceOf(Class<X>)`                          | returns a typed `ObjectCheck<X>` for further chaining          |
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

`MapCheck` additionally exposes overloaded `is(k1, v1, ..., k4, v4)` shortcuts.

## Number checks

| method                       | meaning                                                |
|:-----------------------------|:-------------------------------------------------------|
| `isPositive()` / `isNegative()` / `isZero()` | sign assertions                        |
| `limitScale()` / `limitScale(int)`           | round decimals before comparison (default scale 4) |

## JSON checks

`check(jsonString)` of a well-formed JSON `String` returns a `JsonCheck` for
field-aware comparison:

```java
check(jsonString)
    .ignoringFieldsOrder()
    .hasField("id", id -> id.isNumber())
    .is("expected.json");
```

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

| method                          | meaning                                                  |
|:--------------------------------|:----------------------------------------------------------|
| `is(Path)`                      | path equality                                            |
| `is(String)`                    | compares path string, `/` and `\` treated as equal       |
| `isAbsolute()` / `isRelative()` | asserts the path kind                                    |

`Path` is also supported everywhere other scalar types are:

```java
check(javaClass).field(JavaClass::getPath).is(c -> c.is("src/main/Test.java")); // field navigation
check(object).field("path").is(c -> c.isPath().is("src/main"));                 // isPath() narrowing
checkOf(() -> paths).at(0).is(c -> c.is("src/main"));                           // typed List<Path> / Collection<Path>
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
| `isNotThrown()`            | asserts the lambda completed without throwing                |
