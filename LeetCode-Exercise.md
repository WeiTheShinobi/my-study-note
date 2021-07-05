# LeetCode Exercise

## 141

[Linked List Cycle - LeetCode](https://leetcode.com/problems/linked-list-cycle/)

輸入一個Linked List，Return `true` *if there is a cycle in the linked list*. Otherwise, return `false`.

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class Solution {
    public boolean hasCycle(ListNode head) {
        // 判斷無點或只有一點，這種情況不會也cycle
        if (head == null) return false;
        if (head.next == null) return false;
        
        // 透過一快一慢的指標，如果有linked list有cycle，兩指標最終會走到同一個節點
        // 如果沒有，最終會null並return false
        ListNode slowPointer = head;
        ListNode fastPointer = head;
        
        while (slowPointer != null && fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
            if (slowPointer == fastPointer) return true;
        }

        return false;
    }
}
```

## 13

[Roman to Integer - LeetCode](https://leetcode.com/problems/roman-to-integer/)

輸入羅馬數字的字串，輸出成阿拉伯數字

```java
class Solution {
    public int romanToInt(String s) {
        
        // 映射出羅馬數字與阿拉伯數字
        Map<Character, Integer> romanNumber = new HashMap<>();
        romanNumber.put('I', 1);
        romanNumber.put('V', 5);
        romanNumber.put('X', 10);
        romanNumber.put('L', 50);
        romanNumber.put('C', 100);
        romanNumber.put('D', 500);
        romanNumber.put('M', 1000);
        
        int total = 0;
        
        // 羅馬數字的4、40...等需要特別處理，
        // 發生這種情況時，下一個數字 i+1 一定大於 i，
        // 所以情況發生就減，其他則加。
        for (int i = 0; i < s.length() - 1; i++) {
            int a = romanNumber.get(s.charAt(i));
            int b = romanNumber.get(s.charAt(i+1));
            if (a < b) {
                total -= a;
            } else {
                total += a;
            }
        }
        total += romanNumber.get(s.charAt(s.length() - 1));
        
        return total;
    }
}
```

想要更快的話，

可以不用創建 Map 物件，

改成如下：

```java
     int getValue(char c) {
        if(c=='I') return 1;
        else if(c=='V') return 5;
        else if(c=='X') return 10;
        else if(c=='L') return 50;
        else if(c=='C') return 100;
        else if(c=='D') return 500;
        else return 1000;
    }
```

