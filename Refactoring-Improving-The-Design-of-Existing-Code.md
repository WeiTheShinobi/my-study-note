## *Refactoring : Improving The Design of Existing Code*

### 第一章

- 如果程式碼很難修改，先重構
- 先寫測試才能重構
- 測試、小修改、測試、小修改…

### 第二章

重構：在不改變**可受觀察之行為**下調整結構

重構應該隨時隨地進行，系統是長期的，不應該為了今天放棄明天。

最佳化所有程式報酬不高，要找出花費大的部分最佳化。

### 第三章 Bad smells in Code

- duplicated code
- long method

現代程式語言免去了呼叫函式的額外開銷，

當你有需要說明的程式碼，

把程式碼放進新的函式中，

把函式的命名當成註釋，並以用途命名。

- large class
- long parameter list

物件導向語言中，

可以幫函式需要的東西放在 class 欄位中

- divergent change

一個 class 受到多種變化而需要修改，

應該讓 class 只會因為一種原因而修改

- shotgun surgery

一個需求需要修改多個 class，

可以幫需要修改的提取出放進一個 class 中，

如果沒有合適的可以新建一個

- feature envy

函式對別的 class 的依賴大於自己所處的 class 中

改善方法是移動該函式

盡量保持**變化只在一個地方發生**

- data clumps
- primitive obsession

新手通常不願意在小任務上運用小物件，

像是 money class 或有起始與結束值的 range class

- switch statements

少用 switch，用多型

但殺雞也別用牛刀

- parallel inheritance hierarchies
- lazy class

每個 class 都有其維護成本

如果不需要就刪掉

- speculative generality

目前用不到就別寫

- temporary field

class 中的某些欄位可能只服務單一特定的函式

不如把它們提煉成一個 method object

- message chains



