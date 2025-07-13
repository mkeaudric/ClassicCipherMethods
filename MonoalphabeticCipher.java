package CipherClassic;

import java.util.Scanner;
import java.util.HashMap;

// belum pakai exception utk karakter diluar alfabet
public class MonoalphabeticCipher {
    public static String Encrypt(String input, String key, boolean uppercase){
        HashMap<Character, Character> map = new HashMap<>();
        String ret = "";

        int inputLen = input.length();
        int keyLen = key.length();

        char cInput, cKey;

        if(uppercase){
            input = input.toUpperCase();
            key = key.toUpperCase();
        } else{
            input = input.toLowerCase();
            key = key.toLowerCase();
        }

        int i, j = 0;

        for(i = 0; i < inputLen; i++){
            cInput = input.charAt(i);
            
            // spasi
            if(cInput == ' ') {
                ret += ' ';
                continue;
            }

            // mapping
            if(j < keyLen){ // kalo key belum dimap semua
                // BTW ini input jadi key utk hashMap nya, jgn salah
                cKey = key.charAt(j);

                if(map.containsKey(cInput)){
                    ret += map.get(cInput);
                } else{
                    map.put(cInput, cKey);
                    ret += cKey;
                    j++;
                }
            } else{
                if(map.containsKey(cInput)){
                    ret += map.get(cInput);
                } else{
                    ret += cInput;
                }
            }
        }

        return ret;
    }

    public static String deleteDuplicates(String str){
        String ret = "";
        char c;
        str = str.toLowerCase();
        boolean used[] = new boolean[26];

        for(int i = 0; i < str.length(); i++){
            c = str.charAt(i);
            if(c == ' ') continue;
            
            if(used[c - 'a'] == false) ret += c;
            used[c - 'a'] = true;
        }

        return ret;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        // note input dan output lowercase
        String input = sc.nextLine();
        String key = sc.nextLine();

        key = deleteDuplicates(key);

        System.out.print((Encrypt(input, key, false))); 

        sc.close();
    }
}

/*
P : Attack postponed to tomorrow and do not use our secret paper until further info
K : The quick brown fox jumps over the lazy dog     ->     thequickbrownfxjmpsvlazydg
C : thhteq uichuikbr hi hioiwwin tkr ri kih fcb ifw cbewbh utubw fkhxj mfwhpbw xkmi 
 */
