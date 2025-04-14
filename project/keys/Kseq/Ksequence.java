package project.keys.Kseq;
import project.keys.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Ksequence implements Keys{
    
    private int k;
    private int n;
    private int count;
    private ArrayList<int[]> solutions;

    public Ksequence(int k, int n)
    {
        this.k = k;
        this.n = n;
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
        return new byte[16];
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
        Ksequence s = new Ksequence(3, 5);
        s.findKseqnces();
        s.printSolutions();
        s.findKiterative();
    }
}


