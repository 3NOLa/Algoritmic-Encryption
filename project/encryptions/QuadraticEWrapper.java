package project.encryptions;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import project.keys.Keys;

public class QuadraticEWrapper extends wrapper{
    private QuadraticEquationEncryption q;

    public QuadraticEWrapper(encryption e,JProgressBar bar,JButton back,Keys key,File FilePtr,boolean encrypt){
        super(e, bar, back, key, FilePtr, encrypt);
        this.q = (QuadraticEquationEncryption)e;
    }

    @Override
    public void run()
    {
        if (encrypt) 
            this.encryptFile();
		else
            this.decryptFile();
    }

    public File encryptFile()
    {
        File encrypted = new File(q.filePtr.getParent(),q.changeFileName(q.filePtr, "encrypted"));
        try{
            FileInputStream filein = new FileInputStream(FilePtr);
            ObjectOutputStream fileout = new ObjectOutputStream(new FileOutputStream(encrypted));
            byte twoBytes [] = new byte[2];
            
            for(int i=0;i<q.fileSize /2;i++)
            {
                filein.read(twoBytes);
                q.subTwoBytes(twoBytes);
                structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                fileout.writeObject(s);
                int percent = (int)((i * 100) / q.fileSize);
                SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
            }

            if (q.fileSize % 2 == 1) {
                byte oneByte = (byte)filein.read();
                twoBytes[0] = twoBytes[1] = oneByte;
                q.subTwoBytes(twoBytes);
                structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                fileout.writeObject(s);
            }

            fileout.close();
            filein.close();
            
            SwingUtilities.invokeLater(() -> this.bar.setValue(100));
            System.out.println("finished Decryption");
			bar.setString("Done Decryption");
			back.setEnabled(true);
        }
        catch(Exception e){
            System.out.println("Error encrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return encrypted;
    }

    public File decryptFile()
    {
        long totalBytes = this.FilePtr.length();
        long bytesRead = 0;
        File decrypted = new File(q.filePtr.getParent(),q.changeFileName(q.filePtr, "decrypted"));
        try{
            ObjectInputStream filein = new ObjectInputStream(new FileInputStream(q.filePtr));
            FileOutputStream fileout = new FileOutputStream(decrypted);
            structEncrypt current = (structEncrypt)filein.readObject();
            byte twoBytes [] = new byte[2];
            try{
                while(true)
                {
                    structEncrypt next = (structEncrypt) filein.readObject();
                    twoBytes = current.QuadraticEquation();
                    q.invSubTwoBytes(twoBytes);
                    fileout.write(twoBytes);
                    current = next;
                    bytesRead += 2;
                    int percent = (int)((bytesRead * 100) / totalBytes);
                    SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
                }
            }
            catch(EOFException eof)
            {
                twoBytes = current.QuadraticEquation();
                q.invSubTwoBytes(twoBytes);
                fileout.write(twoBytes[0]);
            }
            
            fileout.close();
            filein.close();

            SwingUtilities.invokeLater(() -> this.bar.setValue(100));
            System.out.println("finished encryption");
			bar.setString("Done Encryption");
			back.setEnabled(true);
        }
        catch(Exception e){
            System.out.println("Error decrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return decrypted;
    }

}
