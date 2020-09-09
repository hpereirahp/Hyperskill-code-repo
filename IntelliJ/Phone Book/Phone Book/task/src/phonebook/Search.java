package phonebook;

import java.util.List;

public class Search {

    public static boolean linearSearch(List<Person> list, String value) {

        for (Person person : list) {
            if (person.getFullName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getDurationToPrint(long duration) {
        int minutes = (int) (duration / (1000 * 60));
        int seconds = (int) (duration / 1000 - minutes * 60);
        int miliseconds = (int) (duration - seconds * 1000);

        return String.format("%d min. %d sec. %d ms.", minutes, seconds, miliseconds);
    }

    public static boolean bubbleSort(List<Person> list) {

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    Person temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
            if (i % 1000 == 0 && i != 0) {
                // it takes too long
//                System.out.printf("%d of %d%n", i, sortedPhoneBook.size());
                return false;
            }
        }
        return true;
    }

    public static boolean bubbleSort(String[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            if (i % 1000 == 0) {
                // it takes too long
                return false;
            }
        }
        return true;
    }

    public static int jumpSearch(List<Person> list, String name) {

        if (list.size() == 0) {
            return -1;
        }
        if (list.get(0).getFullName().equals(name)) {
            return 0;
        }

        int currentPos = 0;
        int previousPos = 0;
        int jumpLength = (int) Math.sqrt(list.size());

        while (currentPos < list.size() - 1) {
            currentPos = Math.min(currentPos + jumpLength, list.size() - 1);

            if (list.get(currentPos).getFullName().compareTo(name) >= 0) {
                break;
            }

            previousPos = currentPos;
        }

        if (previousPos == list.size() - 1) {
            return -1;
        }

        for (int i = currentPos; i > previousPos; i--) {
            if (list.get(i).getFullName().equals(name)) {
                return i;
            }
        }
        return -1;
    }


    public static int jumpSearch(String[] array, String name) {

        if (array.length == 0) {
            return -1;
        }
        if (array[0].equals(name)) {
            return 0;
        }

        int currentPos = 0;
        int previousPos = 0;
        int jumpLength = (int) Math.sqrt(array.length);

        while (currentPos < array.length - 1) {
            currentPos = Math.min(currentPos + jumpLength, array.length - 1);

            if (array[currentPos].compareTo(name) >= 0) {
                break;
            }

            previousPos = currentPos;
        }

        if (previousPos == array.length - 1) {
            return -1;
        }

        for (int i = currentPos; i > previousPos; i--) {
            if (array[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void quickSort(List<Person> list, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(list, left, right);
            quickSort(list, left, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, right);
        }
    }

    private static int partition(List<Person> list, int left, int right) {
        Person pivot = list.get(right);
        int partitionIndex = left;

        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivot) <= 0) {
                swap(list, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(list, partitionIndex, right);

        return partitionIndex;
    }

    private static void swap(List<Person> list, int i, int j) {
        Person temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static int binarySearch(List<Person> list, String value) {

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (list.get(mid).getFullName().equals(value)) {
                return mid;
            } else if (value.compareTo(list.get(mid).getFullName()) < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}
