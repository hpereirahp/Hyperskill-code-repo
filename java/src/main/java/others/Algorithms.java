package others;

public class Algorithms {
	
	public static int jumpSearch(int[] array, int target) {
	    int currentRight = 0; // right border of the current block
	    int prevRight = 0; // right border of the previous block

	    /* If array is empty, the element is not found */
	    if (array.length == 0) {
	        return -1;
	    }

	    /* Check the first element */
	    if (array[currentRight] == target) {
	        return 0;
	    }

	    /* Calculating the jump length over array elements */
	    int jumpLength = (int) Math.sqrt(array.length);

	    /* Finding a block where the element may be present */
	    while (currentRight < array.length - 1) {

	        /* Calculating the right border of the following block */
	        currentRight = Math.min(array.length - 1, currentRight + jumpLength);

	        if (array[currentRight] >= target) {
	            break; // Found a block that may contain the target element
	        }

	        prevRight = currentRight; // update the previous right block border
	    }

	    /* If the last block is reached and it cannot contain the target value => not found */
	    if ((currentRight == array.length - 1) && target > array[currentRight]) {
	        return -1;
	    }

	    /* Doing linear search in the found block */
	    return backwardSearch(array, target, prevRight, currentRight);
	}

	private static int backwardSearch(int[] array, int target, int leftExcl, int rightIncl) {
	    for (int i = rightIncl; i > leftExcl; i--) {
	        if (array[i] == target) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	public static int[] bubbleSort(int[] array) {
	    for (int i = 0; i < array.length - 1; i++) {
	        for (int j = 0; j < array.length - i - 1; j++) {
	            /* if a pair of adjacent elements has the wrong order it swaps them */
	            if (array[j] > array[j + 1]) {
	                int temp = array[j];
	                array[j] = array[j + 1];
	                array[j + 1] = temp;
	            }
	        }
	    }

	    return array;
	}
	
	public static int binarySearch(int[] array, int elem, int left, int right) {
	    while (left <= right) {
	        int mid = left + (right - left) / 2; // the index of the middle element
	            
	        if (elem == array[mid]) {
	            return mid; // the element is found, return its index
	        } else if (elem < array[mid]) {
	            right = mid - 1; // go to the left subarray
	        } else {
	            left = mid + 1;  // go the the right subarray
	        }
	    }
	    return -1; // the element is not found
	}
	
	public static int binarySearchRecursive(int[] array, int elem, int left, int right) {
	    if (left > right) {
	        return -1; // search interval is empty, the element is not found
	    }
	        
	    int mid = left + (right - left) / 2; // the index of the middle element
	        
	    if (elem == array[mid]) {
	        return mid; // the element is found, return its index
	    } else if (elem < array[mid]) {
	        return binarySearchRecursive(array, elem, left, mid - 1); // go to the left subarray
	    } else {
	        return binarySearchRecursive(array, elem, mid + 1, right); // go the the right subarray    
	    }
	}
	
	public static void quickSort(int[] array, int left, int right) {
	    if (left < right) {
	        int pivotIndex = partition(array, left, right); // the pivot is already on its place
	        quickSort(array, left, pivotIndex - 1);  // sort the left subarray
	        quickSort(array, pivotIndex + 1, right); // sort the right subarray 
	    }
	}
	
	private static int partition(int[] array, int left, int right) {
	    int pivot = array[right];  // choose the rightmost element as the pivot
	    int partitionIndex = left; // the first element greater than the pivot

	    /* move large values into the right side of the array */
	    for (int i = left; i < right; i++) {
	        if (array[i] <= pivot) { // may be used '<' as well
	            swap(array, i, partitionIndex);
	            partitionIndex++;
	        }
	    }

	    swap(array, partitionIndex, right); // put the pivot on a suitable position

	    return partitionIndex;
	}

	private static void swap(int[] array, int i, int j) {
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	}

}
