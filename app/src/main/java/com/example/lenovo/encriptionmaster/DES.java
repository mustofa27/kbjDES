package com.example.lenovo.encriptionmaster;


import java.util.Arrays;


public class DES {

    /**
     * Input Permutation.
     */
    private static final byte[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    /**
     * Invers Initial Permutation.
     */
    private static final byte[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };


    /**
     * Expansion Permutation.
     */
    private static final byte[] E = {
            32, 1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9,  10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    /**
     * Substitution Boxes.
     */
    private static final byte[][] S = { {
            14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
            0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
            4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
            15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
    }, {
            15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
            3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
            0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
            13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
    }, {
            10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
            13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
            13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
            1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
    }, {
            7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
            13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
            10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
            3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
    }, {
            2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
            14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
            4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
            11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
    }, {
            12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
            10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
            9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
            4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
    }, {
            4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
            13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
            1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
            6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
    }, {
            13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
            1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
            7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
            2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
    } };

    /**
     * "P" Permutation.
     */
    private static final byte[] P = {
            16, 7,  20, 21,
            29, 12, 28, 17,
            1,  15, 23, 26,
            5,  18, 31, 10,
            2,  8,  24, 14,
            32, 27, 3,  9,
            19, 13, 30, 6,
            22, 11, 4,  25
    };

    // Permutations relating to subkey generation

    /**
     * PC1 Permutation.
     */
    private static final byte[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1,  58, 50, 42, 34, 26, 18,
            10, 2,  59, 51, 43, 35, 27,
            19, 11, 3,  60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7,  62, 54, 46, 38, 30, 22,
            14, 6,  61, 53, 45, 37, 29,
            21, 13, 5,  28, 20, 12, 4
    };

    /**
     * PC2 Permutation.
     */
    private static final byte[] PC2 = {
            14, 17, 11, 24, 1,  5,
            3,  28, 15, 6,  21, 10,
            23, 19, 12, 4,  26, 8,
            16, 7,  27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    /**
     * Subkey Rotations.
     */
    private static final byte[] rotations = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };


    private static long IP(long src)  { return permute(IP, 64, src);                 } // 64-bit output
    private static long FP(long src)  { return permute(FP, 64, src);                 } // 64-bit output
    private static long E(int src)    { return permute(E, 32, src&0xFFFFFFFFL);      } // 48-bit output
    private static int  P(int src)    { return (int)permute(P, 32, src&0xFFFFFFFFL); } // 32-bit output
    private static long PC1(long src) { return permute(PC1, 64, src);                } // 56-bit output
    private static long PC2(long src) { return permute(PC2, 56, src);                } // 48-bit output


    private static long permute(byte[] table, int srcWidth, long src) {
        long dst = 0;
        for (int i=0; i<table.length; i++) {
            int srcPos = srcWidth - table[i];
            dst = (dst<<1) | (src>>srcPos & 0x01);
        }
        return dst;
    }

    private static byte S(int boxNumber, byte src) {
        // The first and last bits determine which 16-value row to
        // reference, so we transform the 6-bit input into an
        // absolute index based on the following bit shuffle:
        // abcdef => afbcde
        src = (byte) (src&0x20 | ((src&0x01)<<4) | ((src&0x1E)>>1));
        return S[boxNumber-1][src];
    }

    /**
     * Untuk konversi 8 byte menjadi 64 bit dalam long
     */
    private static long getLongFromBytes(byte[] ba, int offset) {
        long l = 0;
        for (int i=0; i<8; i++) {
            byte value;
            if ((offset+i) < ba.length) {
                value = ba[offset+i];
            } else {
                value = 0;
            }
            l = l<<8 | (value & 0xFFL);
        }
        return l;
    }

    /**
     * Untuk konversi 64 bit dalam long menjadi 8 byte
     */
    private static void getBytesFromLong(byte[] ba, int offset, long l) {
        for (int i=7; i>=0; i--) {
            if ((offset+i) < ba.length) {
                ba[offset+i] = (byte) (l & 0xFF);
                l = l >> 8;
            } else {
                break;
            }
        }
    }

    private static int feistel(int r, long subkey) {
        // 1. expansion
        long e = E(r);
        // 2. key mixing
        long x = e ^ subkey;
        // 3. substitution
        int dst = 0;
        for (int i=0; i<8; i++) {
            dst>>>=4;
            int s = S(8-i, (byte)(x&0x3F));
            dst |= s << 28;
            x>>=6;
        }
        // 4. permutation
        return P(dst);
    }

    /**
     * Membuat 16 subkey 48-bit
     * value.
     */
    private static long[] createSubkeys(long key) {
        long subkeys[] = new long[16];

        // PC1 permutation
        key = PC1(key);

        // memisah 28-bit kiri dan kanan.
        int c = (int) (key>>28);
        int d = (int) (key&0x0FFFFFFF);

        for (int i=0; i<16; i++) {
            if (rotations[i] == 1) {
                c = ((c<<1) & 0x0FFFFFFF) | (c>>27);
                d = ((d<<1) & 0x0FFFFFFF) | (d>>27);
            } else {
                c = ((c<<2) & 0x0FFFFFFF) | (c>>26);
                d = ((d<<2) & 0x0FFFFFFF) | (d>>26);
            }

            long cd = (c&0xFFFFFFFFL)<<28 | (d&0xFFFFFFFFL);

            // PC2 permutation
            subkeys[i] = PC2(cd);
        }

        return subkeys;
    }


    public static long encryptBlock(long m, long key) {
        // membuat 16 subkey
        long subkeys[] = createSubkeys(key);

        // initial permutation
        long ip = IP(m);

        // memisah menjadi 32-bit kanan dan kiri.
        int l = (int) (ip>>32);
        int r = (int) (ip&0xFFFFFFFFL);

        for (int i=0; i<16; i++) {
            int previous_l = l;
            l = r;
            r = previous_l ^ feistel(r, subkeys[i]);
        }

        // swap left and right segment
        long rl = (r&0xFFFFFFFFL)<<32 | (l&0xFFFFFFFFL);
        //inverse permutation
        long fp = FP(rl);

        return fp;
    }

    public static void encryptBlock(
            byte[] message,
            int messageOffset,
            byte[] ciphertext,
            int ciphertextOffset,
            byte[] key
    ) {
        long m = getLongFromBytes(message, messageOffset);
        long k = getLongFromBytes(key, 0);
        long c = encryptBlock(m, k);
        getBytesFromLong(ciphertext, ciphertextOffset, c);
    }


    public static byte[] encrypt(byte[] message, byte[] key) {
        byte[] ciphertext = new byte[message.length];

        for (int i=0; i<message.length; i+=8) {
            encryptBlock(message, i, ciphertext, i, key);
        }

        return ciphertext;
    }

    public static byte[] encrypt(byte[] challenge, String password) {
        return encrypt(challenge, passwordToKey(password));
    }

    private static byte[] passwordToKey(String password) {
        byte[] pwbytes = password.getBytes();
        byte[] key = new byte[8];
        for (int i=0; i<8; i++) {
            if (i < pwbytes.length) {
                key[i] = pwbytes[i];
            } else {
                key[i] = 0;
            }
        }
        return key;
    }
}

