package software.plusminus.check.fixtures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessivePublicCount", "PMD.OptionalField"})
public class TestObject {

    private String name;
    private Integer count;

    private Path pathField;
    @JsonIgnore
    private boolean primitiveBooleanField;
    private Boolean booleanField;
    @JsonIgnore
    private char primitiveCharacterField;
    private Character characterField;
    @JsonIgnore
    private byte primitiveByteField;
    private Byte byteField;
    @JsonIgnore
    private short primitiveShortField;
    private Short shortField;
    @JsonIgnore
    private int primitiveIntegerField;
    @JsonIgnore
    private long primitiveLongField;
    private Long longField;
    private BigInteger bigIntegerField;
    @JsonIgnore
    private float primitiveFloatField;
    private Float floatField;
    @JsonIgnore
    private double primitiveDoubleField;
    private Double doubleField;
    private BigDecimal bigDecimalField;
    private Instant temporalField;
    private InputStream streamField;
    private TestEnum enumField;
    private Collection<String> collectionField;
    private List<String> listField;
    private SortedSet<String> sortedSetField;
    private Deque<String> dequeField;
    private Optional<String> optionalField;
    private Map<String, String> mapField;
    private String[] arrayField;

    public TestObject() {
    }

    public TestObject(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public static TestObject of(String value, int number) {
        TestObject object = new TestObject(value, number);
        object.setPathField(Paths.get(value));
        object.setPrimitiveBooleanField(true);
        object.setBooleanField(Boolean.TRUE);
        object.setPrimitiveCharacterField(value.charAt(0));
        object.setCharacterField(value.charAt(0));
        object.setPrimitiveByteField((byte) number);
        object.setByteField((byte) number);
        object.setPrimitiveShortField((short) number);
        object.setShortField((short) number);
        object.setPrimitiveIntegerField(number);
        object.setPrimitiveLongField(number);
        object.setLongField((long) number);
        object.setBigIntegerField(BigInteger.valueOf(number));
        object.setPrimitiveFloatField(number + 0.5f);
        object.setFloatField(number + 0.5f);
        object.setPrimitiveDoubleField(number + 0.5);
        object.setDoubleField(number + 0.5);
        object.setBigDecimalField(BigDecimal.valueOf(number));
        object.setTemporalField(Instant.now());
        object.setStreamField(new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)));
        object.setEnumField(TestEnum.values()[number - 1]);
        object.setCollectionField(Collections.singletonList(value));
        object.setListField(Collections.singletonList(value));
        object.setSortedSetField(new TreeSet<>(Collections.singletonList(value)));
        object.setDequeField(new ArrayDeque<>(Collections.singletonList(value)));
        object.setOptionalField(Optional.of(value));
        object.setMapField(Collections.singletonMap("key", value));
        object.setArrayField(new String[]{value});
        return object;
    }
}
