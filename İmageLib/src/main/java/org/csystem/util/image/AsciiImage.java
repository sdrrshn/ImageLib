package org.csystem.util.image;

import com.sun.jdi.event.StepEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Stream;

public class AsciiImage {
    private final char[][] m_image;


    private void fillImage(Path path) throws IOException {
        try (var br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                int lenght = line.length();
                for (int k = 0; k < lenght; k++) {
                    m_image[i][k] = line.charAt(k);

                }
                i++;
            }

        }

    }

    public void floodFillRecur(int i, int k, char c, char bound) {
        if (m_image[i][k] == c || m_image[i][k] == bound)
            return;
        m_image[i][k] = c;
        floodFillRecur(i + 1, k, c, bound);
        floodFillRecur(i - 1, k, c, bound);
        floodFillRecur(i, k + 1, c, bound);
        floodFillRecur(i, k - 1, c, bound);

    }

    public AsciiImage(int row, int col) {
        m_image = new char[row][col];

    }

    public int getRow() {
        return m_image.length;
    }

    public int getCol() {
        return m_image[0].length;
    }

    public void setChar(int i, int k, char c) {
        m_image[i][k] = c;
    }

    public void loadImage(String path) {
        Arrays.stream(m_image).forEach(array -> Arrays.fill(array, ' '));
    }

    public void floodFill(int i, int k, char c, char bound) {
        floodFillRecur(i, k, c, bound);
    }
    public void saveImage(String path)throws IOException {
        try(var bw =Files.newBufferedWriter(Path.of(path)
                ,StandardCharsets.US_ASCII, StandardOpenOption.TRUNCATE_EXISTING)){
            bw.write(toString());
            bw.flush();
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var chars : m_image) {
            for (char c : chars) {
                sb.append(c);
            }
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
