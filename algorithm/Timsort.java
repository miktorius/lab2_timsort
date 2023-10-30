package algorithm;

import java.util.Arrays;
import java.util.Random;

import structures.Pair;
import structures.Stack;

public class Timsort {

    public static String launch(int[] array) {
        timSort(array);
        return Arrays.toString(array);
    }

    private static void insertionSort(int[] array, int l, int r) {
        int value;
        int i;
        for (int j = l + 1; j < r; j++) {
            value = array[j];
            i = j - 1;
            while (i >= l && array[i] > value) {
                array[i + 1] = array[i];
                array[i] = value;
                i--;
            }
        }
    }

    private static void merge(int[] array, Pair<Integer> left, Pair<Integer> right) {

    }

    private static void timSort(int[] array) {

        // Step 0 : Minrun calculation.

        int N = array.length;
        int minrun = getMinrun(N);

        // Step 1 : Partitioning and sorting subarrays.

        int[][] runs = new int[N / minrun + 1][2];
        int runCount = 0;
        int runSize = 1;
        int i = 0;
        int j;

        while (i < N) { // subsequence searching
            runs[runCount][0] = i;
            j = i + 1;
            if (j != N && array[j - 1] <= array[j]) {
                while (j != N && array[j - 1] <= array[j]) {
                    runSize++;
                    j++;
                }
            } else if (j != N && array[j - 1] > array[j]) {
                while (j != N && array[j - 1] > array[j]) {
                    runSize++;
                    j++;
                }
                reverseSubSequence(array, i, i + runSize - 1); // reverse descending subsequence
            }
            runs[runCount][1] = (i >= N - minrun) ? N - i : Math.max(runSize, minrun); // run size
                                                                                       // control
            insertionSort(array, runs[runCount][0], runs[runCount][0] + runs[runCount][1]); // sorting current run

            i += Math.max(runSize, minrun); // next step preparations
            runCount++;
            runSize = 1;
        }

        // Step 3 : Merging.
        Stack<Pair<Integer>> stack = new Stack<Pair<Integer>>();
        Pair<Integer> runX, runY, runZ;
        int X, Y, Z;
        for (i = 0; i < runCount; i++) {
            stack.enqueue(new Pair<Integer>(runs[i][0], runs[i][1]));

            runX = stack.dequeue();
            runY = stack.getSize() >= 2 ? stack.dequeue() : null;
            runZ = stack.getSize() >= 3 ? stack.dequeue() : null;

            X = runX.v2;
            Y = runY != null ? runY.v2 : null;
            Z = runZ != null ? runZ.v2 : null;

            while (true) {

                break;
            }
        }
    }

    private static void updateXYZ(Stack<Pair<Integer>> s,
                                  Pair<Integer> rX, Pair<Integer> rY, Pair<Integer> rZ,
                                  int X, int Y, int Z) {
        rX = s.dequeue();
        rY = s.getSize() >= 2 ? s.dequeue() : null;
        rZ = s.getSize() >= 3 ? s.dequeue() : null;
    }

    private static void reverseSubSequence(int[] array, int l, int r) {
        int d = (r - l + 1) / 2;
        for (int i = 0; i < d; i++) {
            int t = array[l + i];
            array[l + i] = array[r - i];
            array[r - i] = t;
        }
    }

    private static int getMinrun(int N) {
        int r = 0;
        while (N >= 64) {
            r |= (N & 1);
            N >>= 1;
        }
        return N + r;
    }

    public static int[] generateArray(int arraySize) {
        var rand = new Random();
        var randomArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = rand.nextInt(1000);
        }
        return randomArray;
    }
}
