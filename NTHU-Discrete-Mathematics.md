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

