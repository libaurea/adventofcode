package de.libaurea.adventofcode.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public abstract class Solution {
    protected static final Logger LOGGER = new Logger();
    protected List<String> input;
    protected List<String> param;

    public Solution debug() {
        LOGGER.toggleDebug();
        return this;
    }

    public Solution with(String file) {
        return with(file, "");
    }

    public Solution with(String file, String... param) {
        this.input = read(file);
        this.param = Arrays.asList(param);
        return this;
    }

    public abstract void solve();

    protected static List<Integer> toIntegerList(List<String> list) {
        return list.stream().map(Integer::parseInt).toList();
    }

    private List<String> read(String file) {
        List<String> result = null;
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(file)) {
            if (stream == null) {
                LOGGER.error("File \'" + file + "\' not found!");
            }
            result = new BufferedReader(new InputStreamReader(stream)).lines().toList();
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
        return result;
    }
}
