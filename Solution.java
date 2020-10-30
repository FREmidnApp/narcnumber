package com.javarush.task.task20.task2025;

import java.util.*;

/* 
Алгоритмы-числа
*/

public class Solution {
    static int[][] powTab = new int[9][64];

    static {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 64; j++) {
                powTab[i - 1][j - 1] = (int) Math.pow(i, j);
            }
        }
    }

    public static boolean isNarc(long sum, long min, int[][] powTab, int exp) {
        if (sum < min) return false;
        long cand = 0;
        for (long n = sum; n > 0; n /= 10) {
            int num = (int) (n % 10);
            if (num == 0) continue;
            cand += powTab[num - 1][exp - 1];
        }
        return cand == sum;
    }

    public static long getSumPow(int[][] powTab, int exp, ArrayList<Byte> ab) {
        long sum = 0;
        for (Byte aByte : ab) {
            sum += powTab[aByte - 1][exp - 1];
        }
        return sum;
    }

    public static long[] getNumbers(long N) {
        ArrayList<Long> resAsList = new ArrayList<>();
        resAsList.add((long) 0);
        LinkedList<ArrayList<Byte>> combL = new LinkedList<>();
        for (int i = 1; i <= 9 && i < N; i++) {
            resAsList.add((long) i);
            ArrayList<Byte> ab = new ArrayList<>();
            ab.add((byte) i);
            combL.addLast(ab);
        }
        int len = (int) Math.log10(N);
        long min1 = 1, min2;
        for (int exp = 2; exp <= len; exp++) {
            min1 = min1 * 10 + 1;
            min2 = min1 * 10 + 1;
            int size = combL.size();
            for (int i = 0; i < size; i++) {
                ArrayList<Byte> ab = combL.pollFirst();
                long sum01 = getSumPow(powTab, exp, ab),
                        sum02 = exp != len ? getSumPow(powTab, exp + 1, ab) : 0;
                for (byte k = ab.get(ab.size() - 1); k <= 9; k++) {
                    long sum1 = sum01 + powTab[k - 1][exp - 1];
                    if (sum1 >= N) continue;
                    ArrayList<Byte> comb = new ArrayList<>(ab);
                    comb.add(k);
                    combL.addLast(comb);
                    if (isNarc(sum1, min1, powTab, exp)) resAsList.add(sum1);
                    long sum2 = exp != len ? sum02 + powTab[k - 1][exp] : 0;
                    if (isNarc(sum2, min2, powTab, exp + 1)) resAsList.add(sum2);
                }
            }
        }
        return resAsList.stream().mapToLong(Long::longValue).sorted().toArray();
    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(1000000000)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);

        /*a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(1000000)));
        b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);*/
    }
}
