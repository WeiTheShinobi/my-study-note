# 離散數學

> [10920趙啟超教授離散數學_第1A講 Logic and Set Theory - YouTube](https://www.youtube.com/watch?v=A_NNGOqui7s&list=PLS0SUwlYe8cyaHfrgAVaaKVNkwLYNsTA2&index=1&ab_channel=NTHUOCW)

## Logic and Set Theory

### Logic

p 表示狀態

- Implication

若 p 則 q

p 表前提

q 表結論
$$
\begin{array}{c|lcr}
& \text{p} & \text{q} & \text{p } \implies { q} & p \iff q\\
\hline
& 0 & 0 & 1 & 1\\
& 0 & 1 & 1 & 0\\
& 1 & 1 & 1 & 1\\
& 1 & 0 & 0 & 0
\end{array}
$$
當前提是錯的，結論必是對的


$$
p \iff q
$$
若且唯若 (if only if)

可以互相推導

結論不成立，前提就不成立

## Set Theory

an unordered collection of objects

集合表示
$$
A =
\begin{Bmatrix}
	n∈N:condition
\end{Bmatrix} \\

\overline{A}  = 
\begin{Bmatrix}
	x:x\notin A \quad  (不在A中)
\end{Bmatrix} \\
∅ 空集合
$$


### Set Opertaion

$$
A \cup B \quad \text {A or B 聯集} \\
A \cap B \quad \text {A and B 交集}
$$

- different

A - B

在A不在B

### Set Relation

- subset：小於等於 S 集合

舉例：**A 是 B 的子集**表示為 A⊆B

- proper subset：小於 S 集合
- power set：S 集合的所有子集 
- disjoint：兩集合之交集為空集合

### 迪摩根定律

- 反(A且B) = 反A 或 反B
- 反(A或B) = 反A 且 反B

快速記憶：消除括號 反或 要相反

自行畫圖或推論也可推得此定律

## Mathematical Induction and Functions

### 數學歸納法

數學歸納法需要滿足兩個條件：

- f(1) is true
- if f(k) is ture, then f(k + 1) is true , for every k in N

請不要執著於這個形式，

條件可以有很多，

要確保整個證明是對的，

n 可以推到 n +1，n + 1 可以推到 n + 2 ...

### 函數

`f: A -> B`

定義：集合 A 對應到集合 B

`A:domain of f`

`B: codomain of f`

`f(A) = {f(a) | a in A}`

- one-to-one (injective)

一對一函數，一個輸入對應一個輸出

這也代表`if f(a1) = f(a2), then a1 = a2`

- onto (surjective)

`if f(A) = B, for all b in B there is at least one a in A with f(a) = b`

白話的說就是集合 B 中每個元素都會被至少一個 a 對應到

- bijective

同時是 one-to-one 且 onto

- 合成函數

`(g o f)(a) = g(f(a))`

不滿足交換律

滿足結合律

- 反函數 inverse

反函數是唯一的。

如果一個函數有反函數，若且為若，函數是 bijective

one-to-one 是因為如果多個映射，反函數推回來會有多個，不合理

onto 是因為有反就有正，如果有東西沒被映射到，不合理

## Pigeonhole Principle

一個鴿子住一個洞

如果鴿子比洞多，那一定有多個鴿子要擠進一個洞

這也可以套用在函數、集合問題中

**舉例**：找出問題的目標，問題的集合大小，如果目標比集合還要大，就可以知道問題能否成立

## Relation

Congruence modulo m

- `m | a-b` : a - b 是 m 的倍數

### 整除 3

以前學過整數 x 的每個位數加總能整除 3 代表 x 能被 3 整除，

試證明

> 以 168 為例
>
> (
> 	(1 * 10^2) mod 3 + 
> 	(6 * 10) mod 3 + 
> 	8 mod 3
> ) mod 3
>
> 因為 10^n mod 3 = 1
>
> 所以 10 mod 3 都等於 1
>
> 得 (1 + 6 + 8 ) mod 3

### Equivalence relation

等價關係（Equivalence relation）是具有自反性，對稱性，遞移性的二元關係

"=" 是一種等價關係

### Partial Orders

部分排序

- 自反性
- Antisymmetric : aRb and bRa => a = b
- 遞移性 

Antisymmetric：要能排序的話，a如果能在b的前面又在b的後面，那a = b。

#### example

A = N(自然數); a R b <=> a|b

反身性：a|b  for all <- N

Antisymmetric：if a|b and b|a, than 

b/a and a/b 都是正整數 and (b/a)^-1=a/b（互為倒數）

因 a/b ＝1 所以 a=b

遞移性： if a|b and b|c, than b=aq, c=bw, for some q, w <- N

因 c = aqw, 所以 a|c

#### example

consider the power set P(S) of a set S

Power set：S的所有部分集合所構成的集合

than "<=" （包含於） is a pitial order on P(S)

1. 反身性 A <= A
2. A <= B B<= A => A=B
3. A <= B and B <= C , than A <= C

### Hasse diagram 哈斯圖

a != b

若 a <= b <= c

則畫 a -> b, b -> c

不能畫 a -> c
