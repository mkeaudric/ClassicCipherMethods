package CipherClassic;

import java.util.Scanner;

public class VigenereCipher {
    // gw coba gabung jadi 1 fungsi decode dan encode (dengan parameter boolean utk decode/encode)
    // gtw cara ini bagus atau engga
    public static String substituteVigenere(String input, String key, boolean isEncoding){
        int i = 0, j = 0;

        // pake kapital aja
        input = input.toUpperCase();
        key = key.toUpperCase();

        StringBuilder sb = new StringBuilder();
        char cInput, cKey;

        for(i = 0; i < input.length(); i++){
            cInput = input.charAt(i);
            cKey = key.charAt(j);

            if(cInput == ' ') {
               sb.append(" ");
               continue;
            }

            if(isEncoding == true) 
                sb.append((char)(((cInput - 'A') + (cKey - 'A'))%26 + 'A'));
            else 
                sb.append((char)(((cInput - 'A') - (cKey - 'A') + 26)%26 + 'A'));
                // NOTE : +26 krn modulonya bisa negatif
                // pake Math.abs hasilnya salah (misal C-F mod 26, dia jadinya 3, padahal mestinya 23)
            j++;
            if(j == key.length()) j = 0; // key looping
        }

        return sb.toString();
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String key = sc.next(); // biasanya cuma 1 kalimat, jadi gw pake sc.next()
        
        sc.nextLine(); // gtw kenapa kemakan di loop kalo ga pake

        // pilih encode(1)/decode(2) 
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
            System.out.println(substituteVigenere(input, key, true));
        } else { // decode
            System.out.println(substituteVigenere(input, key, false));
        }

        sc.close();
    }
}
