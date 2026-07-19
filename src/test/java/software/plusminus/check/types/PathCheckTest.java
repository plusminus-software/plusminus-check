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

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static software.plusminus.check.Checks.check;
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

    private String path(String path) {
        return path.replace('/', File.separatorChar);
    }
}
