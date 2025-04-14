package project.keys.Kseq;
import project.keys.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ksequence implements Keys{
    
    private int k;
    private int n;
    private int count;
    private Random rand;
    private ArrayList<int[]> solutions;

    public Ksequence(int k, int n,int seed)
    {
        this.k = k;
        this.n = n;
        this.rand = new Random(seed);
        solutions = new ArrayList<>();
        this.count = 0;
    }

    private int KDfs(int start,int[] seq,int count)
    {
        if(count == this.k)
        {
            solutions.add(Arrays.copyOf(seq, this.k));
            return 1;
        }

        int sum = 0;

        for(int i=start;i<=this.n;i++)
        {
            seq[count] = i;

            sum += KDfs(i,seq,count+1);
        }

        return sum;
    }

    public void findKseqnces()
    {
        int [] seq = new int[this.k];
        this.count =  KDfs(1, seq, 0);
        System.out.println("amount: " + this.count);
    }

    public void findKiterative() {
        int[] seq = new int[this.k];
        
        for (int i = 0; i < this.k; i++) {
            seq[i] = 1;
        }
    
        while (true) {
            System.out.print(Arrays.toString(seq));
            System.out.println();
            
            int i;
            for (i = this.k - 1; i >= 0; i--) {
                if (seq[i] < this.n) {
                    break;
                }
            }
            
            if (i < 0) {
                break;
            }
            
            seq[i]++;
            for (int j = i + 1; j < this.k; j++) {
                seq[j] = seq[i];
            }
        }
    }

    public int findSolutionsAmount()
    {// משולש פסקל
        int amount = 0;
        int Sigama = 0;

        for(int i=1;i<=this.n;i++)
        {
            Sigama += i;
            amount += Sigama;
        }

        return amount;
    }

    public int getSolutionAmount()
    {
        return this.count;
    }

    public ArrayList<int[]> getSolutions()
    {
        return this.solutions;
    }

    public byte[] getKey16()
    {
        byte key[] =  new byte[16];

        if (count == 0)findSolutionsAmount();
        
        for(int i=0;i<16;i++)
        {
            int randSolutionIndex = rand.nextInt(this.count);
            int keySolution = 0;
            for(int j=0;j<this.k;j++)
                keySolution += solutions.get(randSolutionIndex)[j];

            key[i] = (byte)(keySolution & 0xFF);
        }

        return key;
    }

    public void printSolutions()
    {
        for(int[] seq : this.solutions)
        {
            for(int num : seq)
                System.out.print(num +" ");

            System.out.println();
        }
    }

}

class main5
{
    public static void main(String[] args) {
        Ksequence s = new Ksequence(6, 50,1234);//k=6 , n=50 java max heap space;
        s.findKseqnces();
        s.printSolutions();
        s.findKiterative();
    }
}


