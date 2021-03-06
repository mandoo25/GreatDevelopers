import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
	/**
	 * @param arr
	 * @return
	 * 
	 * 	fastest algorithm among the maxSubarray 1~3
	 *  하기 공식을 이용한 알고리즘~!!
	 *  SumMax(n) = Max(Sum(n), Sum(n) - Min(Sum(0) ~ Sum(n-1))
	 * 
	 */
	
	
	static long[] maxSubarray3(long[] arr) {
    	boolean debugPrint = false;
    	
    	long[] sum = new long[arr.length];
    	
    	long subArrMax = arr[0];
    	long sumMin = arr[0];
    	long subSeqMax = Long.MIN_VALUE;
    	
    	for(int i = 0 ; i < arr.length ; i++)
    	{
    		if(i == 0)
    		{
    			sum[i] = arr[0];
    		}
    		else
    		{
    			sum[i] += sum[i-1] + arr[i];
    			if(sum[i-1] < sumMin) sumMin = sum[i-1];
    			
    			long subMax = sum[i] - sumMin;
    			subMax = sum[i] < subMax ? subMax : sum[i];
    			
    			if(subArrMax < subMax) subArrMax = subMax;
    			
    			System.out.println(subArrMax+","+sumMin+" ");
    		}
    		
    		if(arr[i] > 0)
			{
				if(subSeqMax > 0)
				{
					subSeqMax += arr[i];
				}
				else
				{
					subSeqMax = arr[i];
				}
			}
			else
			{
				if(subSeqMax < 0 )
				{
					if(subSeqMax < arr[i])
					{
    					subSeqMax = arr[i];
					}
				}    				
			}    		
    		
			/*
    		if(debugPrint)
    		{
    			System.out.print(subSeqMax+" ");
    		}
    		*/
    		
    	}
    	
    	long[] result = new long[2];
    	result[0] = subArrMax;	// 2, -1, 2, 3, 4
    	result[1] = subSeqMax;	// 2,  2, 3, 4
    	return result;
    }
	
	/**
	 * @param arr
	 * @return
	 * 
	 * 	가장 느린 알고리즘.. 전체의 경우의 수를 다 구함 (정답은 다 맞음)
	 *  
	 *  
	 * 
	 */
	
	static long[] maxSubarray2(long[] arr) {
    	boolean debugPrint = true;
    	
    	long subArrMax = arr[0];
    	long subSeqMax = Long.MIN_VALUE;
    	
    	for(int i = 0 ; i < arr.length ; i++)
    	{
    		long sum = 0;
    		for(int j = i ; j < arr.length ; j++)
    		{
    			if(j == i)
    			{
    				sum = arr[j];
    			}
    			else
    			{
    				sum += arr[j];
    			}

    			if(sum > subArrMax)     subArrMax = sum;				
    		}
    		

			if(arr[i] > 0)
			{
				if(subSeqMax > 0)
				{
					subSeqMax += arr[i];
				}
				else
				{
					subSeqMax = arr[i];
				}
			}
			else
			{
				if(subSeqMax < 0 )
				{
					if(subSeqMax < arr[i])
					{
    					subSeqMax = arr[i];
					}
				}    				
			}    		
    		
			/*
    		if(debugPrint)
    		{
    			System.out.print(subSeqMax+" ");
    		}
    		*/
    		
    	}
    	
    	long[] result = new long[2];
    	result[0] = subArrMax;	// 2, -1, 2, 3, 4
    	result[1] = subSeqMax;	// 2,  2, 3, 4
    	return result;
    }
	
	/**
	 * @param arr
	 * @return
	 * 
	 *  위의 경우와 마찬가지로 sum의 모든 경우의 수를 구하는데, 2차원 배열을 이용하여 구함.. (일반적인 동적 프로그래밍 구조여서 시작했다가 완전 망함...)
	 *  자바에서는 배열의 안정적인 크기로 추천하는 것이, Integer.MAX_VALUE - 8 이라고 함.... 배열의 크기가 너무 커지는 바람에... 이클립스에서도 실행 불가 ( Heap 사이즈 조정해도 문제)
	 *  해커 랭크 사이트에서는 같은 코드인데.. Runtime error가 나거나, Wrong error라고 나옴.. 이래서 무슨 문제인지 알기 어려웠음..
	 * 
	 * 
	 * 	//
	 	// Some VMs reserve some header words in an array.
 		// Attempts to allocate larger arrays may result in
 		// OutOfMemoryError: Requested array size exceeds VM limit
 		//
		private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	 * Martin Buchholz (Google) 에서 2010-05-09에 추가했습니다. 크리스 Hegarty (오라클)에 의해 검토.ㄷ
	    따라서 최대 "안전한"숫자는 2 147 483 639 ( Integer.MAX_VALUE - 8 )이고 "더 큰 배열을 할당하려고하면 OutOfMemoryError 가 발생할 수 있습니다"라고 말할 수 있습니다.
	 * 
	 */
	// Complete the maxSubarray function below.
    static long[] maxSubarray(long[] arr) {
    	boolean debugPrint = true;
    	
    	long[][] sum = new long[arr.length][arr.length];
    	
    	long subArrMax = arr[0];
    	long subSeqMax = Long.MIN_VALUE;
    	
    	for(int i = 0 ; i < sum.length ; i++)
    	{
    		for(int j = i ; j < sum.length ; j++)
    		{
    			
				if( i == 0 )
				{
					if( j == 0) sum[i][j] = arr[j];
					else	    sum[i][j] = arr[j] + sum[i][j-1];
				}
				else
				{
					sum[i][j] = sum[i-1][j] - arr[i-1];
				}
    			
    			if(sum[i][j] > subArrMax) subArrMax = sum[i][j];
    		}
    		

			if(arr[i] > 0)
			{
				if(subSeqMax > 0)
				{
					subSeqMax += arr[i];
				}
				else
				{
					subSeqMax = arr[i];
				}
			}
			else
			{
				if(subSeqMax < 0 )
				{
					if(subSeqMax < arr[i])
					{
    					subSeqMax = arr[i];
					}
				}    				
			}    		
    		
    		if(debugPrint)
    		{
    			System.out.print(subSeqMax+" ");
    		}
    		
    	}
    	
    	if(debugPrint)
    	{
    		System.out.println();
    		System.out.println("----------");
    		for(int i = 0 ; i < sum.length ; i++)
        	{
        		for(int j = 0 ; j < sum.length ; j++)
        		{
        			System.out.print(sum[i][j]+" ");
        		}
        		System.out.println();
        	}
    		System.out.println("----------");
    	}
    	
    	
    	long[] result = new long[2];
    	result[0] = subArrMax;	// 2, -1, 2, 3, 4
    	result[1] = subSeqMax;	// 2,  2, 3, 4
    	return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            long[] arr = new long[n];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                long arrItem = Long.parseLong(arrItems[i]);
                arr[i] = arrItem;
            }

            long[] result = maxSubarray3(arr);
            
            for (int i = 0; i < result.length; i++) {
                // bufferedWriter.write(String.valueOf(result[i]));
            	System.out.print(result[i]);

                if (i != result.length - 1) {
                    // bufferedWriter.write(" ");
                	System.out.print(" ");
                }
            }
            
            System.out.println();
            // bufferedWriter.newLine();
        }

        // bufferedWriter.close();

        scanner.close();
    }
}
