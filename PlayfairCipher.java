package CipherClassic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PlayfairCipher {
    static Queue<Integer> spaceIdx = new LinkedList<>();

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

        for(Integer idx : spaceIdx){
            sb.insert(idx, " ");
        }

        return sb.toString();
    }

    // decode sama persis, yang beda cuma kasus geser kiri dan geser atas
    public static String decodeInput (String input, String key){
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

            if(c1_j == c2_j){ // geser ke kiri (modulo di java tetep negatif, jadi abis -1, + dividennya)
                sb.append(playfairMatrix[c1_j][(c1_k - 1 + 5)%5]);
                sb.append(playfairMatrix[c2_j][(c2_k - 1 + 5)%5]);
            } else if (c1_k == c2_k){ // geser ke atas
                sb.append(playfairMatrix[(c1_j - 1 + 5)%5][c1_k]);
                sb.append(playfairMatrix[(c2_j - 1 + 5)%5][c2_k]);
            } else{ // swap horizontal
                // kasus c1_j != c2_j && c1_k != c2_k
                // gaada kasus c1_j == c2_j && c1_k == c2_k, karena dobel udah diilangin pas inisialisasi
                sb.append(playfairMatrix[c1_j][c2_k]);
                sb.append(playfairMatrix[c2_j][c1_k]);
            }
        }

        for(Integer idx : spaceIdx){
            sb.insert(idx, " ");
        }

        return sb.toString();
    }

    // hitung panjang string tanpa spasi, 
    // simpen indeks kemunculan spasi,
    // return string input tanpa spasi,
    // juga trim dan toUpperCase
    public static String stringWithoutSpace(Queue<Integer> spaceIdx, String str){
        str = str.trim().toUpperCase();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == ' ') {
                spaceIdx.add(i);
                continue;
            } else{
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    // - ubah J -> I, 
    // - ubah huruf doble (misal BB -> BX), 
    // - dan tambahkan X (atau Z) jika length input ganjil
    public static String initInput(String input){
        StringBuilder sb = new StringBuilder(stringWithoutSpace(spaceIdx, input));
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

        System.out.println("1. Encode");
        System.out.println("2. Decode");
        int action;

        while(true){
            try {
                action = sc.nextInt();
            } catch (Exception e){
                System.out.println("Hanya bisa masukan angka");
                continue;
            }
            if(action != 1 && action != 2){
                System.out.println("Aksi yang bisa dipilih hanya 1 atau 2");
            } else{
                break;
            }
        }
        
        if(action == 1){
            System.out.println(encodeInput(input, key));
        } else{
            System.out.println(decodeInput(input, key));
        }

        sc.close();
    }
}