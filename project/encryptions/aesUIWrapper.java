package project.encryptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.*;

import project.keys.Keys;

public class aesUIWrapper extends wrapper {
    private aes a;

    public aesUIWrapper(encryption e,JProgressBar bar,JButton back,Keys key,File FilePtr,boolean encrypt){
        super(e, bar, back, key, FilePtr, encrypt);
        a = (aes)e;
    }

    @Override
    public void run()
    {
        if (this.encrypt) 
            this.encryptFile();
		else
            this.decryptFile();
    }

    @Override
    public File encryptFile()
    {
        File encrypted = new File(this.FilePtr.getParent(),a.changeFileName(this.FilePtr, "encrypted"));
        long totalBytes = this.FilePtr.length();
        long bytesRead = 0;
        byte expandKey[][][] = a.expandKey(this.key_16);
        byte plaintext[] = new byte[16];
        byte cipher[];
        
        try{
            FileInputStream filein = new FileInputStream(this.FilePtr);
            FileOutputStream fileout = new FileOutputStream(encrypted);
            boolean addPadding = false;
            int amountofBytes;

            while ((amountofBytes = filein.read(plaintext)) != -1) {
                if (amountofBytes < 16 && filein.available() ==  0) {
                    for (int i = amountofBytes; i < 16; i++) plaintext[i] = 0;
                    a.padding(plaintext, amountofBytes);
                    addPadding = true;
                }
                cipher = a.encrypt(plaintext, expandKey);
                fileout.write(cipher);

                bytesRead += amountofBytes;
                int percent = (int)((bytesRead * 100) / totalBytes);
                SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
            }

            if (!addPadding) {
                a.padding(plaintext, 0);
                cipher = a.encrypt(plaintext, expandKey);
                fileout.write(cipher);
            }
            
            fileout.close();
            filein.close();

            System.out.println("finished encryption");
			bar.setString("Done Encryption");
			back.setEnabled(true);
        }
        catch(Exception e){
            System.out.println("Error encrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return encrypted;
    }

    public File decryptFile() {
        File decrypted = new File(this.FilePtr.getParent(),a.changeFileName(this.FilePtr, "decrypted"));
        byte expandKey[][][] = a.expandKey(this.key_16);
        try {
            FileInputStream filein = new FileInputStream(this.FilePtr);
            FileOutputStream fileout = new FileOutputStream(decrypted);
            
            long fileSize = this.FilePtr.length();
            int numBlocks = (int)(fileSize / 16);
            byte[] cipherBlock = new byte[16];
            byte[] plainBlock;
            
            for (int i = 0; i < numBlocks - 1; i++) {
                filein.read(cipherBlock);
                
                plainBlock = a.decrypt(cipherBlock, expandKey); 
                fileout.write(plainBlock);
                for (int j = 0; j < 16; j++) cipherBlock[j] = 0;

                int percent = (int)((i * 100) / numBlocks);
                SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
            }

            filein.read(cipherBlock);
            plainBlock = a.decrypt(cipherBlock, expandKey);
            byte[] unpaddedBlock = a.invPadding(plainBlock);
            fileout.write(unpaddedBlock);
            
            fileout.close();
            filein.close();

            SwingUtilities.invokeLater(() -> this.bar.setValue(100));
            System.out.println("finished Decryption");
			bar.setString("Done Decryption");
			back.setEnabled(true);
        }
        catch(Exception e) {
            System.out.println("Error decrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return decrypted;
    }
}
