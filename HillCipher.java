package CipherClassic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class HillCipher {
    static Queue<Integer> spaceIdx = new LinkedList<>();

    // return string tanpa space, dan sudah huruf besar
    public static String stringWithoutSpace(String str){
        StringBuilder sb = new StringBuilder();
        char c;

        for(int i = 0; i < str.length(); i++){
            c = str.charAt(i);
            if(c == ' '){
                spaceIdx.add(i);
                continue;
            } else{
                sb.append(Character.toUpperCase(c));
            }
        }

        return sb.toString();
    }

    // perkalian matriks dengan modulo
    public static int[][] matrixMultiplicationMod(int[][] plainTextMatrix, int[][] keyMatrix, int modulo){
        int resRow = plainTextMatrix.length;
        int resCol = keyMatrix[0].length;
        int[][] res = new int[resRow][resCol]; // axb X cxd = axd
        int i, j, k;

        for(i = 0; i < resRow; i++){
            for(j = 0; j < resCol; j++){
                for(k = 0; k < 3; k++){
                    res[i][j] = (res[i][j] + plainTextMatrix[i][k] * keyMatrix[k][j])%modulo;
                }
                res[i][j] = res[i][j]%modulo;
            }
        }

        return res;
    }

    // encode (dan decode)
    public static String encodeHill (String input, int[][] keyMatrix){
        int i, j, inputLen = input.length(); // original input length
        
        // panjang padding (berapa banyak X yg ditambahin supaya panjang inputnya 3n)
        int padding = (3 - (inputLen % 3)) % 3;  // bisa 0, 1, atau 2

        int totalLen = inputLen + padding;
        int row = totalLen / 3;
        int col = 3;
        
        int[][] plainTextMatrix = new int[row][col];
        int ct = 0;

        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                if(ct < inputLen) plainTextMatrix[i][j] = input.charAt(ct++) - 'A'; // hill chipher mod 26, jadi geser ASCII ke 0
                else plainTextMatrix[i][j] = 'X' - 'A'; 
            }
        }

        int[][] cipherTextMatrix = matrixMultiplicationMod(plainTextMatrix, keyMatrix, 26);
        
        StringBuilder sb = new StringBuilder();

        int curCol = 0, curRow = 0;
        for(i = 0; i < totalLen; i++){
            sb.append((char)(cipherTextMatrix[curRow][curCol]+'A'));
            curCol++;
            if(curCol == 3){
                curCol = 0;
                curRow++;
            }
        }

        for(int idx : spaceIdx){
            sb.insert(idx, " ");
        }

        return sb.toString();
    }

    // determinan 3x3 dgn mod
    public static int determinantMatrix3x3Mod(int[][] m, int mod) {
        int det = 
            m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1])
          - m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0])
          + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
        det %= mod;
        if (det < 0) det += mod;
        return det;
    }

    // Hitung kofaktor 3x3 dgn mod
    public static int[][] cofactorMatrix3x3Mod(int[][] m, int mod) {
        int[][] cof = new int[3][3];
        // kofaktor = (-1)^(i+j) * minor(i,j)
        cof[0][0] =  +(m[1][1]*m[2][2] - m[1][2]*m[2][1]);
        cof[0][1] = -(m[1][0]*m[2][2] - m[1][2]*m[2][0]);
        cof[0][2] =  +(m[1][0]*m[2][1] - m[1][1]*m[2][0]);

        cof[1][0] = -(m[0][1]*m[2][2] - m[0][2]*m[2][1]);
        cof[1][1] =  +(m[0][0]*m[2][2] - m[0][2]*m[2][0]);
        cof[1][2] = -(m[0][0]*m[2][1] - m[0][1]*m[2][0]);

        cof[2][0] =  +(m[0][1]*m[1][2] - m[0][2]*m[1][1]);
        cof[2][1] = -(m[0][0]*m[1][2] - m[0][2]*m[1][0]);
        cof[2][2] =  +(m[0][0]*m[1][1] - m[0][1]*m[1][0]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cof[i][j] %= mod;
                if (cof[i][j] < 0) cof[i][j] += mod;
            }
        }
        return cof;
    }

    // Adjoint = transpose(cofactor)
    public static int[][] adjointMatrix3x3Mod(int[][] m, int mod) {
        int[][] cof = cofactorMatrix3x3Mod(m, mod);
        int[][] adj = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                adj[i][j] = cof[j][i];  // transpose
        return adj;
    }

    // Inverse matrix 3x3 dgn mod
    public static int inverseMatrix3x3Mod(int a, int mod) {
        a %= mod; if (a < 0) a += mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1) return x;
        }
        throw new ArithmeticException("No modular inverse for " + a);
    }

    // 5. Inverse matrix modulo
    public static int[][] matrixInverse3x3Mod(int[][] m, int mod) {
        int det = determinantMatrix3x3Mod(m, mod);
        int invDet = inverseMatrix3x3Mod(det, mod);
        int[][] adj = adjointMatrix3x3Mod(m, mod);
        int[][] inv = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inv[i][j] = (adj[i][j] * invDet) % mod;
                if (inv[i][j] < 0) inv[i][j] += mod;
            }
        }
        return inv;
    }

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        input = stringWithoutSpace(input);

        // biasanya Hill cipher pake 3x3, 
        // tapi bisa berapapun nxn asal nanti inputnya disamain (tambahin biar bisa dibagi jadi n kolom)

        int[][] keyMatrix = new int[3][3];
        int i, j;

        for(i = 0; i < 3; i++){
            for(j = 0; j < 3; j++){
                keyMatrix[i][j] = sc.nextInt();
            }
        }

        String output = encodeHill(input, keyMatrix);

        System.out.println("Encode : " + output);

        spaceIdx.clear();
        output = stringWithoutSpace(output);
        System.out.println("Decode using inverse key : " + encodeHill(output, matrixInverse3x3Mod(keyMatrix, 26)));

        sc.close();
    }
}
