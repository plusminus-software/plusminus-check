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
package software.plusminus.check.types;

import lombok.Data;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.Checks.checkOf;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PathCheckTest {

    @Test
    public void isOk() {
        check(Paths.get("src/main/java")).is(Paths.get("src/main/java"));
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Paths.get("src/main")).is(Paths.get("src/test")),
                path("src/main"), path("src/test"));
    }

    @Test
    public void isStringOk() {
        Path path = Paths.get("src/main/java/software/plusminus/test/TestGenerated.java");
        String expectedPath = "src/main/java/software/plusminus/test/TestGenerated.java"
                .replace('/', File.separatorChar);
        check(path).is(expectedPath);
    }

    @Test
    public void isStringWithForwardSlashesOk() {
        Path path = Paths.get("src/main/java/software/plusminus/test/TestGenerated.java");
        check(path).is("src/main/java/software/plusminus/test/TestGenerated.java");
    }

    @Test
    public void isStringWithBackslashesOk() {
        Path path = Paths.get("src/main/java/software/plusminus/test/TestGenerated.java");
        check(path).is("src\\main\\java\\software\\plusminus\\test\\TestGenerated.java");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check(Paths.get("src/main")).is("src/test"),
                path("src/main"), "src/test");
    }

    @Test
    public void isNullOk() {
        check((Path) null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> check((Path) null).is("src/main"));
    }

    @Test
    public void isNotNullOk() {
        check(Paths.get("src/main")).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> check((Path) null).isNotNull());
    }

    @Test
    public void isSameOk() {
        Path path = Paths.get("src/main");
        check(path).isSame(path);
    }

    @Test
    public void isSameFail() {
        assertFail(() -> check(Paths.get("src/main")).isSame(Paths.get("src/main")));
    }

    @Test
    public void isAbsoluteOk() {
        check(Paths.get("src/main").toAbsolutePath()).isAbsolute();
    }

    @Test
    public void isAbsoluteFail() {
        assertFail(() -> check(Paths.get("src/main")).isAbsolute(),
                path("src/main"), "absolute path");
    }

    @Test
    public void isRelativeOk() {
        check(Paths.get("src/main")).isRelative();
    }

    @Test
    public void isRelativeFail() {
        Path absolute = Paths.get("src/main").toAbsolutePath();
        assertFail(() -> check(absolute).isRelative(),
                absolute.toString(), "relative path");
    }

    @Test
    public void fieldWithGetterOk() {
        TestFile file = new TestFile("test", Paths.get("src/main"));
        check(file).field(TestFile::getPath).is(c -> c.is("src/main"));
    }

    @Test
    public void fieldWithGetterFail() {
        TestFile file = new TestFile("test", Paths.get("src/main"));
        assertFail(() -> check(file).field(TestFile::getPath).is(c -> c.is("src/test")),
                "path ", path("src/main"), "src/test");
    }

    @Test
    public void fieldWithNameNarrowedToPathOk() {
        TestFile file = new TestFile("test", Paths.get("src/main"));
        check(file).field("path").is(c -> c.isPath().is("src/main"));
    }

    @Test
    public void isPathFail() {
        assertFail(() -> check((Object) "not a path").isPath());
    }

    @Test
    public void pathListSupplierElementOk() {
        List<Path> paths = Collections.singletonList(Paths.get("src/main"));
        checkOf(() -> paths)
                .at(0).is(c -> c.is("src/main"))
                .hasSize(1);
    }

    @Test
    public void pathListSupplierElementFail() {
        List<Path> paths = Collections.singletonList(Paths.get("src/main"));
        assertFail(() -> checkOf(() -> paths).at(0).is(c -> c.is("src/test")),
                "[0] ", path("src/main"), "src/test");
    }

    @Test
    public void pathArrayElementOk() {
        Path[] paths = {Paths.get("src/main")};
        check(paths).at(0).is(c -> c.is("src/main"));
    }

    private String path(String path) {
        return path.replace('/', File.separatorChar);
    }

    @Data
    private static class TestFile {

        private String name;
        private Path path;

        TestFile(String name, Path path) {
            this.name = name;
            this.path = path;
        }
    }
}
