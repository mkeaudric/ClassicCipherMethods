package CipherClassic;

import java.util.Scanner;

public class FrequencyAnalysis {

    static String[] engFreq = new String[10];

    static {
        // Sumber: web online & buku "Codes and Ciphers" by D'Agapeyeff, A
        engFreq[0] = "ESIARNTOLCDUPMGHBYFVKWZXQJ"; // all words in dictionary
        engFreq[1] = "ETAOINSRHDLUCMFYWGPBVKXQJZ"; // default (paling umum)
        engFreq[2] = "ETRINOASDLCHFUPMYGWVBXKQJZ"; // (/100000)
        engFreq[3] = "EAOIDHNRSTUY";                // Edgar Allan Poe freq
        engFreq[4] = "ETAONIRSHDLCWUM";             // Kerckhoff freq
        engFreq[5] = "ETIONASH";                    // Versi de Romanini
        engFreq[6] = "ETOANIRSHDLUCMPFYWGBVKJXZQ";  // Normal freq
        engFreq[7] = "EOANIRSTDLHUCMPYFGWBVKXJQZ";  // Telegraph freq
        engFreq[8] = "ETAISONHRDLUCMFWYPGBVKJQXZ";  // Thomas freq
        engFreq[9] = "ETOANIRSHDLCFUMPYWGBVKXJQZ";  // Valerio freq (/1000)
    }

    public static String subCharacterByFreq(String input, String freqMask) {
        input = input.toUpperCase(); // Samakan kapital
        freqMask = freqMask.toUpperCase();

        // Hitung frekuensi huruf di input
        int[] freq = new int[26];
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                freq[c - 'A']++;
            }
        }

        // Simpan huruf" dalam input dan frekuensinya
        Character[] chars = new Character[26];
        for (int i = 0; i < 26; i++) chars[i] = (char) ('A' + i);

        // Urutkan berdasarkan frekuensi menurun
        java.util.Arrays.sort(chars, (a, b) -> Integer.compare(freq[b - 'A'], freq[a - 'A']));

        // Buat mapping: huruf input -> huruf pada freqMask
        char[] mapping = new char[26];
        for (int i = 0; i < 26; i++) {
            if (freq[chars[i] - 'A'] == 0) break; // Stop kalau frekuensinya 0
            if (i < freqMask.length()) {
                mapping[chars[i] - 'A'] = freqMask.charAt(i);
            } else {
                mapping[chars[i] - 'A'] = chars[i]; // fallback ke dirinya sendiri
            }
        }

        String ret = "";
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                ret += mapping[c - 'A'];
            } else {
                ret += c; // karakter selain huruf tetap
            }
        }

        return ret;
    }

    public static String switchCharacters(String input, char c1, char c2) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c1) chars[i] = c2;
            else if (chars[i] == c2) chars[i] = c1; // ini mestinya ngambil char freq tertinggi di tabel 
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input
        System.out.print("Masukan input: ");
        String input = sc.nextLine();
        String freqTable; // utk isi manual

        // Pilih Frequency table
        while (true) {
            System.out.println("Pilih Frequency Table:");
            for (int i = 0; i < engFreq.length; i++) {
                System.out.printf("%d : %s%n", i + 1, engFreq[i]);
            }
            System.out.println("Lainnya : isi manual");

            int idx = sc.nextInt() - 1;

            if (idx < 0) {
                System.out.println("Input harus angka positif!");
                continue;
            } else {
                if (idx < 10) input = subCharacterByFreq(input, engFreq[idx]);
                else {
                    System.out.print("frequency table manual : ");
                    freqTable = sc.next();
                    input = subCharacterByFreq(input, freqTable);
                }
                break;
            }
        }

        while (true) {
            System.out.println("\n" + input);
            System.out.println("1. Tukar karakter");
            System.out.println("2. Ganti Frequency Table");
            System.out.println("3. Selesai");

            int aksi = sc.nextInt();

            if (aksi == 1) {
                System.out.print("karakter yang mau diganti : ");
                char c1 = sc.next().charAt(0);

                System.out.print("karakter yang menggantikan : ");
                char c2 = sc.next().charAt(0);

                input = switchCharacters(input, c1, c2);

            } else if (aksi == 2) {
                while (true) {
                    System.out.println("Pilih Frequency Table:");
                    for (int i = 0; i < engFreq.length; i++) {
                        System.out.printf("%d : %s%n", i + 1, engFreq[i]);
                    }
                    System.out.println("Lainnya : isi manual");

                    int idx = sc.nextInt() - 1;

                    if (idx < 0) {
                        System.out.println("Input harus positif");
                        continue;
                    } else {
                        if (idx < 10) input = subCharacterByFreq(input, engFreq[idx]);
                        else {
                            System.out.print("Masukkan Frequency table: ");
                            freqTable = sc.next();
                            input = subCharacterByFreq(input, freqTable);
                        }
                        break;
                    }
                }
            } else if (aksi == 3) {
                break;
            } else {
                System.out.println("aksi harus angka 1-3");
            }
        }
        sc.close();
    }
}

/*
CONTOH :

Masukan input: thhteq uichuikbr hi hioiwwin tkr ri kih fcb ifw cbewbh utubw fkhxj mfwhpbw xkmi
Pilih Frequency Table:
1 : ESIARNTOLCDUPMGHBYFVKWZXQJ
2 : ETAOINSRHDLUCMFYWGPBVKXQJZ
3 : ETRINOASDLCHFUPMYGWVBXKQJZ
4 : EAOIDHNRSTUY
5 : ETAONIRSHDLCWUM
6 : ETIONASH
7 : ETOANIRSHDLUCMPFYWGBVKJXZQ
8 : EOANIRSTDLHUCMPYFGWBVKXJQZ
9 : ETAISONHRDLUCMFWYPGBVKJQXZ
10 : ETOANIRSHDLCFUMPYWGBVKXJQZ
Lainnya : isi manual
9

NTTNLP HERTHESID TE TEWEAAEF NSD DE SET ORI EOA RILAIT HNHIA OSTCM UOATYIA CSUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : N
karakter yang menggantikan : A

ATTALP HERTHESID TE TEWENNEF ASD DE SET ORI EON RILNIT HAHIN OSTCM UONTYIN CSUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : L
karakter yang menggantikan : C

ATTACP HERTHESID TE TEWENNEF ASD DE SET ORI EON RICNIT HAHIN OSTLM UONTYIN LSUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : P
karakter yang menggantikan : K

ATTACK HERTHESID TE TEWENNEF ASD DE SET ORI EON RICNIT HAHIN OSTLM UONTYIN LSUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : S
karakter yang menggantikan : N

ATTACK HERTHENID TE TEWESSEF AND DE NET ORI EOS RICSIT HAHIS ONTLM UOSTYIS LNUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : H
karakter yang menggantikan : P

ATTACK PERTPENID TE TEWESSEF AND DE NET ORI EOS RICSIT PAPIS ONTLM UOSTYIS LNUE
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : E
karakter yang menggantikan : O

ATTACK PORTPONID TO TOWOSSOF AND DO NOT ERI OES RICSIT PAPIS ENTLM UESTYIS LNUO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : R
karakter yang menggantikan : S

ATTACK POSTPONID TO TOWORROF AND DO NOT ESI OER SICRIT PAPIR ENTLM UERTYIR LNUO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : I
karakter yang menggantikan : E

ATTACK POSTPONED TO TOWORROF AND DO NOT ISE OIR SECRET PAPER INTLM UIRTYER LNUO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : F
karakter yang menggantikan : W

ATTACK POSTPONED TO TOFORROW AND DO NOT ISE OIR SECRET PAPER INTLM UIRTYER LNUO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : I
karakter yang menggantikan : U

ATTACK POSTPONED TO TOFORROW AND DO NOT USE OUR SECRET PAPER UNTLM IURTYER LNIO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : L
karakter yang menggantikan : I

ATTACK POSTPONED TO TOFORROW AND DO NOT USE OUR SECRET PAPER UNTIM LURTYER INLO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : F
karakter yang menggantikan : M

ATTACK POSTPONED TO TOMORROW AND DO NOT USE OUR SECRET PAPER UNTIF LURTYER INLO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : F
karakter yang menggantikan : L

ATTACK POSTPONED TO TOMORROW AND DO NOT USE OUR SECRET PAPER UNTIL FURTYER INFO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
1
karakter yang mau diganti : Y
karakter yang menggantikan : H

ATTACK POSTPONED TO TOMORROW AND DO NOT USE OUR SECRET PAPER UNTIL FURTHER INFO
1. Tukar karakter
2. Ganti Frequency Table
3. Selesai
3
 */