# Algorithm

## sliding window

尋找大串字串裡的特定小字串，

使用滑動的 window 檢視

不對就繼續滑動

```go
## 567. https://leetcode.com/problems/permutation-in-string/solution/

func checkInclusion(s1 string, s2 string) bool {
	if len(s1) > len(s2) {return false}
	s1map := [26]rune{}
	s2map := [26]rune{}

	for i := 0; i < len(s1); i++ {
		c1 := rune(s1[i])
		c2 := rune(s2[i])
		s1map[c1-'a']++
		s2map[c2-'a']++
	}

	for left, right := -1, len(s1) - 1; right < len(s2); left, right = left+1, right+1 {
		if s1map == s2map {return true}
		if left == -1 {continue}

		remove := rune(s2[left])
		add := rune(s2[right])
		s2map[remove - 'a']--
		s2map[add - 'a']++
	}

	return s1map == s2map
    
}
```

##  trie

