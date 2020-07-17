package com.mediasoft.tm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] numbers = readArray();
        System.out.println("Minimum number: " + findMin(numbers));
        System.out.println("Maximum number: " + findMax(numbers));
    }

    private static int findMin(int[] array) {
        int min = array[0];
        for (int value : array) {
            min = Math.min(value, min);
        }
        return min;
    }

    private static int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            max = Math.max(value, max);
        }
        return max;
    }

    private static int[] readArray() {
        try {
            System.out.print("How many integers are you going to represent? ");
            Scanner scanner = new Scanner(System.in);
            int size = scanner.nextInt();
            if (size <= 0) {
                throw new RuntimeException();
            }
            int[] numbers = new int[size];
            System.out.print("Enter integers separated by spaces and press <Enter>: ");
            for (int i = 0; i < size; i++) {
                numbers[i] = scanner.nextInt();
            }
            return numbers;
        } catch (Exception ex) {
            System.out.println("An undefined error has occurred. The program is completed.");
            System.exit(-1);
        }
        return null;
    }
}
