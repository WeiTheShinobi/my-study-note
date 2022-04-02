## Linear Algebra - NTU version

> 學習筆記
>
> [[線性代數\] 第1-1 單元: Introduction to Linear Algebra - YouTube](https://www.youtube.com/watch?v=IG-EQUIk7P0&list=PLcCssb7oxfAsRFq8V8hDxB4oxCXSjl5Om&index=1&ab_channel=臺大科學教育發展中心CASE)

### Basic Concepts on Matrices and Vectors

- 矩陣相等( equal )：兩個矩陣大小相等且所有元素一樣

`aij = bij`

- 子矩陣( submatix )：從一個矩陣中刪除若干個 rows / columns

- 矩陣的加法

矩陣的大小( m*n )必須相等

同樣的位置相加即可 `aij + bij`

- 係數積( scalar multiplication )

由矩陣與一個係數構成

係數 * 矩陣所有元素 即可

- 零矩陣( zero matrix )

以 O 表示，元素全為 0

任何矩陣乘 O 得 O

但 A * O 不一定是 B * O

因為矩陣大小不一定一樣

- transpose

將一個 mn 矩陣A 變成 nm 矩陣AT且 `aij -> aji`

`(AT)T = A`

- vector

one row / column matrix

加法與係數積同矩陣

- linear combination

u1, u2 ... uk

c1u1, c2u2 ... ckuk

兩個向量不平行：u, v 皆非零向量且 u != cv ( u 不是 v 的係數積)

在二維中，如果 u v 向量不平行，任何向量都可以寫成 u v 的線性組合( 唯一線性組合 )

- standard vectors

e1 = [1,0...,0]

e2 = [0,1...,0]

每個向量都可以被 standard vectors 線性組合

v_ = [v1, v2 ... vn]

= v1 * [1,0...,0] + v2 * [0, 1....0] + .... vn * [0,0...1]

- matrix-vector product

一個矩陣和一個向量的乘積

Av = a1v1 + a2v2 + ... anvn

Av = [a1, a2 ... an] * [v1, v2 ... vn]T

![001](C:\Information\IntellijProjects\my-study-note\image\Linear-Algebra-NTU-version\001.png)

- identity matrix

一個由 standard vectors 構成的正方形矩陣

表示為 In

![002](C:\Information\IntellijProjects\my-study-note\image\Linear-Algebra-NTU-version\002.png)

所有矩陣乘以 In 不會改變

- stochastic matrix

所有 colume 相加為 1 且 所有元素非負

且矩陣為正方形

`[[0.53, 0.4],[0.47, 0.6]]`

### System of Linear Equations 

一個方程式稱為 linear equation

聯立方程式稱為 system of linear equation
![003](C:\Information\IntellijProjects\my-study-note\image\Linear-Algebra-NTU-version\003.png)

- 每個聯立方程式只有**無解**、**一組解**或**無限多組解**

- 連續、不連續

如果聯立方程式是連續的，那有一組解或無線多組解

不連續則無解

- 相等

兩聯立方程式如果有**相同的 solution set 則 相等**

- elementary row operations

不改變 solution set 的方程式轉換

- augmented matrices

將聯立方程式整個放進矩陣中

![004](C:\Information\IntellijProjects\my-study-note\image\Linear-Algebra-NTU-version\004.png)