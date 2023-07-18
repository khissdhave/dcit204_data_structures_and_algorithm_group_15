import java.util.Scanner;

public class SearchAndSort {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of elements in the array: ");
            int n = scanner.nextInt();

            int[] arr = new int[n];
            System.out.println("Enter the elements of the array: ");
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
                    index = LinearSearch.linearSearch(arr, key);
                    break;
                case 2:
                    // Array must be sorted for binary search, so let's sort it first
                    MergeSort.mergeSort(arr, 0, n - 1);
                    index = BinarySearch.binarySearch(arr, 0, n - 1, key);
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
            System.out.println("4. Selection Sort");
            System.out.println("5. Quick Sort");
            System.out.println("6. Radix Sort");
            System.out.print("Enter your choice: ");
            int sortChoice = scanner.nextInt();

            switch (sortChoice) {
                case 1:
                    MergeSort.mergeSort(arr, 0, n - 1);
                    System.out.println("Array sorted using Merge Sort.");
                    break;
                case 2:
                    BubbleSort.bubbleSort(arr);
                    System.out.println("Array sorted using Bubble Sort.");
                    break;
                case 3:
                    InsertionSort.insertionSort(arr);
                    System.out.println("Array sorted using Insertion Sort.");
                    break;
                case 4:
                    SelectionSort.selectionSort(arr);
                    System.out.println("Array sorted using Selection Sort.");
                    break;
                case 5:
                    QuickSort.quickSort(arr, 0, n - 1);
                    System.out.println("Array sorted using Quick Sort.");
                    break;
                case 6:
                    RadixSort.radixSort(arr);
                    System.out.println("Array sorted using Radix Sort.");
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
