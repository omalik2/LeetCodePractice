/*
Problem Statement from LeetCode (https://leetcode.com/problems/jump-game-ii/description/):

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

*/

/*
Rationale

Let M(i) be the mininum jumps required to reach index i
Let N(i) be the maximum index reachable from the jump that led us to index i
Let maxIdx(i) be the maximum index reachable from indexes 0 to i (inclusive)

Assume that we have the minimum jumps to i - 1 i.e. M(i-1) is known. There are two possibilities for index i:

The jump that took us to i-1 will also take us to i (i.e. N(i-1) >= i ). This implies that M(i) = M(i-1) (i.e. no extra jump needed to reach i ) and N(i) = N(i-1) (i.e. the jump that took us to i-1 can also take us to i).
-> This means that we require storing the jumpable index, N(i-1), that took us to i-1

The jump that took us to i-1 will not take us to i. This means that we will need to make another jump somewhere betweeen i-1 and 0 (inclusive). Where should we take this jump?
a) We could take jump at i-1. But will this maximise the index reachable? No because
i-2 could allow us to reach an index greater than the one reachable by i-1.

b) we should take the jump from the index that gives us the maximum range. Thus we need the maximum index	we can jump to seen up till index i. This is maxIdx(i). This need not be an array as we only refer to the currently maximum index available to jump to.

In this case, the count of jumps up to i, M(i), is equal to jumps required to get to the index that allows us
to jump the furthest forward + 1 i.e. M(i) = M( maxIdx(i) ) + 1
Equally, the maximum index reachable from the jump that led us to i would be N(i) = nums( maxIdx(i) ) + maxIdx(i).

As the second case requires us to know the maximum jumpable index we can reach from index i, we need to update the maxIdx at each i if we see a jump from i that extends the reachable index range.
if( nums(i) + i > nums(maxIdx) + maxIdx )
maxIdx = i;

Time: O(n)
Space: O(2n) = O(n)
*/

public class Solution{

public int jump(int[] nums) {
	// Array to hold the min jumps required to reach each element i
	int[] M = new int[nums.length];
	// Base case: No jumps required to reach index 0
	M[0] = 0;
	
	// Index of the maximum jump seen so far
	int maxIdx = 0;
	
	// Array to hold the index reachable from the jump that took us to i
	int[] N = new int[nums.length];
	// Jump that took us to index 0 can't take us anywhere else so 0
	N[0] = 0;

	for(int i = 1; i < nums.length; i++){
		// Can we reach i from the same jump that we could reach i-1 from?
		if (N[i-1] >= i) {
			M[i] = M[i-1];
			N[i] = N[i-1];
		}
		else {
			// No we cannot, so we have to take a jump somewhere,
			// Best place to take jump is from place that maximises the reachable index
			M[i] = M[maxIdx] + 1;
			// The max reachable index from where we jumped to i from is maxIdx
			N[i] = nums[maxIdx] + maxIdx;
		} 
		
		// Update the maximum position jumpable from i
		if(nums[i] + i > nums[maxIdx] + maxIdx){
			maxIdx = i;
		}
	}

	return M[nums.length-1];

}

}
