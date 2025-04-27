package project.encryptions;

import java.io.File;

import project.keys.Kseq.*;

public class sequenceEncryption extends encryption{
    private Ksequence key;
    private File FilePtr;
    private int fileSize;
    private int k;
    private int n;
    private int seed;
    
    public sequenceEncryption(File f)
    {
        this.FilePtr = f;
        key = new Ksequence(0, 0, 0);
    }

}
