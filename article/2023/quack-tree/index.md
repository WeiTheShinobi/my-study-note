---
author: WeiTheShinobi
title: 會打字就行！用 Go 語言實作好玩小專案：tree
date: 2023-02-25
description: 會打字就行！用 Go 語言實作好玩小專案：tree
---

# 會打字就行！用 Go 語言實作好玩小專案：tree

<img src="01.png">

tree 是一個**很讚的工具**，它可以把你的資料夾底下的檔案弄成一個樹的型狀，非常的酷。**實作這個工具不難**，而且也可以擴充很多很酷的功能，本篇將帶大家實作最基本的功能：**遞迴印出資料夾下的檔案**，我們會使用 Go 語言來實作，一起動動手吧！

## 什麼是樹？

樹是一種資料結構，那什麼是樹？為何叫樹？就像是我們在看星星，**一顆一顆的星星連起來成了星座**。我們看著許許多多的記憶體位址灑在天空，**當一個位址指向另一個位址，我們就畫一條線把他們連起來，而最後這個圖形就像樹一樣**。同樣的道理，這樣也能理解什麼是**鏈結串列**了：**「排成一串的記憶體位址」**，別被名字被疑惑，思考事情的本質。

```go
type TreeNode struct {
  data any
  nodes []*TreeNode
}
```

### 樹跟我們的 tree 有什麼關係？

看看樹的形狀，再看看你的資料夾，你有發現什麼嗎？這兩個東西形狀真像呢！所以我們可以把這些目錄轉換成樹型資料結構，真是太棒了！而當我們做完第一個資料夾，我們只要**遞迴**的往子資料夾重複一樣的動作，就可以延伸我們的枝幹。

### 前往每個樹節點

透過遞迴的方式，可以輕鬆的拜訪每個樹的節點，邏輯是這樣的：拜訪這個節點，如果節點有子節點就拜訪，否則結束，非常的簡單吧！有一些方式可以讓我們選擇，這裡我們使用**深度優先搜尋（Depth-First-Search，DFS）**，如字面上的意思，相對於**廣度優先搜尋**，**深度優先**是指如果可以往下走就往下走，不能往下才往側邊走。這裡先有個概念，在接下來的實作中很有幫助。

### 所謂遞迴

對於不習慣使用遞迴的人來說遞迴很陌生，其實遞迴跟迴圈差不多，就是**重複做一樣的事情**，輸入的參數就是狀態。**每一次創建樹節點**就是在做一樣的事情。這次問題的差別在於查詢的路徑不一樣，所以就以路徑當作參數。以下示範遞迴與迴圈從 1 加到 10 總和的實作，還不理解的讀者可以多多思考。

```go
func main() {
	// 以迴圈的方式
	var result int
	for i := 1; i <= 10; i++ {
		result += i
	}
	fmt.Println(result == 55)

	// 以遞迴的方式
	var recursive func(n int) int
	recursive = func(n int) int {
		if n == 1 {
			return 1
		}
		return n + recursive(n-1)
	}
	fmt.Println(recursive(10) == 55)
}
```

<img src="02.png">

## 開始

程式碼：[WeiTheShinobi/quack-tree: implement tree in go (github.com)](https://github.com/WeiTheShinobi/quack-tree)

這次會分兩個部分，一是建構出樹，二是印出樹。我們先創建我們的專案，然後新增檔案，專案結構如下。

```
.
├── dir.go
├── go.mod
└── main.go
```

### 樹節點

這個資料結構是我們的樹節點，有自己的路徑、底下的檔案與資料夾。

檔案名：dir.go

```go
type Dir struct {
	path    string
	files   []string
	subDirs []*Dir
}
```

### 步驟一：創建

檔案名：dir.go

```go
func Build(path string) (*Dir, error) {
  d := &Dir{path: path}
	files, err := ioutil.ReadDir(path)
	if err != nil {
		return nil, err
	}
  
  return d, nil
}
```

真的很簡單，這裡先單純的創建一個`Dir`物件，這個`Build`函式會回傳`(*Dir, error)`。我們使用`ioutil.ReadDir()`替我們搜尋路徑的檔案，它會**與作業系統互動**並返回`([]fs.FileInfo, error)`，我們再處理回傳值即可，現在再加上處理的程式碼。

```go
func Build(path string) (*Dir, error) {
	d := &Dir{path: path}
	files, err := ioutil.ReadDir(path)
	if err != nil {
		return nil, err
	}
	
  // 處理回傳檔案資訊
	for _, file := range files {
    // 判斷是否是資料夾，決定要不要遞迴
		if file.IsDir() {
      // 遞迴創建樹，輸入的參數是子資料夾的路徑
			sd, err := Build(path + "/" + file.Name())
			if err != nil {
				fmt.Println(err)
				continue
			}
      // 忽略隱藏的資料夾
			if file.Name()[0] == '.' {
				continue
			}
			d.subDirs = append(d.subDirs, sd)
		} else {
      // 不是資料夾，直接加即可
			d.files = append(d.files, file.Name())
		}
	}

	return d, nil
}
```

這裡思路是這樣的：`Build`出一個節點，`Build`出它的子節點，重複做下去。

<img src="04.png">

### 步驟二：印出

印出的實作與創建差不多，同樣以**深度優先**的方式**走過每個節點**，走過即印出，並不難，我們可以先從簡單的開始，再慢慢完善功能。

檔案名：main.go

```go
func main() {
	d, _ := Build(".")
	fmt.Println(".")
	d.Print()
}
```

檔案名：dir.go

```go
func (d *Dir) Print() {
	for _, fileName := range d.files {
		fmt.Println(fileName)
	}

	for _, dir := range d.subDirs {
		fmt.Println(dir.path)
    // 遞迴
		dir.Print()
	}
}
```

輸出：

```bash
go run .

.
dir.go
go.mod
main.go
```

這一段簡單的程式碼就可以印出資料夾了，但是這還不夠，因為這沒有印出**資料夾的結構**，而只是印出檔名而已，我們想要一目了然，看起來像個**樹**，越深的節點需要在前面印出**越多空白或是`│ `符號**，如下所示。

```
    │   │   ├── foo
    │   │   │   └── foo.go
```

繼續編寫我們的程式碼，讓它能夠輸出樹的結構，而這會用到一些符號，照著以下規則輸出：

- `└`用於最後一個檔案。
- `├`非最後一個檔案。
- `─`、`│`用於連接。
- 父資料夾如果有其他檔案還沒輸出，前綴就要加上`│`，反之是空格。

我們只要依照這四個規則實作即可，至於前綴的話，它代表**該節點的每個父節點是不是最後一個資料夾**，我們可以選擇**修改函式的簽名**，把前綴當作參數傳入。雖然看起來很複雜，但就是在原有的結構上加上一些條件，並不難，改寫好得程式碼如下：

檔案名：dir.go

```go
func (d *Dir) Print(prefix string) {
	for i, fileName := range d.files {
    // 如果是最後一個檔案的情況
		if i == len(d.files)-1 && len(d.subDirs) == 0 {
			fmt.Printf("%s└── %s\n", prefix, fileName)
		} else {
			fmt.Printf("%s├── %s\n", prefix, fileName)
		}
	}

	for i, dir := range d.subDirs {
		// 將路徑以 `/` 分割，取最後一個
		splitPath := strings.Split(dir.path, "/")
		lastFileName := splitPath[len(splitPath)-1]

		if i == len(d.subDirs)-1 {
      // 如果是最後一個檔案的情況
			fmt.Printf("%s└── %s\n", prefix, lastFileName)
			dir.Print(prefix + "    ")
		} else {
			fmt.Printf("%s├── %s\n", prefix, lastFileName)
			dir.Print(prefix + "│   ")
		}
	}
}
```

```bash
go run .
.
├── dir.go
├── go.mod
└── main.go
```

這樣我們就能輸出**美麗的資料夾樹**了，真是太棒了！

### 編譯執行檔

這裡示範用`go build`來編譯出執行檔，`-o`可以寫檔案名，也可以直接把路徑寫入，只要把執行檔放在有**環境變數**的地方即可使用囉！以下是我的電腦為例。

```bash
go build -o ~/go/bin/quack-tree
quack-tree
```

### 還可以增加的功能

有很多有趣的功能可以考慮：

- 例如選擇檔案路徑

- 完整路徑名

- 效能優化

- 把結果輸出成一個檔案

- 其他酷東西

當然在你可能還需要實作一個**命令列參數分析**來解析輸入，讓程式知道需要做什麼，然後你就可以用`-a`、 `--help`等等之類的神奇魔法來控制程式，就像在用其他功能一樣，像是`ls -a`。

<img src="03.png">

## 結語

感謝觀看，自己動手做會很有成就感的，**實作是很重要的**，思考樹和遞迴，希望你喜歡這次的內容，最後再提供一個簡潔的寫法。

ＷeiTheShinobi

```go
func printTree(path string, prefix string) {
	files, err := ioutil.ReadDir(path)
	if err != nil {
		fmt.Println(err)
		return
	}

	for i, file := range files {
		if file.IsDir() {
			if i == len(files)-1 {
				fmt.Printf("%s└── %s\n", prefix, file.Name())
				printTree(filepath.Join(path, file.Name()), prefix+"    ")
			} else {
				fmt.Printf("%s├── %s\n", prefix, file.Name())
				printTree(filepath.Join(path, file.Name()), prefix+"│   ")
			}
		} else {
			if i == len(files)-1 {
				fmt.Printf("%s└── %s\n", prefix, file.Name())
			} else {
				fmt.Printf("%s├── %s\n", prefix, file.Name())
			}
		}
	}
}
```

