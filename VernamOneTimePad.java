package CipherClassic;

import java.util.Scanner;

public class VernamOneTimePad {
    public static String generateRandom(int length){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            sb.append((char)((Math.random()*100) %26 + 'A'));
        }
        return sb.toString();
    }

    public static String substituteVernam(String input){
        int i = 0, j = 0;

        input = input.toUpperCase();
        int inputLen = input.length();
        String key = generateRandom(inputLen);
        System.out.println("Generated key : " + key);

        StringBuilder sb = new StringBuilder();
        char cInput, cKey;

        for(i = 0; i < inputLen; i++){
            cInput = input.charAt(i);
            cKey = key.charAt(i);

            if(cInput == ' '){
                sb.append(" ");
            } else{
                sb.append((char)(((cInput-'A') ^ (cKey-'A')) + 'A'));
                // NOTE : Vernam XOR gabisa dimodulo, jadi akan ada karakter aneh diluar alfabet yg muncul
                // range dari (cInput-'A') ^ (cKey-'A') sendiri 0-31, sementara karakter alfabet cuma 0-25
                j++;
                if(j == key.length()) j = 0;
            }
        }

        return sb.toString();
    }

    public static String keepInsideAlphabetRange(String str){
        StringBuilder sb = new StringBuilder(str);
        int c;

        for(int i = 0; i < str.length(); i++){
            c = str.charAt(i) - 'A';

            if(c > 25){
                sb.replace(i, i+1, (char)(c%26 + 'A') + "");
            }
        }

        return sb.toString();
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine(); 

        String ciphertext = substituteVernam(input);
        System.out.println("Raw ciphertext : " + ciphertext);
        System.out.println("Alphabet range ciphertext : " + keepInsideAlphabetRange(ciphertext));
        sc.close();
    }
}
