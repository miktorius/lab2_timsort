package algorithm;

import java.util.Arrays;
import java.util.Random;
import structures.Pair;
import structures.Stack;

public class Timsort {

    public static String launch(int[] array) {
        int[] testArray = array.clone();
        Arrays.sort(testArray);
        timSort(array);
        assert Arrays.equals(testArray, array);
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

        // copying to temporary array
        int[] tempArray = new int[left.v2];
        for (int iter = 0, j = left.v1; iter < left.v2; iter++, j++) {
            tempArray[iter] = array[j];
        }

        // merging
        int ptrL = 0;
        int ptrR = right.v1;
        int leftLen = left.v2;
        int rightLen = right.v1 + right.v2;
        int i, j;
        int seriesCount = 0;
        boolean lastElemFlag = true; // true - left, false - right
        for (i = left.v1; ptrL < leftLen && ptrR < rightLen;) {
            // check if galloping mode needed
            if (seriesCount >= 7) {
                if (lastElemFlag == true) {
                    j = binarySearch(tempArray, ptrL, leftLen, array[ptrR]);
                    while (ptrL != j) {
                        array[i++] = tempArray[ptrL++];
                    }
                } else {
                    j = binarySearch(array, ptrR, rightLen, tempArray[ptrL]);
                    while (ptrR != j) {
                        array[i++] = array[ptrR++];
                    }
                }
                seriesCount = 0;
                continue;
            }
            // collecting remaining elements if any
            if (tempArray[ptrL] < array[ptrR]) {
                array[i] = tempArray[ptrL++];
                if (lastElemFlag == false) {
                    lastElemFlag = true;
                    seriesCount = 0;
                }
                seriesCount++;
            } else {
                array[i] = array[ptrR++];
                if (lastElemFlag == true) {
                    lastElemFlag = false;
                    seriesCount = 0;
                }
                seriesCount++;
            }
            i++;
        }

        // copying remaining elements
        while (ptrL < leftLen) {
            array[i++] = tempArray[ptrL++];
        }
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
            runs[runCount][1] = (i >= N - minrun) ? N - i : Math.max(runSize, minrun); // run size control
            insertionSort(array, runs[runCount][0], runs[runCount][0] + runs[runCount][1]); // sorting current run

            i += Math.max(runSize, minrun); // next step preparations
            runCount++;
            runSize = 1;
        }

        // Step 2 : Merging.

        Stack<Pair<Integer>> stack = new Stack<Pair<Integer>>();
        Pair<Integer> runX, runY, runZ;
        int X, Y, Z;

        // runs processing
        for (i = 0; i < runCount; i++) {
            stack.enqueue(new Pair<Integer>(runs[i][0], runs[i][1]));

            // updating variables
            runX = stack.peek(0);
            runY = stack.getSize() >= 2 ? stack.peek(1) : null;
            runZ = stack.getSize() >= 3 ? stack.peek(2) : null;
            X = runX.v2;
            Y = runY != null ? runY.v2 : 0;
            Z = runZ != null ? runZ.v2 : 0;

            // invariants checking
            while (true) {
                if (runY != null && runZ != null) {
                    if (stack.getSize() >= 2 && (Y <= X || i == runCount - 1)) {
                        merge(array, runY, runX);
                        stack.dequeue();
                        stack.dequeue();
                        stack.enqueue(new Pair<Integer>(runY.v1, Y + X));

                        // updating variables
                        runX = stack.peek(0);
                        runY = stack.getSize() >= 2 ? stack.peek(1) : null;
                        runZ = stack.getSize() >= 3 ? stack.peek(2) : null;
                        X = runX.v2;
                        Y = runY != null ? runY.v2 : 0;
                        Z = runZ != null ? runZ.v2 : 0;
                        continue;
                    }
                    if (stack.getSize() >= 3 && Z <= X + Y) {
                        if (Z < X) {
                            merge(array, runZ, runY);
                            runX = stack.dequeue();
                            stack.dequeue();
                            stack.dequeue();
                            stack.enqueue(new Pair<Integer>(runZ.v1, Z + Y));
                            stack.enqueue(runX);
                        } else {
                            merge(array, runY, runX);
                            stack.dequeue();
                            stack.dequeue();
                            stack.enqueue(new Pair<Integer>(runY.v1, X + Y));
                        }

                        // updating variables
                        runX = stack.peek(0);
                        runY = stack.getSize() >= 2 ? stack.peek(1) : null;
                        runZ = stack.getSize() >= 3 ? stack.peek(2) : null;
                        X = runX.v2;
                        Y = runY != null ? runY.v2 : 0;
                        Z = runZ != null ? runZ.v2 : 0;
                        continue;
                    }
                } else if (runY != null && runZ == null) {
                    if (Y <= X || i == runCount - 1) {
                        merge(array, runY, runX);
                        stack.dequeue();
                        stack.dequeue();
                        stack.enqueue(new Pair<Integer>(runY.v1, X + Y));

                        // updating variables
                        runX = stack.peek(0);
                        runY = stack.getSize() >= 2 ? stack.peek(1) : null;
                        runZ = stack.getSize() >= 3 ? stack.peek(2) : null;
                        X = runX.v2;
                        Y = runY != null ? runY.v2 : 0;
                        Z = runZ != null ? runZ.v2 : 0;
                    }
                }
                break;
            }
        }
    }

    private static int binarySearch(int[] array, int l, int r, int x) {
        int m = -1;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (m == r) {
                break;
            }
            if (array[m] == x) {
                return m;
            }
            if (array[m] < x) {
                l = m + 1;

            } else {
                r = m - 1;
            }
            
        }
        return m;
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
        while (N >= 4) {
            r |= (N & 1);
            N >>= 1;
        }
        return N + r;
    }

    public static int[] generateRandomArray(int arraySize) {
        var rand = new Random();
        var randomArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = rand.nextInt(1000);
        }
        return randomArray;
    }
    
    public static int[] generateGallopTestArray(int size, int range1Start, int range1End, int range2Start, int range2End) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            if (rand.nextBoolean()) {
                // Generate a random value in the first range (1-10)
                array[i] = rand.nextInt(range1End - range1Start + 1) + range1Start;
            } else {
                // Generate a random value in the second range (20-30)
                array[i] = rand.nextInt(range2End - range2Start + 1) + range2Start;
            }
        }
        return array;
    }
}
