package CipherClassic;

import java.util.Scanner;

public class CaesarCipher {
    public static String cipher(String input, int shift){
        // note : kata dosen StringBuilder lebih cepat, krn String biasa tiap kali +=, bikin String baru
        String ret = "";
        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            if(Character.isUpperCase(c)){
                ret += (char)((c - 'A' + shift) % 26 + 'A');
            } else if (Character.isLowerCase(c)){
                ret += (char)((c - 'a' + shift) % 26 + 'a');
            } else{ // spasi
                ret += ' ';
            }
        }
        return ret;
    }
    
    // kenapa (c - 'a' + shift) % 26 + 'a',
    // c - 'a' akan mengubah indeks alfabet dari 0 sampai 25 (karena tadinya a = ASCII value of 97)
    // lalu geser dengan shift, (c - 'a' + shift)
    // modulo dengan 26 agar wrap around saat melebihi, (c - 'a' + shift) % 26
    // ini valuenya karena dari 0, harus dijumlahkan lagi agar sama, (c - 'a' + shift) % 26 + 'a'
    // jadi - 'a' dan + 'a' itu karena kita ubah dulu jadi mulai dari 0 indeksnya (agar bisa di mod 26)
    // , baru jumlahin lagi agar sesuai yg sebenarnya

    public static void testAll26Characters (String input){
        for(int i = 1; i < 26; i++){
            System.out.println(i + " : " + cipher(input, i));
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        testAll26Characters(input);

        sc.close();
    }
}
