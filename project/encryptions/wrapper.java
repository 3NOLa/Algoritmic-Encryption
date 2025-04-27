package project.encryptions;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import project.keys.Keys;

interface encryptionwrapper extends Runnable{
    public File encryptFile();
    public File decryptFile();
}

public abstract class wrapper implements encryptionwrapper{
    protected encryption e;
    protected JProgressBar bar;
    protected JButton back;
	protected Keys key;
    protected byte[] key_16;
    protected File FilePtr;
    protected boolean encrypt;

    public wrapper(encryption e,JProgressBar bar,JButton back,Keys key,File FilePtr,boolean encrypt){
        this.e = e;
        this.bar = bar;
        this.back = back;
        this.key = key;
        this.key_16 = (this.key != null)?this.key.getKey16():null;
        this.FilePtr = FilePtr;
        this.encrypt = encrypt;
    }

    public void setParmeters(JProgressBar bar,JButton back,Keys key,File FilePtr,boolean encrypt){
        this.bar = bar;
        this.back = back;
        this.key = key;
        this.key_16 = this.key.getKey16();
        this.FilePtr = FilePtr;
        this.encrypt = encrypt;
    }
}
