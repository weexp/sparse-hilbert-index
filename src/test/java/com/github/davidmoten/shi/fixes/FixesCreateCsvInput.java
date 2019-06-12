package com.github.davidmoten.shi.fixes;

import static com.github.davidmoten.shi.fixes.Fixes.input;
import static com.github.davidmoten.shi.fixes.Fixes.ser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;

import com.github.davidmoten.bigsorter.Reader;

public class FixesCreateCsvInput {

    public static void main(String[] args) throws IOException {
        try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("target/input.csv")))) {
            Reader<byte[]> r = ser.createReader(new BufferedInputStream(new FileInputStream(input)));
            while (true) {
                byte[] b = r.read();
                if (b == null) {
                    break;
                } else {
                    ByteBuffer bb = ByteBuffer.wrap(b);
                    int mmsi = bb.getInt();
                    float lat = bb.getFloat();
                    float lon = bb.getFloat();
                    long time = bb.getLong();
                    bb.position(bb.position() + 8);
                    float speedKnots = bb.getShort() / 10.0f;
                    float cog = bb.getShort() / 10.0f;
                    float heading = bb.getShort() / 10.0f;
                    out.format("%s,%s,%s,%s,%s,%s,%s\n", mmsi, lat, lon, time, speedKnots, cog, heading);
                }
            }
        }
    }

}