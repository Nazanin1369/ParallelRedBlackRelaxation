package main;
import static main.Application.n;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable
{

	private double A[][];
	private int start_row;
	private int end_row;
	private final CyclicBarrier barrier;
	 public Worker(double A[][], CyclicBarrier barrier, int start_row, int end_row)
	 {
		 this.A = A;
		 this.barrier = barrier;
		 this.start_row = start_row;
		 this.end_row = end_row;
	 }
	
	@Override
	public void run() 
	{
		int start = 1;
		double temp;
		int time = 0;
		int i,j,k;
		
		System.out.println("Thread: "+ Thread.currentThread().getName());
		
		for(k = 0; k <80000; k++)
		{
			int []flip = {2, 1};
			time = 0;
			while(time < 2)
			{
		      for(i = start_row; i <= end_row; i++)
		     {
		    	  if(i % 2 == 0)
					{
		    		  
						start = flip[0] ;
					}
					else
					{
						start = flip[1];
					}
		    	  
				for(j = start; j <= n - 2; j++)
				{
					temp = (A[i - 1][j] + A[i + 1][j] + A[i][j - 1] + A[i][j + 1]) / 4;
					A[i][j] = temp;
				}
				 if(start == 1)
					{
						start = 2;
					}	
					 else
					{
						start = 1;
					}
				
				
				
		     }//end of i for
		     
		    //Barrier
				try
				{
					barrier.await();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				catch(BrokenBarrierException b)
				{
					b.printStackTrace();
				}
				flip[0] = 1;
				flip[1] = 2;
		
			
			time++;
		}//end of while
			  
	}//end of k for
	 
}
	
}

