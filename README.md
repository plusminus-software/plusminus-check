# plusminus-check
Rich test assertions for Java

## What is it?
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
    check(actual).is(new User("Alice", 30)); // OK
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
        .hasField("id",        id -> id.isNumber())
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
        .fieldOf(Order::getCustomer).fieldOf(Customer::getName).is("Alice")
        .fieldOf(Order::getItems).at(0).fieldOf(Item::getPrice).is("9.99");
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
Primitives, `BigDecimal`, `Temporal`, `Optional`, `Map`, arrays, collections,
enums, POJOs — overloads dispatch on the argument and give you the matching
checker (sign checks for numbers, `isEmpty()` for collections, `hasField()`
for JSON, …).

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
