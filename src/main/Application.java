package main;

import java.util.concurrent.CyclicBarrier;

public class Application 
{

	public static int n = 34;
	public static double A[][] = new double[n][n];
	public static final int numworkers = Runtime.getRuntime().availableProcessors();
	public static void main(String[] args)
	{
		/**
		 * Initialization Part
		 */
		for( int i = 0; i < n; i++)
		{
			for(int j =0; j < n; j++)
			{
				A[i][j] = 0;
			}
		}
		
		for(int i =0; i < n; i++)
		{
			A[i][0] = 10; 
			A[0][i] = 10; 
			A[i][n-1] = 10; 
			A[n-1][i] = 10;
		}
		/*Sequential */
		
		
		/* Parallel*/
		CyclicBarrier barrier = new CyclicBarrier(1);
		int start,end;
		
		Thread[] threads = new Thread[numworkers];
		int portion =  (A.length - 2) / numworkers;
	
		for(int i = 0; i < numworkers; i++)
		{
			start = i * portion + 1 ;
			end =  start + portion - 1;
			System.out.println("start : " + start + " End: " + end);
			threads[i] = new Thread( new Worker(A,barrier, start, end));
			
		}
		long par_start = System.nanoTime();
		for(int i = 0; i < numworkers; i++)
		{
			threads[i].start();
		}
		for(int i = 0; i < numworkers; i++)
		{
			try 
			{
				threads[i].join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
		long par_end = System.nanoTime();
		long par_time = (par_end - par_start);
		System.out.print("Parallel Elapsed Time: ");
		System.out.println((par_time / Math.pow(10,9)));
		
		for( int i = 0; i < n; i++)
		{
			System.out.println();
			for(int j =0; j < n; j++)
			{
				System.out.print(A[i][j]+ " "); 
			}
		}
		
		
	}
	
	void sequentialRedBlack()
	{
		
	}
}
