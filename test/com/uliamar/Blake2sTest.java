package com.uliamar;


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Pol on 31/1/16.
 */
public class Blake2sTest {

    @Test
    public void test() {
        File f = new File("res/blake2stest.txt");
        try {
            Scanner s = new Scanner(f);
//            boolean b = s.hasNext();
            while (s.hasNext()) {
                s.next(); //in
                String input = s.next();
                s.next(); //out
                String expectedOutput = s.next();
                byte[] in = hexStringToByteArray(input);
                Blake2s blake2s = new Blake2s(null, null);
                blake2s.update(in, null, null);
                byte[] digest = blake2s.digest();
                byte[] expected = hexStringToByteArray(expectedOutput);
                if (Arrays.equals(digest, expected)) {
                    System.out.println("good");
                    assertTrue(true);
                } else {
                    System.err.println("bad");
                    System.err.println("input: " + input);
                    System.err.println("expected: " + expectedOutput);
                    System.err.println("found: " + byteArrayToHexString(digest));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKeyed() {
        File f = new File("/Users/Pol/Desktop/blakejs/blake2stestkey.txt");
        try {
            Scanner s = new Scanner(f);
            boolean isFirstInput = true;
            while (s.hasNext()) {
                s.next(); //in
                String input;
                if (isFirstInput) {
                    isFirstInput = false;
                    input = null;
                } else {
                    input = s.next();
                }
                byte[] in = input == null ? new byte[0] : hexStringToByteArray(input);
                s.next(); //key
                String key = s.next();
                s.next(); //out
                String expectedOutput = s.next();
                Blake2s blake2s = new Blake2s(null, hexStringToByteArray(key));
                blake2s.update(in, null, null);
                byte[] digest = blake2s.digest();
                byte[] expected = hexStringToByteArray(expectedOutput);
                if (Arrays.equals(digest, expected)) {
                    System.out.println("good");
                    assertTrue(true);
                } else {
                    System.err.println("bad");
                    System.err.println("input: " + input);
                    System.err.println("expected: " + expectedOutput);
                    System.err.println("found: " + byteArrayToHexString(digest));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dump(byte[] s) {
        for (byte b : s) {
            System.out.print((int) (b & 0xFF));
        }
        System.out.print("\n");
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] b) {
        int len = b.length;
        String data = new String();

        for (int i = 0; i < len; i++){
            data += Integer.toHexString((b[i] >> 4) & 0xf);
            data += Integer.toHexString(b[i] & 0xf);
        }
        return data;
    }
}