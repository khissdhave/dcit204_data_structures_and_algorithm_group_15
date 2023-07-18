import java.util.Scanner;


public class SearchAndSort {
    // Function for linear search
    public static int linearSearch(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i;  // Return the index of the found element
            }
        }
        return -1;  // Return -1 if the element is not found
    }

    // Function for binary search (array must be sorted in ascending order)
    public static int binarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == key) {
                return mid;  // Return the index of the found element
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;  // Return -1 if the element is not found
    }

    // Function for merge sort
    public static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    // Function for bubble sort
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

    // Function for insertion sort
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of elements in the array: ");
            int n = scanner.nextInt();

            int[] arr = new int[n];
            System.out.print("Enter the elements of the array: ");
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }

            System.out.print("Enter the element to search for: ");
            int key = scanner.nextInt();

            System.out.println("Select the search algorithm:");
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            long startTime = System.nanoTime();  // Start the timer

            int index;
            switch (choice) {
                case 1:
                    index = linearSearch(arr, key);
                    break;
                case 2:
                    // Array must be sorted for binary search, so let's sort it first
                    mergeSort(arr, 0, n - 1);
                    index = binarySearch(arr, 0, n - 1, key);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            long endTime = System.nanoTime();  // Stop the timer
            long duration = endTime - startTime;  // Calculate the duration

            if (index != -1) {
                System.out.println("Element found at index: " + index);
            } else {
                System.out.println("Element not found in the array.");
            }

            System.out.println("Runtime: " + duration + " nanoseconds");

            System.out.println("Select the sorting algorithm:");
            System.out.println("1. Merge Sort");
            System.out.println("2. Bubble Sort");
            System.out.println("3. Insertion Sort");
            System.out.print("Enter your choice: ");
            int sortChoice = scanner.nextInt();

            switch (sortChoice) {
                case 1:
                    mergeSort(arr, 0, n - 1);
                    System.out.println("Array sorted using Merge Sort.");
                    break;
                case 2:
                    bubbleSort(arr);
                    System.out.println("Array sorted using Bubble Sort.");
                    break;
                case 3:
                    insertionSort(arr);
                    System.out.println("Array sorted using Insertion Sort.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.print("Sorted array: ");
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i] + " ");
            }
        }

        System.out.println();
    }
}

