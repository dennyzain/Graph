import java.util.Arrays;
public class Assignment2 {
    public static void mergeSort(int[] arr) {
        if(arr.length > 1) {

            int pivot = arr.length / 2;

            int[] left = Arrays.copyOfRange(arr, 0, pivot);
            int[] right = Arrays.copyOfRange(arr, pivot, arr.length);

            mergeSort(left);
            mergeSort(right);

            merge(arr, left, right);
        }
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while(i < left.length && j < right.length) {
            if(left[i] < right[j]) {
                arr[k++] = left[i++];
            }else{
                arr[k++] = right[j++];
            }
        }

        while(i < left.length) {arr[k++] = left[i++];}
        while(j < right.length) {arr[k++] = right[j++];}
    }

    public static void countingSort(int[] arr) {
        int min = Arrays.stream(arr).min().getAsInt();
        int max = Arrays.stream(arr).max().getAsInt();

        int[] count = new int[max - min + 1];
        int[] output = new int[arr.length];

        for (int num : arr) {
            count[num - min]++;
        }

        for (int i = count.length - 2; i >= 0; i--) {
            count[i] += count[i + 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }


        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    public static void main(String[] args) {
        int[] numbers = {33,22,55,44,11};
        System.out.println("Original: " + Arrays.toString(numbers));
        countingSort(numbers);
        System.out.println("Sorted: " + Arrays.toString(numbers));
    }
}


