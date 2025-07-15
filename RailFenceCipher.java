package CipherClassic;

import java.util.Scanner;

public class RailFenceCipher {

    // hilangin spasi, toUpperCase
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

    public static String encodeRailFence(String input, int depth){
        int inputLen = input.length();
        char[][] railFence = new char[depth][inputLen];
        boolean direction = false; // true -> atas, false -> bawah
        int i = 0, j;

        for(j = 0; j < inputLen; j++){
            railFence[i][j] = input.charAt(j);

            if(direction == false){ // bawah
                i++;
                if(i == depth - 1) direction = true; // kalo udah di ujung bawah, ganti arah 
            } else{ // atas (true)
                i--;
                if(i == 0) direction = false;
            }
        }

        // debug(railFence);
        
        StringBuilder sb = new StringBuilder();

        for(i = 0; i < depth; i++){
            for(j = 0; j < inputLen; j++){
                sb.append(railFence[i][j]);
            } sb.append(" ");
        }

        return sb.toString();
    }

    public static String decodeRailFence(String input, int depth){
        String[] rowStrings = input.split(" ");
        int idxJump = 2*(depth-1);

        char[][] railFence = new char[depth][input.length() - (rowStrings.length-1)]; 
        // - (rowStrings.length-1) krn spasi juga kehitung di inputLen
        // rowStrings.length - 1 karena yg dihitung spasinya, bukan banyak stringnya, yg adalah banyak string - 1
        // contoh : TKVM HNYUEYUH AORC -> stringnya 3, spasinya 2

        char[] charactersInRow;
        int i, curRow; // curRow dan i itu sama" row saat ini, tapi i akan berubah untuk lompatan, jadi dipisah
        for(i = 0; i < rowStrings.length; i++){
            curRow = i;
            charactersInRow = rowStrings[i].toCharArray();
            int ct = 0; // utk alternasi lompatan
            for(char c : charactersInRow){
                // kalau kita ada di baris pertama dan terakhir, lompatannya ga alternasi (sepanjang idxJump terus)
                if(i == 0 || i == rowStrings.length - 1){
                    railFence[i][curRow] = c;
                    curRow += 2*(depth-1);
                } else{
                    // alternasi : idxJump-2 , idxJump - (idxJump-2)
                    // lalu iterasi berikutnya -2 lagi : idxJump-4, idxJump - (idxJump-4)
                    railFence[i][curRow] = c;
                    if(ct%2 == 0) curRow += idxJump;
                    else curRow += (2*(depth-1) - idxJump);
                    ct++;
                }
            }
            idxJump -= 2;
        }

        debug(railFence);

        int j;
        boolean direction = false;
        StringBuilder sb = new StringBuilder();
        // Kode ini sama spt encoding, jadi sebenernya bisa diwrap di method baru 
        for(j = 0, i = 0; j < railFence[0].length; j++){
            sb.append(railFence[i][j]);

            if(direction == false){ // bawah
                i++;
                if(i == depth - 1) direction = true; // kalo udah di ujung bawah, ganti arah 
            } else{ // atas (true)
                i--;
                if(i == 0) direction = false;
            }
        }

        return sb.toString();
    }

    public static void debug(char[][] array){
        int i, j;
        for(i = 0; i < array.length; i++){
            for(j = 0; j < array[0].length; j++){
                if(array[i][j] == '\u0000') System.out.print('-');
                else System.out.print(array[i][j]);
            } System.out.println();
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        int depth = sc.nextInt();
        
        System.out.println("1. Encode");
        System.out.println("2. Decode");
        int action;

        while (true) {
            String actionString = sc.nextLine();
            try {
                action = Integer.parseInt(actionString);
                if (action == 1 || action == 2) {
                    break; 
                } else {
                    System.out.println("Aksi yang bisa dipilih hanya 1 atau 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hanya bisa masukan angka");
            }
        }
        
        if(action == 1){
            // encode butuh ngilangin spasi, dan toUpperCase, lakukan di dalam initInput
            input = initInput(input);
            System.out.println(encodeRailFence(input, depth));
        } else{
            // kalo decode, butuh spasi utk pisahin, jadi toUpperCase aja
            System.out.println(decodeRailFence(input.toUpperCase(), depth));
        }

        sc.close();
    }
}
