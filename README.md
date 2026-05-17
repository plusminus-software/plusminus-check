# plusminus-check
Rich test assertions for Java

## Why?
To get a fluent assertions based on strings/jsons instead of equals() method and to easily use files from resource folder.
Example:
```
@Test
public void testProduct() {
    Product product = new Product("testName");
    
    check(product).is("{\"name\":\"testName\"}");
    check(product).is("some-file-in-resources-folder.json");
    check(product).is(new Product("otherName"));// fail + you see json comparison of two object
}
```
## Supported types
1. boolean and Boolean
2. char and Character
3. Integers: byte, Byte, short, Short, int, Integer, long, Long, BigInteger
4. Decimals: float, Float, double, Double, BigDecimal
5. Simple: String, Temporal
6. Containers: Optional, Collection, List
7. Object


## Check methods on different types
|                    | is(primitive) | is(T) | is(String) | isLike(any) | isNull()+ | isEmpty()+ | isEqual(T) | isSame(T) | isType(T)+ |
|:-------------------|:-------------:|:-----:|:----------:|:-----------:|:---------:|:----------:|:----------:|:---------:|:----------:|
| boolean            |       +       |   +   |            |             |           |            |            |           |            |
| Boolean            |       +       |   +   |            |             |     +     |            |            |           |            |
| char               |       +       |   +   |     +      |             |           |            |            |           |            |
| Character          |       +       |   +   |     +      |             |     +     |            |            |           |            |
| Primitive integers |       +       |   +   |            |             |           |            |            |           |            |
| Integers           |       +       |   +   |            |             |     +     |            |            |           |            |
| Primitive decimals |       +       |   +   |     +      |             |           |            |            |           |            |
| Decimals           |       +       |   +   |     +      |             |     +     |            |            |           |            |
| Simple objects     |               |   +   |     +      |      +      |     +     |    +/-     |            |     +     |            |
| Collections        |               |   +   |     +      |      +      |     +     |     +      |     +      |     +     |     +      |
| Objects            |               |   +   |     +      |      +      |     +     |            |     +      |     +     |     +      |

