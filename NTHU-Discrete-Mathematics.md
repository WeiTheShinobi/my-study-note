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

### total order

一個集合任取兩個都能比較則為 total order

---

- def

一個 Partial Orders 的 maximal 或 minimal 代表沒其他元素比他小或大，但這不代表他比所有人大，因為有些不能比較。

- def

greatest element/ least element 比所有的元素都大/小，這也代表他們跟所有元素都能比較。

greatest 與 maximal 的差別在於能否比較，minimal, least 同理。

- Upper bound, lower bound（上界、下界）

如字面的意思，即是集合中的上下界。

greatest lower bound = infimum

least upper bound = supremum

glb, lub 這兩個不一定會再集合中

這是蠻直觀的東西，照著字面推理即可。

## Principles of Counting

### addition principle 加法原理

A, B 皆為有限集合，且交集為空集合，則：

- |A or B| = |A| + |B|

### multiplication principle

A, B 皆為有限集合，則：

|A * B| = |A| * |B|

#### expample

|a| = m, |b| = n （集合的長度）

求 a 到 b 的做法數：

a 中的每個元素會有 n 個走法

所以會是 n * n * n..... * n = n^m

### 不重複選擇

從 set a 取 m things，求解法數

`n(n-1)(n-2)...(n-m+1)`

該做法就是高中學機率時的 C

### permutation

#### factorial

`!`，譯作乘階

`n! = n * (n-1)!; n >= 1; 0! = 1`

#### example

10個人排序，求做法數：

10 * (10-1) * (10-2) ... 10-9 = 10!

---

10個人排序，但 p2, p3, p4 要在一起，求做法數：

G = {p2, p3, p4}

所以可以轉換成：p1, G, p5, p6 ... p10

把三個人當成一個，這樣計算得 8!

G 內的 3 人排序得 3!

所以答案是 8! * 3!

- unordered selection

現在不管順序了，求做法數：

因為不同順序都只能算一個，所以要把重複的去掉，得：

`n(n-1)(n-2)(n-3)...(n-m+1) / m!`

即是 binomial coefficient，與高中學的 C 不同，C 是組合數，而且 n >= m，binomial coefficient 則沒關係，唸作 n choose m。

#### example

set |A| = 8, 求 A 有多少 3 個元素的子集？

`8 choose 3 = (8 * 7 * 6) / (3 * 2 * 1) = 56`

---

求 8 choose 9：

當下面比上面大的時候為 0

### Pascal's Identity

`n choose r = (n-1 choose r-1 ) + (n-1 choose r)`

不需要硬背公式，`n choose r`是有 r 個元素的子集數，`n-1 choose r-1`指子集固定有一個某數的子集數（假設為x），`n-1 choose r`則是沒有 x 的子集數。根據加分原理，兩者為空集合，所以全部數目就是兩個 size 相加。

這個關係式可以畫成一個帕斯卡三角形（例：`3 choose 2 = (2 choose 1) + (2 choose 2)`）

### Binomial Theorem

$$
(x+y)^n=Σ_{r=0}^{n} C(n, r) x^{n-r}y^r
$$

#### example

 "MISSISSIPPI" 可以有多少種排序？

十一個字：11!

四個 S 四個 I 兩個 P 一個Ｍ：4!4!2!1!

答案：11! / 4!4!2!1!

### Multinomial Theoerm 

$$
(x_{1}+x_{2}+...x_{k})^n=Σ_{x_{1}+x_{2}+...x_{k}=n} C(n, (x_{1},x_{2}...x_{k})) x_{1}^{n_{1}}+x_{1}^{n_{2}}+...x_{1}^{n_{k}}
$$

### Principle of Inclusion and Exclusion

$$
|A ∪ B| = |A| + |B| - |A ∩ B|
$$

關鍵在於把重複的扣掉
