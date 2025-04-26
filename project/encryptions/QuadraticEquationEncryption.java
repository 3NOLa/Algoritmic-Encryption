package project.encryptions;

import java.io.*;

class structEncrypt implements Serializable{
    public int addition;
    public int mult;
    public boolean switchFlag;

    public structEncrypt(int firstByte, int seconedByte)
    {
        this.addition = firstByte + seconedByte;
        this.mult = firstByte * seconedByte;
        this.switchFlag = (firstByte<seconedByte)?true:false;
    }

    public byte[] QuadraticEquation()
    {
        byte[] origanalNumbers = new byte[2];
        int a = 1;

        byte firstNum = (byte)((addition + Math.sqrt((addition*addition) - 4 * a * mult)) / 2*a);
        byte seconedByte = (byte)((addition - Math.sqrt((addition*addition) - 4 * a * mult)) / 2*a);
        if (switchFlag) {
            origanalNumbers[1] = firstNum;
            origanalNumbers[0] = seconedByte;
        }
        else{
            origanalNumbers[0] = firstNum;
            origanalNumbers[1] = seconedByte;
        }

        return origanalNumbers;
    }
}

public class QuadraticEquationEncryption {
    private File filePtr;
    private int fileSize;

    public QuadraticEquationEncryption(File f)
    {
        this.filePtr = f;
        this.fileSize = (int)f.length();
    }

    public String changeFileName(File f,String newName)
    {
        String originalName = f.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = originalName.substring(0, dotIndex);
        String extension = originalName.substring(dotIndex);

        return baseName + newName + extension;
    }

    public void subTwoBytes(byte[] twoBytes) {
        twoBytes[0] = aesConstants.s_box[twoBytes[0] & 0xff];
        twoBytes[1] = aesConstants.s_box[twoBytes[1] & 0xff];
    }

    public void invSubTwoBytes(byte[] twoBytes) {
        twoBytes[0] = aesConstants.s_box_inverse[twoBytes[0] & 0xff];
        twoBytes[1] = aesConstants.s_box_inverse[twoBytes[1] & 0xff];
    }

    public File encryptFile()
    {
        File encrypted = new File(filePtr.getParent(),changeFileName(filePtr, "encrypted"));
        try{
            FileInputStream filein = new FileInputStream(filePtr);
            ObjectOutputStream fileout = new ObjectOutputStream(new FileOutputStream(encrypted));
            byte twoBytes [] = new byte[2];
            
            for(int i=0;i<fileSize /2;i++)
            {
                filein.read(twoBytes);
                subTwoBytes(twoBytes);
                structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                fileout.writeObject(s);
            }

            if (fileSize % 2 == 1) {
                byte oneByte = (byte)filein.read();
                twoBytes[0] = twoBytes[1] = oneByte;
                subTwoBytes(twoBytes);
                structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                fileout.writeObject(s);
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

    public File decryptFile()
    {
        File decrypted = new File(filePtr.getParent(),changeFileName(filePtr, "decrypted"));
        try{
            ObjectInputStream filein = new ObjectInputStream(new FileInputStream(filePtr));
            FileOutputStream fileout = new FileOutputStream(decrypted);
            structEncrypt current = (structEncrypt)filein.readObject();
            byte twoBytes [] = new byte[2];
            try{
                while(true)
                {
                    structEncrypt next = (structEncrypt) filein.readObject();
                    twoBytes = current.QuadraticEquation();
                    invSubTwoBytes(twoBytes);
                    fileout.write(twoBytes);
                    current = next;
                }
            }
            catch(EOFException eof)
            {
                twoBytes = current.QuadraticEquation();
                invSubTwoBytes(twoBytes);
                fileout.write(twoBytes[0]);
            }
            
            fileout.close();
            filein.close();
        }
        catch(Exception e){
            System.out.println("Error decrypting file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return decrypted;
    }

    public static void main(String[] args) {
        structEncrypt s = new structEncrypt(6, 8);
        System.out.println(s.addition + ", " + s.mult);
        byte [] t = s.QuadraticEquation();
        for(byte x : t)
            System.out.println(x);
        QuadraticEquationEncryption q = new QuadraticEquationEncryption(new File("C:\\Users\\keyna\\PycharmProjects\\pythonProject3\\alon2.py"));
        File encrypted_file = q.encryptFile();
        System.out.println(encrypted_file.getAbsolutePath() + "\n");
        
        QuadraticEquationEncryption qd = new QuadraticEquationEncryption(new File("C:\\\\Users\\\\keyna\\\\PycharmProjects\\\\pythonProject3\\\\alon2encrypted.py"));
        File decrypted_file = qd.decryptFile();
        System.out.println(decrypted_file.getAbsolutePath() + "\n");
    }
}


