package project.encryptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

final class aesConstants{
    public static final byte s_box[] = {
        /*                  0     1             2            3               4                5            6              7                8           9            a          b                c               d              e              f */
        /* 0 */  (byte)  0x63, (byte)  0x7c, (byte)  0x77, (byte)  0x7b, (byte)  0xf2, (byte)  0x6b, (byte)  0x6f, (byte)  0xc5, (byte)  0x30, (byte)  0x01, (byte)  0x67, (byte)  0x2b, (byte)  0xfe, (byte)  0xd7, (byte)  0xab, (byte)  0x76,
        /* 1 */  (byte)  0xca, (byte)  0x82, (byte)  0xc9, (byte)  0x7d, (byte)  0xfa, (byte)  0x59, (byte)  0x47, (byte)  0xf0, (byte)  0xad, (byte)  0xd4, (byte)  0xa2, (byte)  0xaf, (byte)  0x9c, (byte)  0xa4, (byte)  0x72, (byte)  0xc0,
        /* 2 */  (byte)  0xb7, (byte)  0xfd, (byte)  0x93, (byte)  0x26, (byte)  0x36, (byte)  0x3f, (byte)  0xf7, (byte)  0xcc, (byte)  0x34, (byte)  0xa5, (byte)  0xe5, (byte)  0xf1, (byte)  0x71, (byte)  0xd8, (byte)  0x31, (byte)  0x15,
        /* 3 */  (byte)  0x04, (byte)  0xc7, (byte)  0x23, (byte)  0xc3, (byte)  0x18, (byte)  0x96, (byte)  0x05, (byte)  0x9a, (byte)  0x07, (byte)  0x12, (byte)  0x80, (byte)  0xe2, (byte)  0xeb, (byte)  0x27, (byte)  0xb2, (byte)  0x75,
        /* 4 */  (byte)  0x09, (byte)  0x83, (byte)  0x2c, (byte)  0x1a, (byte)  0x1b, (byte)  0x6e, (byte)  0x5a, (byte)  0xa0, (byte)  0x52, (byte)  0x3b, (byte)  0xd6, (byte)  0xb3, (byte)  0x29, (byte)  0xe3, (byte)  0x2f, (byte)  0x84,
        /* 5 */  (byte)  0x53, (byte)  0xd1, (byte)  0x00, (byte)  0xed, (byte)  0x20, (byte)  0xfc, (byte)  0xb1, (byte)  0x5b, (byte)  0x6a, (byte)  0xcb, (byte)  0xbe, (byte)  0x39, (byte)  0x4a, (byte)  0x4c, (byte)  0x58, (byte)  0xcf,
        /* 6 */  (byte)  0xd0, (byte)  0xef, (byte)  0xaa, (byte)  0xfb, (byte)  0x43, (byte)  0x4d, (byte)  0x33, (byte)  0x85, (byte)  0x45, (byte)  0xf9, (byte)  0x02, (byte)  0x7f, (byte)  0x50, (byte)  0x3c, (byte)  0x9f, (byte)  0xa8,
        /* 7 */  (byte)  0x51, (byte)  0xa3, (byte)  0x40, (byte)  0x8f, (byte)  0x92, (byte)  0x9d, (byte)  0x38, (byte)  0xf5, (byte)  0xbc, (byte)  0xb6, (byte)  0xda, (byte)  0x21, (byte)  0x10, (byte)  0xff, (byte)  0xf3, (byte)  0xd2,
        /* 8 */  (byte)  0xcd, (byte)  0x0c, (byte)  0x13, (byte)  0xec, (byte)  0x5f, (byte)  0x97, (byte)  0x44, (byte)  0x17, (byte)  0xc4, (byte)  0xa7, (byte)  0x7e, (byte)  0x3d, (byte)  0x64, (byte)  0x5d, (byte)  0x19, (byte)  0x73,
        /* 9 */  (byte)  0x60, (byte)  0x81, (byte)  0x4f, (byte)  0xdc, (byte)  0x22, (byte)  0x2a, (byte)  0x90, (byte)  0x88, (byte)  0x46, (byte)  0xee, (byte)  0xb8, (byte)  0x14, (byte)  0xde, (byte)  0x5e, (byte)  0x0b, (byte)  0xdb,
        /* a */  (byte)  0xe0, (byte)  0x32, (byte)  0x3a, (byte)  0x0a, (byte)  0x49, (byte)  0x06, (byte)  0x24, (byte)  0x5c, (byte)  0xc2, (byte)  0xd3, (byte)  0xac, (byte)  0x62, (byte)  0x91, (byte)  0x95, (byte)  0xe4, (byte)  0x79,
        /* b */  (byte)  0xe7, (byte)  0xc8, (byte)  0x37, (byte)  0x6d, (byte)  0x8d, (byte)  0xd5, (byte)  0x4e, (byte)  0xa9, (byte)  0x6c, (byte)  0x56, (byte)  0xf4, (byte)  0xea, (byte)  0x65, (byte)  0x7a, (byte)  0xae, (byte)  0x08,
        /* c */  (byte)  0xba, (byte)  0x78, (byte)  0x25, (byte)  0x2e, (byte)  0x1c, (byte)  0xa6, (byte)  0xb4, (byte)  0xc6, (byte)  0xe8, (byte)  0xdd, (byte)  0x74, (byte)  0x1f, (byte)  0x4b, (byte)  0xbd, (byte)  0x8b, (byte)  0x8a,
        /* d */  (byte)  0x70, (byte)  0x3e, (byte)  0xb5, (byte)  0x66, (byte)  0x48, (byte)  0x03, (byte)  0xf6, (byte)  0x0e, (byte)  0x61, (byte)  0x35, (byte)  0x57, (byte)  0xb9, (byte)  0x86, (byte)  0xc1, (byte)  0x1d, (byte)  0x9e,
        /* e */  (byte)  0xe1, (byte)  0xf8, (byte)  0x98, (byte)  0x11, (byte)  0x69, (byte)  0xd9, (byte)  0x8e, (byte)  0x94, (byte)  0x9b, (byte)  0x1e, (byte)  0x87, (byte)  0xe9, (byte)  0xce, (byte)  0x55, (byte)  0x28, (byte)  0xdf,
        /* f */  (byte)  0x8c, (byte)  0xa1, (byte)  0x89, (byte)  0x0d, (byte)  0xbf, (byte)  0xe6, (byte)  0x42, (byte)  0x68, (byte)  0x41, (byte)  0x99, (byte)  0x2d, (byte)  0x0f, (byte)  0xb0, (byte)  0x54, (byte)  0xbb, (byte)  0x16,
        };

    public static final byte s_box_inverse[] =  {
        /*          0     1     2     3     4     5     6     7     8     9     a     b     c     d     e     f */
        /* 0 */  (byte)  0x52, (byte)  0x09, (byte)  0x6a, (byte)  0xd5, (byte)  0x30, (byte)  0x36, (byte)  0xa5, (byte)  0x38, (byte)  0xbf, (byte)  0x40, (byte)  0xa3, (byte)  0x9e, (byte)  0x81, (byte)  0xf3, (byte)  0xd7, (byte)  0xfb,
        /* 1 */  (byte)  0x7c, (byte)  0xe3, (byte)  0x39, (byte)  0x82, (byte)  0x9b, (byte)  0x2f, (byte)  0xff, (byte)  0x87, (byte)  0x34, (byte)  0x8e, (byte)  0x43, (byte)  0x44, (byte)  0xc4, (byte)  0xde, (byte)  0xe9, (byte)  0xcb,
        /* 2 */  (byte)  0x54, (byte)  0x7b, (byte)  0x94, (byte)  0x32, (byte)  0xa6, (byte)  0xc2, (byte)  0x23, (byte)  0x3d, (byte)  0xee, (byte)  0x4c, (byte)  0x95, (byte)  0x0b, (byte)  0x42, (byte)  0xfa, (byte)  0xc3, (byte)  0x4e,
        /* 3 */  (byte)  0x08, (byte)  0x2e, (byte)  0xa1, (byte)  0x66, (byte)  0x28, (byte)  0xd9, (byte)  0x24, (byte)  0xb2, (byte)  0x76, (byte)  0x5b, (byte)  0xa2, (byte)  0x49, (byte)  0x6d, (byte)  0x8b, (byte)  0xd1, (byte)  0x25,
        /* 4 */  (byte)  0x72, (byte)  0xf8, (byte)  0xf6, (byte)  0x64, (byte)  0x86, (byte)  0x68, (byte)  0x98, (byte)  0x16, (byte)  0xd4, (byte)  0xa4, (byte)  0x5c, (byte)  0xcc, (byte)  0x5d, (byte)  0x65, (byte)  0xb6, (byte)  0x92,
        /* 5 */  (byte)  0x6c, (byte)  0x70, (byte)  0x48, (byte)  0x50, (byte)  0xfd, (byte)  0xed, (byte)  0xb9, (byte)  0xda, (byte)  0x5e, (byte)  0x15, (byte)  0x46, (byte)  0x57, (byte)  0xa7, (byte)  0x8d, (byte)  0x9d, (byte)  0x84,
        /* 6 */  (byte)  0x90, (byte)  0xd8, (byte)  0xab, (byte)  0x00, (byte)  0x8c, (byte)  0xbc, (byte)  0xd3, (byte)  0x0a, (byte)  0xf7, (byte)  0xe4, (byte)  0x58, (byte)  0x05, (byte)  0xb8, (byte)  0xb3, (byte)  0x45, (byte)  0x06,
        /* 7 */  (byte)  0xd0, (byte)  0x2c, (byte)  0x1e, (byte)  0x8f, (byte)  0xca, (byte)  0x3f, (byte)  0x0f, (byte)  0x02, (byte)  0xc1, (byte)  0xaf, (byte)  0xbd, (byte)  0x03, (byte)  0x01, (byte)  0x13, (byte)  0x8a, (byte)  0x6b,
        /* 8 */  (byte)  0x3a, (byte)  0x91, (byte)  0x11, (byte)  0x41, (byte)  0x4f, (byte)  0x67, (byte)  0xdc, (byte)  0xea, (byte)  0x97, (byte)  0xf2, (byte)  0xcf, (byte)  0xce, (byte)  0xf0, (byte)  0xb4, (byte)  0xe6, (byte)  0x73,
        /* 9 */  (byte)  0x96, (byte)  0xac, (byte)  0x74, (byte)  0x22, (byte)  0xe7, (byte)  0xad, (byte)  0x35, (byte)  0x85, (byte)  0xe2, (byte)  0xf9, (byte)  0x37, (byte)  0xe8, (byte)  0x1c, (byte)  0x75, (byte)  0xdf, (byte)  0x6e,
        /* a */  (byte)  0x47, (byte)  0xf1, (byte)  0x1a, (byte)  0x71, (byte)  0x1d, (byte)  0x29, (byte)  0xc5, (byte)  0x89, (byte)  0x6f, (byte)  0xb7, (byte)  0x62, (byte)  0x0e, (byte)  0xaa, (byte)  0x18, (byte)  0xbe, (byte)  0x1b,
        /* b */  (byte)  0xfc, (byte)  0x56, (byte)  0x3e, (byte)  0x4b, (byte)  0xc6, (byte)  0xd2, (byte)  0x79, (byte)  0x20, (byte)  0x9a, (byte)  0xdb, (byte)  0xc0, (byte)  0xfe, (byte)  0x78, (byte)  0xcd, (byte)  0x5a, (byte)  0xf4,
        /* c */  (byte)  0x1f, (byte)  0xdd, (byte)  0xa8, (byte)  0x33, (byte)  0x88, (byte)  0x07, (byte)  0xc7, (byte)  0x31, (byte)  0xb1, (byte)  0x12, (byte)  0x10, (byte)  0x59, (byte)  0x27, (byte)  0x80, (byte)  0xec, (byte)  0x5f,
        /* d */  (byte)  0x60, (byte)  0x51, (byte)  0x7f, (byte)  0xa9, (byte)  0x19, (byte)  0xb5, (byte)  0x4a, (byte)  0x0d, (byte)  0x2d, (byte)  0xe5, (byte)  0x7a, (byte)  0x9f, (byte)  0x93, (byte)  0xc9, (byte)  0x9c, (byte)  0xef,
        /* e */  (byte)  0xa0, (byte)  0xe0, (byte)  0x3b, (byte)  0x4d, (byte)  0xae, (byte)  0x2a, (byte)  0xf5, (byte)  0xb0, (byte)  0xc8, (byte)  0xeb, (byte)  0xbb, (byte)  0x3c, (byte)  0x83, (byte)  0x53, (byte)  0x99, (byte)  0x61,
        /* f */  (byte)  0x17, (byte) 0x2b,  (byte)  0x04, (byte)  0x7e, (byte)  0xba, (byte)  0x77, (byte)  0xd6, (byte)  0x26, (byte)  0xe1, (byte)  0x69, (byte)  0x14, (byte)  0x63, (byte)  0x55, (byte)  0x21, (byte)  0x0c, (byte)  0x7d,
        };
    
    public static final byte mixColumns[][] = {  {(byte) 0x02,(byte) 0x03,(byte) 0x01,(byte) 0x01},
                                                {(byte) 0x01,(byte) 0x02,(byte) 0x03,(byte) 0x01},
                                                {(byte) 0x01,(byte) 0x01,(byte) 0x02,(byte) 0x03},
                                                {(byte) 0x03,(byte) 0x01,(byte) 0x01,(byte) 0x02}
                                            };

    public static final byte invMixColumns[][] = {
                                                    {(byte) 0x0E, (byte) 0x0B, (byte) 0x0D, (byte) 0x09},
                                                    {(byte) 0x09, (byte) 0x0E, (byte) 0x0B, (byte) 0x0D},
                                                    {(byte) 0x0D, (byte) 0x09, (byte) 0x0E, (byte) 0x0B},
                                                    {(byte) 0x0B, (byte) 0x0D, (byte) 0x09, (byte) 0x0E}
                                                };

    public static final byte[] rcon = {
        (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10, (byte)0x20, (byte)0x40,
        (byte)0x80, (byte)0x1B, (byte)0x36
    };

    private aesConstants(){};
}

public class aes {

     // GF(2⁸) multiplication functions in a utility class
     public static byte gfMultiply(byte a, byte b) {
        if (b == 0x01) {
            return a;
        }

        byte result = 0;
        for (int i = 0; i < 8; ++i) {
            if ((b & (1 << i)) != 0) {
                result ^= a;
            }
            boolean highBitSet = (a & 0x80) != 0;
            a <<= 1;

            if (highBitSet) {
                a ^= 0x11B;
            }
        }
        return result;
    }
    
    public void subBytes(byte[][] state) {
        for(int i=0;i<state.length;i++)
        {
            for(int j=0;j<state[0].length;j++)
            {
                int index_s_box = state[i][j] & 0xff;
                state[i][j] = aesConstants.s_box[index_s_box];
            }
        }
    }

    public void invSubBytes(byte[][] state) {
        for(int i=0;i<state.length;i++)
        {
            for(int j=0;j<state[0].length;j++)
            {
                int index_s_box = state[i][j] & 0xff;
                state[i][j] = aesConstants.s_box_inverse[index_s_box];
            }
        }
    }
    
    public void shiftRows(byte[][] state) {
        for(int i=0;i<state.length;i++)
        {
            for(int k=0;k<i;k++)
            {
                byte first = state[i][0];
                for(int j=0;j<3;j++)
                    state[i][j] = state[i][j+1];
                state[i][3] = first;
            }
        }
    }

    public void invShiftRows(byte[][] state) {
        for(int i=0;i<state.length;i++)
        {
            for(int k=0;k<i;k++)
            {
                byte last = state[i][3];
                for(int j=3;j>0;j--)
                    state[i][j] = state[i][j-1];
                state[i][0] = last;
            }
        }
    }
    
    public void mixColumns(byte[][] state) {
        byte temp[] = { 0, 0, 0, 0 };
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
                temp[j] = (byte)(gfMultiply(aesConstants.mixColumns[j][0], state[0][i]) ^ 
                                gfMultiply(aesConstants.mixColumns[j][1], state[1][i]) ^ 
                                gfMultiply(aesConstants.mixColumns[j][2], state[2][i]) ^ 
                                gfMultiply(aesConstants.mixColumns[j][3], state[3][i]));

            for(int j=0;j<4;j++)
                state[j][i] = temp[j];
        }
    }

    public void invMixColumns(byte[][] state) {
        byte temp[] = { 0, 0, 0, 0 };
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
                temp[j] = (byte)(gfMultiply(aesConstants.invMixColumns[j][0], state[0][i]) ^ 
                                gfMultiply(aesConstants.invMixColumns[j][1], state[1][i]) ^ 
                                gfMultiply(aesConstants.invMixColumns[j][2], state[2][i]) ^ 
                                gfMultiply(aesConstants.invMixColumns[j][3], state[3][i]));

            for(int j=0;j<4;j++)
                state[j][i] = temp[j];
        }
    }
    
    public void addRoundKey(byte[][] state, byte[][] roundKey) {
        for(int i=0;i<state.length;i++)
            for(int j=0;j<state[0].length;j++)
                state[i][j] ^= roundKey[i][j];
    }
    
    public byte[][][] expandKey(byte[] key) {
        byte[][][] roundKeys = new byte[11][4][4];
        
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                roundKeys[0][i][j] = key[i * 4 +j];

        for(int i=1;i<=10;i++)
        {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    roundKeys[i][j][k] = roundKeys[i-1][j][k];
                }
            }

            byte temp[] = new byte[4];

            for(int j = 0; j < 4; j++) {
                temp[j] = roundKeys[i-1][j][3];
            }
            
            byte t = temp[0];
            for (int j = 0; j < 3; j++) {
                temp[j] = temp[j+1];
            }
            temp[3] = t;

            for(int j=0;j<temp.length;j++)
                temp[j] = aesConstants.s_box[temp[j] & 0xff];

            temp[0] ^= aesConstants.rcon[i-1];
            
            for(int j=0;j<temp.length;j++)
                roundKeys[i][j][0] ^= temp[j];
            
            for (int j = 1; j < 4; j++) 
                for (int k = 0; k < 4; k++) 
                    roundKeys[i][k][j] ^= roundKeys[i][k][j-1];
        }

        return roundKeys;
    }
    
    public byte[] encrypt(byte[] plaintext, byte expandKey[][][]) {
        byte[][] state = new byte[4][4];
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++) 
                state[j][i] = plaintext[i * 4 + j];
            
        addRoundKey(state, expandKey[0]);

        for(int i=1;i<=9;i++)
        {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, expandKey[i]);
        }

        subBytes(state);
        shiftRows(state);
        addRoundKey(state, expandKey[10]);

        byte[] ciphertext = new byte[16];

        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++) 
                ciphertext[i * 4 + j] = state[j][i];
        
        return ciphertext;
    }

    public byte[] decrypt(byte[] ciphertext, byte expandKey[][][]){
        byte[][] state = new byte[4][4];

        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++) 
                state[j][i] = ciphertext[i * 4 + j];
            
        addRoundKey(state, expandKey[10]);
        invShiftRows(state);
        invSubBytes(state);

        for(int i=9;i>0;i--)
        {
            addRoundKey(state, expandKey[i]);
            invMixColumns(state);
            invShiftRows(state);
            invSubBytes(state);
        }

        addRoundKey(state, expandKey[0]);


        byte[] plaintext = new byte[16];

        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 4; j++) 
                plaintext[i * 4 + j] = state[j][i];
            
        return plaintext;
    }
    
    public void padding(byte[] plaintext,int amountofBytes)
    {
        byte padValue = (byte)(16 - amountofBytes);
        System.out.println("padValue: " + padValue);
        for(int i=amountofBytes;i<plaintext.length;i++)
            plaintext[i] = padValue;
    }

    public byte[] invPadding(byte[] data) {
        if (data.length == 0) return data;
        
        int padValue = data[data.length - 1] & 0xff;
        for(int i=0;i<16;i++)
            System.out.println(i + "byte value: " + data[i]);
        
        // Check padding validity
        if (padValue < 1 || padValue > 16) {
            throw new IllegalArgumentException("Invalid padding: " + padValue);
        }
        
        // Verify all padding bytes are correct
        for (int i = data.length - padValue; i < data.length; i++) {
            if ((data[i] & 0xff) != padValue) {
                throw new IllegalArgumentException("Invalid padding: " + (data[i] & 0xff) + ", padvalue: " + padValue + " in i: " + i);
            }
        }
        
        byte[] result = new byte[data.length - padValue];
        System.arraycopy(data, 0, result, 0, result.length);
        return result;
    }
    
    public String changeFileName(File f,String newName)
    {
        String originalName = f.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = originalName.substring(0, dotIndex);
        String extension = originalName.substring(dotIndex);

        return baseName + newName + extension;
    }

    public File encryptFile(File f, byte[] key)
    {
        File encrypted = new File(f.getParent(),changeFileName(f, "encrypted"));
        byte expandKey[][][] = expandKey(key);
        byte plaintext[] = new byte[16];
        byte cipher[];
        try{
            FileInputStream filein = new FileInputStream(f);
            FileOutputStream fileout = new FileOutputStream(encrypted);
            boolean addPadding = false;
            int amountofBytes;

            while ((amountofBytes = filein.read(plaintext)) != -1) {
                if (amountofBytes < 16 && filein.available() ==  0) {
                    for (int i = amountofBytes; i < 16; i++) plaintext[i] = 0;
                    padding(plaintext, amountofBytes);
                    addPadding = true;
                }
                cipher = encrypt(plaintext, expandKey);
                fileout.write(cipher);
            }

            if (!addPadding) {
                padding(plaintext, 0);
                cipher = encrypt(plaintext, expandKey);
                fileout.write(cipher);
            }
            
            fileout.close();
            filein.close();
        }
        catch(Exception e){
            System.out.println("Error encrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return encrypted;
    }

    public File decryptFile(File f, byte[] key) {
        File decrypted = new File(f.getParent(),changeFileName(f, "decrypted"));
        byte expandKey[][][] = expandKey(key);
        try {
            FileInputStream filein = new FileInputStream(f);
            FileOutputStream fileout = new FileOutputStream(decrypted);
            
            long fileSize = f.length();
            int numBlocks = (int)(fileSize / 16);
            byte[] cipherBlock = new byte[16];
            byte[] plainBlock;
            
            for (int i = 0; i < numBlocks - 1; i++) {
                filein.read(cipherBlock);
                
                plainBlock = decrypt(cipherBlock, expandKey); 
                fileout.write(plainBlock);
                for (int j = 0; j < 16; j++) cipherBlock[j] = 0;
            }

            filein.read(cipherBlock);
            plainBlock = decrypt(cipherBlock, expandKey);
            byte[] unpaddedBlock = invPadding(plainBlock);
            fileout.write(unpaddedBlock);
            
            fileout.close();
            filein.close();
        }
        catch(Exception e) {
            System.out.println("Error decrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return decrypted;
    }

    public static void main(String[] args) {
        aes a = new aes();
        File f = new File("C:\\Users\\keyna\\Downloads\\image (8).png");

        byte key[] = new byte[16];
        for(int i=0;i<16;i++)
            key[i] = (byte)i;

        byte data[] = new byte[16];
        for(int i=0;i<16;i++)
            data[i] = (byte)(i * 2);
        byte expandKey[][][] = a.expandKey(key);

        System.out.println("\n");
            System.out.print("origanal data: = ");
        for(int i=0;i<16;i++)
            System.out.print(data[i] + ", ");
        System.out.println("\n");

        byte encryptData[] = a.encrypt(data, expandKey);
        System.out.print("encrypted data: = ");
        for(int i=0;i<16;i++)
            System.out.print(encryptData[i] + ", "); 
        System.out.println("\n");

        byte decryptedptData[] = a.decrypt(encryptData, expandKey);
        System.out.print("decrypted data: = ");
        for(int i=0;i<16;i++)
            System.out.print(decryptedptData[i] + ", ");

        System.out.println();
        File encrypted_file = a.encryptFile(f, key);
        System.out.println(encrypted_file.getAbsolutePath() + "\n");
        File decrypted_file = a.decryptFile(encrypted_file, key);
        System.out.println(decrypted_file.getAbsolutePath() + "\n");

    }
}
