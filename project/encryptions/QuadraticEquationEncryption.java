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

public class QuadraticEquationEncryption extends encryption{
    public File filePtr;
    public int fileSize;

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
        try(
            FileInputStream filein = new FileInputStream(filePtr);
            ObjectOutputStream fileout = new ObjectOutputStream(new FileOutputStream(encrypted));)
        {
            byte FileBytes [] = new byte[1024];
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

                    subTwoBytes(twoBytes);
                    structEncrypt s = new structEncrypt((int)twoBytes[0], (int)twoBytes[1]);
                    fileObjects[j/2] = s;
                }
                fileout.writeObject(fileObjects);
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
            structEncrypt[] current = (structEncrypt[])filein.readObject();
            try{
                while(true)
                {
                    structEncrypt[] next = (structEncrypt[]) filein.readObject();
                    byte objectBytes [] = new byte[current.length * 2];
                    for(int i =0;i<current.length;i++)
                    {
                        byte[] twoBytes = current[i].QuadraticEquation();
                        invSubTwoBytes(twoBytes);
                        objectBytes[i * 2] = twoBytes[0];
                        objectBytes[(i * 2) + 1] = twoBytes[1];
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
                    invSubTwoBytes(twoBytes);
                    objectBytes[i * 2] = twoBytes[0];
                    if(i*2 + 1 < bytesLength)
                        objectBytes[(i * 2) + 1] = twoBytes[1];
                }
                fileout.write(objectBytes);
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


