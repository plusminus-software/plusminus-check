package software.plusminus.check.fixtures;

import lombok.Data;

@Data
public class TestObject {

    private String name;
    private Integer count;

    public TestObject() {
    }

    public TestObject(String name, Integer count) {
        this.name = name;
        this.count = count;
    }
}
