Mong H. Ng
Nice to meet you!

1. Runtime

For offline, let the size of the dictionary be m. We read each line holding that word one by one and add each word to a corresponding arraylist. We ignore the calculation on getting the sorted version of a word since 100log(100) is a constant, where 100 is the max length of a word. For each word in the dictionary we perform a fixed number of operation. Thus the runtime will be linear to the m: O(m).

For online, since we use a hashmap for fast lookup (assuming we have fair distribution and low collision), the runtime will be constant: O(1)

2. Space complexity

In one form or the other, we store each word in the dictionary once in memory. This means we have space complexity of O(m) where m is again size of the dictionary.

The assumption that we have several GB of memory is important. Note the dictionary contains at most a million words, and each word is at most 100 characters long. Assuming each character is one byte. We would use around 10e2 x 10e6 = 10e8 and any extra object headers and other overhead memory usage. This is less than the common 4Gb memory (4Gb = 4x10e9).

3. Extra credit

First step: always check if we have enough memory. If we do, just do the above approach.

If we don't have enough memory, we can first do the same preprocessing. Then store the results to a file. Then turn the hashmap to point the sorted word to the index of the line of the file. In other word:

"apple" -> "appel apple pepla"

would become

"apple" -> i, where i is the line number of the result file

This will slow our performance down by a lot since we must now access that file. Thus we could also do some caching. We could have a LRU cache storing the most recently used anagrams.
