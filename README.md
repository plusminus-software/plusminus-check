# plusminus-check
Rich test assertions for Java

## Why?
Fluent assertions based on JSON / string comparison instead of `equals()`, with
first-class support for loading expected values from `src/test/resources`.

```java
@Test
public void testProduct() {
    Product product = new Product("testName");

    check(product).is("{\"name\":\"testName\"}");
    check(product).is("some-file-in-resources-folder.json");
    check(product).is(new Product("otherName")); // fails, prints a JSON diff
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
8. Containers: `Optional`, `Collection`, `List`, `SortedSet`, `Deque`, `Map`
9. Arbitrary objects (compared via JSON / JSOG)

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
| `isBoolean()` / `isString()` / `isNumber()` / ... | narrows to a typed checker (see `ObjectCheckType`)             |
| `field("name")`                                   | descends into a field by name, returns a `LinkedCheck`         |
| `fieldOf(User::getName, ...)`                     | descends via a method reference, with full check builder       |
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
