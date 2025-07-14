package CipherClassic;

import java.util.Scanner;

public class VernamCipher {
    public static String substituteVernam(String input, String key){
        int i = 0, j = 0;

        input = input.toUpperCase();
        key = key.toUpperCase();

        StringBuilder sb = new StringBuilder();
        char cInput, cKey;

        for(i = 0; i < input.length(); i++){
            cInput = input.charAt(i);
            cKey = key.charAt(j);

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

    // kalau masih mau output dalam range alfabet (0-25), bisa mapping sendiri (utk tampilan outputnya aja)
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
        String key = sc.next();

        // krn decode bener bener tinggal masukin lagi ciphertext, gaperlu bikin 2 parameter/fungsi berbeda
        String ciphertext = substituteVernam(input, key);
        System.out.println("Raw ciphertext : " + ciphertext);
        System.out.println("Alphabet range ciphertext : " + keepInsideAlphabetRange(ciphertext));
        sc.close();
    }
}
