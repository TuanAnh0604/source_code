import java.util.Arrays;

public class AlgorithmRuntimeComparison {
    // Linear Search O(n)
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // Binary Search O(log n)
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    // Bubble Sort O(n²)
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] inputSizes = {10_000, 50_000, 100_000}; // Example input sizes for testing
        int target = 50; // Target value to search

        for (int size : inputSizes) {
            System.out.println("\nInput Size: " + size);

            // Generate random array for testing
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (int) (Math.random() * size);
            }
            int[] sortedArr = arr.clone();
            Arrays.sort(sortedArr); // Binary search requires sorted input

            // Measure runtime of Linear Search
            long startTime = System.nanoTime();
            linearSearch(arr, target);
            long endTime = System.nanoTime();
            System.out.println("Linear Search (O(n)) Runtime: " + (endTime - startTime) + " ns");

            // Measure runtime of Binary Search
            startTime = System.nanoTime();
            binarySearch(sortedArr, target);
            endTime = System.nanoTime();
            System.out.println("Binary Search (O(log n)) Runtime: " + (endTime - startTime) + " ns");

            // Measure runtime of Bubble Sort
            int[] bubbleArr = arr.clone();
            startTime = System.nanoTime();
            bubbleSort(bubbleArr);
            endTime = System.nanoTime();
            System.out.println("Bubble Sort (O(n²)) Runtime: " + (endTime - startTime) + " ns");
        }
    }
}
