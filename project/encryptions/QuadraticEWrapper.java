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
            byte FileBytes [] = new byte[1024];
            int totalBytes = 0;
            int bytesRead;

            while((bytesRead = filein.read(FileBytes)) != -1)
            {
                if (bytesRead % 2 == 1) FileBytes[bytesRead + 1] = FileBytes[bytesRead];

                structEncrypt fileObjects[] = new structEncrypt[bytesRead/2];  
                for(int j=0;j<bytesRead;j+=2)
                {
                    byte twoBytes [] = new byte[2];
                    twoBytes[0] = FileBytes[j];
                    twoBytes[1] = FileBytes[j+1];

                    q.subTwoBytes(twoBytes);
                    structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                    fileObjects[j/2] = s;

                    totalBytes += 2;
                    int percent = (int)((totalBytes * 100) / q.fileSize);
                    SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
                }
                fileout.writeObject(fileObjects);
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
            structEncrypt[] current = (structEncrypt[])filein.readObject();
            try{
                while(true)
                {
                    structEncrypt[] next = (structEncrypt[]) filein.readObject();
                    byte objectBytes [] = new byte[current.length * 2];
                    for(int i =0;i<current.length;i++)
                    {
                        byte[] twoBytes = current[i].QuadraticEquation();
                        q.invSubTwoBytes(twoBytes);
                        objectBytes[i * 2] = twoBytes[0];
                        objectBytes[(i * 2) + 1] = twoBytes[1];
                        bytesRead += 2;
                        int percent = (int)((bytesRead * 100) / totalBytes);
                        SwingUtilities.invokeLater(() -> this.bar.setValue(percent));
                    }
                    fileout.write(objectBytes);
                    current = next;

                }
            }
            catch(EOFException eof)
            {
                int bytesLength = current.length * 2;
                if(current.length % 2 == 1)
                    bytesLength--;

                byte objectBytes [] = new byte[bytesLength * 2];
                for(int i =0;i<current.length;i++)
                {
                    byte[] twoBytes = current[i].QuadraticEquation();
                    q.invSubTwoBytes(twoBytes);
                    objectBytes[i * 2] = twoBytes[0];
                    if(i*2 + 1 < bytesLength)
                        objectBytes[(i * 2) + 1] = twoBytes[1];
                }
                fileout.write(objectBytes);
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
