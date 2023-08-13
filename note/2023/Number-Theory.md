### Number Theory

>參考資料：[數論 01 Division Algorithm - YouTube](https://www.youtube.com/watch?v=HX5mint6-gs&list=PL-rFbNhZ7F6KTU1EuB3u3AI7mSgdOBNVM&index=2&t=8s&ab_channel=ChihShengChuang)

數論全是整數

#### Division Algorithm


$$
let \quad a \in Z\quad , b\in N \\
then \quad \exists!\,q\quad ,r\in N \\
a = bq + r \\
where \quad 0 \leq r \lt b
$$
證明：
$$
let\,S =
\begin{Bmatrix}
	a-xb:a-xb\ge0,\,x\in Z
\end{Bmatrix} \\
$$

if  r >= b：

by well ordering principle

S 集合有一個最小值 minS = r
$$
r = a - bq = minS \ge b\\

a - bq \ge b\\

a - bq - b \ge 0\\

a - b * (q - 1) \ge minS = r = a - bq \ge 0\\

-b \ge 0\\

b < 0 \quad but\, b \in N 矛盾\\
因此\,r < b
$$

#### GCD

