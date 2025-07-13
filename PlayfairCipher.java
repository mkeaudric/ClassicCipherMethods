package CipherClassic;

import java.util.Scanner;

public class PlayfairCipher {
    public static String deleteDuplicatesAndJ(String str){
        // tambahan, delete J kalau ada (pakenya I)
        // juga jadiin uppercase

        String ret = "";
        char c;
        str = str.toUpperCase();
        boolean used[] = new boolean[26];

        for(int i = 0; i < str.length(); i++){
            c = str.charAt(i);
            if(c == ' ' || c == 'J') continue;
            
            if(used[c - 'A'] == false) ret += c;
            used[c - 'A'] = true;
        }

        return ret;
    }

    public static char[][] constructPlayfairMatrix (String key){
        char[][] playfairMatrix = new char[5][5];
        int i, j, k, ct = 0, keyLen = key.length();

        boolean alphabet[] = new boolean[26];

        for(i = 0; i < 5; i++){
            for(j = 0; j < 5; j++){
                if(ct < keyLen){
                    char c = key.charAt(ct++);
                    playfairMatrix[i][j] = c;
                    alphabet[c - 'A'] = true;
                    continue;
                }
                for(k = 0; k < 26; k++){
                    char curAlp = (char)(k + 'A');

                    if(curAlp == 'J') continue; // i/j dipake sekali di playfair

                    if(alphabet[k] == false) {
                        playfairMatrix[i][j] = curAlp;
                        alphabet[curAlp - 'A'] = true;
                        break;
                    }
                }
            }
        }
        return playfairMatrix;
    }

    public static String encodeInput (String input, String key){
        int i, j, k, inputLen = input.length();

        StringBuilder sb = new StringBuilder();
        
        char[][] playfairMatrix = constructPlayfairMatrix(key);

        for(i = 0; i < inputLen; i += 2){
            char c1 = input.charAt(i);
            char c2 = input.charAt(i+1);

            int c1_j, c1_k, c2_j, c2_k;
            c1_j = c1_k = c2_j = c2_k = 0;

            for(j = 0; j < 5; j++){
                for(k = 0; k < 5; k++){
                    if(playfairMatrix[j][k] == c1) {
                        c1_j = j;
                        c1_k = k;
                    }
                    if(playfairMatrix[j][k] == c2) {
                        c2_j = j;
                        c2_k = k;
                    }
                }
            }

            if(c1_j == c2_j){ // geser ke kanan
                sb.append(playfairMatrix[c1_j][(c1_k + 1)%5]);
                sb.append(playfairMatrix[c2_j][(c2_k + 1)%5]);
            } else if (c1_k == c2_k){ // geser ke bawah
                sb.append(playfairMatrix[(c1_j + 1)%5][c1_k]);
                sb.append(playfairMatrix[(c2_j + 1)%5][c2_k]);
            } else{ // swap horizontal
                // kasus c1_j != c2_j && c1_k != c2_k
                // gaada kasus c1_j == c2_j && c1_k == c2_k, karena dobel udah diilangin pas inisialisasi
                sb.append(playfairMatrix[c1_j][c2_k]);
                sb.append(playfairMatrix[c2_j][c1_k]);
            }
        }

        return sb.toString();
    }

    // public static String decodeInput (String input, String key){
    //     key = deleteDuplicatesAndJ(key);
        
    //     char[][] playfairMatrix = constructPlayfairMatrix(key);

    //     for(int i = 0; i < input.length(); i += 2){

    //     }

    //     return " ";
    // }

    // - kapitalisasi huruf, 
    // - ubah J -> I, 
    // - ubah huruf doble (misal BB -> BX), 
    // - dan tambahkan X (atau Z) jika length input ganjil
    public static String initInput(String input){
        StringBuilder sb = new StringBuilder(input.toUpperCase().trim());
        int inputLen = sb.length();

        if(inputLen%2 == 1) {
            if(sb.charAt(inputLen - 1) != 'X'){
                sb.append('X');
            } else{
                // ada kasus juga utk ABX -> AB XX, ini X juga harus diganti jadi suatu huruf lain (gw pake Z)
                sb.append('Z');
            }
        }

        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) == 'J'){
                sb.insert(i, 'I');
            }

            // kalo i > 0 (artinya bisa cek belakang), dan bagian yg dobel" (ABCDE -> AB CD EX ....) : indeks i ke 1, 3, 5, ...
            if(i > 0 && (i-1)%2 == 0){
                if(sb.charAt(i-1) == sb.charAt(i)){
                    if(sb.charAt(i) != 'X'){
                        sb.insert(i, 'X');
                    } else{ 
                        sb.insert(i, 'Z');
                    }
                }
            }   
        }

        return sb.toString();
    }

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        
        String input = sc.nextLine();
        input = initInput(input);

        String key = sc.nextLine();
        key = deleteDuplicatesAndJ(key);

        System.out.println(encodeInput(input, key));

        sc.close();
    }
}