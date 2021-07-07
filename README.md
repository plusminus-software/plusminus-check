# plusminus-check
Rich test assertions for Java

##Why?
To get a fluent assertions based on strings/jsons instead of equals() method and to assert using files in an resource folder.
Examples:

```
@Test
public void testProduct() {
    Product product = new Product("testName");
    
    check(product).is("{\"name\":\"testName\"}");
    check(product).is("some-file-in-resources-folder.json");
    check(product).is(new Product("otherName"));// shows error with JSON comparison
}
```
