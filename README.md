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
