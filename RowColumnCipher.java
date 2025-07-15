package CipherClassic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RowColumnCipher {
    public static String initInput(String input){
        StringBuilder sb = new StringBuilder();
        int inputLen = input.length();
        char c;

        for(int i = 0; i < inputLen; i++){
            c = input.charAt(i);
            if(c == ' ')
                continue;  
            else
                sb.append(Character.toUpperCase(c));
        }

        return sb.toString();
    }

    public static String generateKey(int n){
        // trik : Collections.shuffle
        List<Integer> nums = new ArrayList<>();
        for(int i = 1; i <= n; i++) nums.add(i);
        Collections.shuffle(nums);

        StringBuilder sb = new StringBuilder();
        for(int num : nums){
            sb.append(num);
        }
        return sb.toString();
    }
    
    private static String encodeRowColumn(String input, String key, int N, int M) {
        int i, j, inputLen = input.length();
        char[][] array = new char[N][M];

        int ct = 0;
        for(i = 0; i < N; i++){
            for(j = 0; j < M; j++){
                if(ct < inputLen){
                    array[i][j] = input.charAt(ct);
                    ct++;
                } else{
                    array[i][j] = ((char)((Math.random()*100) %26 + 'A'));
                }
            }
        }

        // print array
        for(i = 0; i < N; i++){
            for(j = 0; j < M; j++){
                System.out.print(array[i][j] + " ");
            } System.out.println();
        }

        return processCiphertext(array, key);
    }

    public static String processCiphertext(char[][] array, String key) {
        int keyLen = key.length();
        int rows = array.length;

        int[] colForOrder = new int[keyLen];
        for (int i = 0; i < keyLen; i++) {
            int order = key.charAt(i) - '1';
            colForOrder[order] = i;
        }

        StringBuilder sb = new StringBuilder();
        
        for (int order = 0; order < keyLen; order++) {
            int col = colForOrder[order];
            for (int row = 0; row < rows; row++) {
                sb.append(array[row][col]);
            }
        }

        return sb.toString();
    }

    private static String decodeRowColumn(String input, String key, int N, int M) {
        int i, j;
        char[][] array = new char[N][M];

        // key.length() = M, jadi pake M aja
        int ct = 0;
        for(j = 0; j < M; j++){
            for(i = 0; i < N; i++){
                array[i][j] = input.charAt(ct++);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(i = 0; i < N; i++){
            for(j = 0; j < M; j++){
                sb.append(array[i][key.charAt(j) - '1']);
            }
        }
        return sb.toString();
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        System.out.print("Masukan plaintext : ");
        String input = sc.nextLine();
        input = initInput(input);

        int inputLen = input.length();

        int N, M;

        while(true){
            System.out.println("Masukan ukuran matriks NxM");
            System.out.print("N : ");
            N = sc.nextInt();
            System.out.print("M : ");
            M = sc.nextInt();
            if(N*M >= inputLen) break;
            else System.out.println("NxM harus lebih besar/sama dengan dari panjang plaintext!");
        }

        String key = generateKey(M);
        System.out.println("Generated key : " + key);

        sc.nextLine();

        System.out.println("1. Encode");
        System.out.println("2. Decode");
        int action;
        while(true){
            String actionString = sc.nextLine();

            try {
                action = Integer.parseInt(actionString);
                if(action == 1 || action == 2){
                    break;
                } else{
                    System.out.println("Aksi yang bisa dipilih hanya 1 atau 2");
                }
            } catch (NumberFormatException e){
                System.out.println("Hanya bisa masukan angka");
            }
        }

        if(action == 1){ // encode
            System.out.println(encodeRowColumn(input, key, N, M));
        } else { // decode
            System.out.println(decodeRowColumn(input, key, N, M));
        }

        sc.close();
    }
}
