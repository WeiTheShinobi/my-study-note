# *《Clean Code》*

> 筆記作者：Wei the Shinobi 葉高緯
>
> 筆記

開發時，我們花費大量的時間在閱讀舊程式碼，

因為要了解舊的才能寫新的，

所以**提高可讀性就是提高效率**。

## 第二章 有意義的命名

簡單的程式碼也可以難以閱讀

要避免使用抽象的命名

讓名稱代表意圖

- 避免誤導

不要相似的名稱，也不要會被誤認為其他單詞的名稱，小寫的`l`和大寫的`O`

- 有意義的區別

message 和 messageInfo 沒有區別

- 類別用名詞、方法用動詞

- 一個概念使用一種字詞

例如 controller 和 manager

讓人難以看出差別

- 專業領域的命名

使用的設計模式的名稱、演算法的名稱

- 無意義的上下文資訊

## 第三章 函式

- 函式應該要盡量的簡短，易於閱讀
- 傳入布林值是糟糕的作法，因為這讓函式做了兩件事，不如拆成兩個
- 傳入的參數應該越少越好，可以傳入物件代替多個參數
- 不要有副作用
- 使用例外取代回傳錯誤碼

一開始寫出的函式肯定雜亂，但通過準則慢慢修改到好。

## 第四章 註解

好的程式碼不需要註解，

註解是用來彌補我們的失敗。

尤其註解可能會隨著更新欺騙你，但程式碼絕對不會。

好的註解：

- 可以寫下解釋這段程式碼的意圖
- 可以寫下程式碼的問題 ex. not thread safe
- // TODO 可以讓 IDE 找到代辦事項，但要記得刪除
- 公共 API 裡的 Javadoc，無庸置疑

爛的註解：

- 把程式碼的過程描述一次，直接看程式碼就好了
- 註解不夠精確，導致誤導
- 日誌註解，請使用 VCS
- 署名，VCS 知道作者是誰，而且隨著時間可能也不一定了
- 註解程式碼

## 第五章 編排

- 程式碼應該像報紙一樣，短而多篇，而不是大長篇。
- 當程式碼進入不同的概念，用空白分隔開。
- 為了不再讀程式碼時跳來跳去，應該有些固定的規則

變數宣告在開始

相依的函式要在附近，呼叫要在被呼叫的上方

概念相似的函式也要放一起

- 重要的是團隊的一致性

## 第六章 物件及資料結構

隱藏實作的細節，就是抽象化的過程。

結構化的程式碼容易添加新的函式，但不容易添加新的資料結構；

物件導向的程式碼容易添加新的類別，但不易更動已有函式。（因為要全都改）

- 火車事故

超長一串相連的程式碼呼叫

```java
String test = obj.getA().getB().getC().getD().getE();
```

- 隱藏結構

不應該需要去了解結構，而是告訴它去做某些事。

## 第七章 錯誤處理

- 包裝第三方的 API 是好的技巧，因為將來你可以更輕鬆的換函式庫或是修改。
- 發生例外時，要提供足夠去追蹤的訊息
- 不要回傳 null

如果你忘了檢查，事後 NPE 很難去修正，其次是一堆 null 檢查很糟，

你可以回傳**特殊情況物件**或 **`empty list (Collections.emptyList())`**或**長度為0的陣列**

- 不要傳遞 null，容易有潛在的錯誤

## 第八章 邊界

- 雖然 Map 很好用，但不要再系統內傳遞它，你可以封裝它再使用。
- **學習式測試(learning tests)**：替第三方 API 寫測試，檢驗我們的了解，測試重點在於我們想要從 API 獲得什麼結果。

## 第九章 單元測試

測試驅動開發的三大法則：

1. 在撰寫一個單元測試前，不可撰寫任何產品程式。
2. 只撰寫剛好無法通過的單元測試，不能編譯也算無法通過。
3. 只撰寫剛好能通過當前測試失敗的產品程式。

- 測試步驟：建造、操作、檢查
- 測試會隨產品一起成長，所以好的測試很重要

## 第十章 類別

類別的名稱可能是幫忙決定大小的第一個手段，

太過模玲兩可的名稱會讓類別很巨大。

我們希望系統由許多小類別組成，

每個小類別都只有「一個修改的理由」。

類別應該只有少量的實體變數，類別的每個方法都應該操作一個或多個類別的變數。

**凝聚性**：類別的方法操作類別變數的程度

如果你的程式凝聚性很低，

代表你應該拆解成多個小程式。

## 第十一章 系統

## 第十二章 羽化

若遵守以下守則，一個設計就可以說是「簡單的」：

- 執行完所有的測試
- 沒有重複的部分
- 表達程式設計師的本意
- 最小化類別和方法的數量

守則根據重要度排序。

為什麼測試很重要？

因為一個完整的測試才能讓你好好的重構。

在移除高層級的重複程式碼時，模板模式是常用的技巧。

具表達力的技巧：

- 好的名稱
- 簡短
- 標準命名法

最重要的是去嘗試。

## 第十三章 平行化

平行化幫助我們將「做什麼」與「何時做」分解開來

平行化會出現很多問題

- 單一職責原則，將系統拆解成一群 POJO，將「與執行緒有無關聯」劃分。
- 限制資料的視野，兩個執行緒修改同一個欄位會出錯，要去限制
- 使用資料的副本
- 或者，讓執行緒獨立運行，就不會有共享資料的問題

同步方法之間的相依性，會導致平行化程式碼裡面的細微錯誤。

如果再同一個共享類別裡，有超過一個以上的同步方法，那你的系統就可能是寫錯了。

- 讓同步區塊越短越好，因為同步很昂貴
- 多執行緒撰寫 Shut-Down 程式碼是困難的

測試：

- 重視失敗

多執行緒的失敗可能執行千次才失敗一次，所以不要把失敗怪給機器

- 先讓非執行緒的程式碼能夠運行
- 執行比處理器數還多的執行緒，鼓勵讓系統**工作切換**
- 在不同平台上運行
- 調整程式碼，使之產生失敗

為了測試，你可以插入呼叫`yield()`，

可能會讓以前不會失敗的地方失敗，

這是為了讓錯誤放大，因為你的程式碼會失敗代表真的有問題。

也可以使用自動化的工具。

## 第十四章 持續地精煉

>  本章示範了如何重構、精煉程式

與其說程式設計是門科學，不如說程式設計是一門技藝更為貼切。

我們並不會一開始就能寫出完全的程式，

而是寫下粗略草稿然後一步步修改。

程式的腐敗是慢慢形成的，

停下腳步漸近修改避免腐敗擴大。

配合自動化的單元測試，

讓你修改程式時更加快速，

單元測試在重構中是重要的一部份。

總結：

- 只讓程式能夠正常運作是不夠的，恰好能運作代表容易損壞
- 糟糕的程式碼一旦開始發酵，就會讓你被拖垮
- 相比程式碼腐敗，盡快把程式碼清理乾淨成本低很多
- 測試是重要的

## 第十五章 JUnit 的內部結構

## 第十六章 重構 SerialDate

## 第十七章 程式碼的氣味與啟發

- 不要註解程式碼，其他人維護會很麻煩，請直接刪掉它
- 遵循「最少驚奇原則」
- 注意邊界條件
- 不一致性，同一種事要用同一種方法做。
- 避免選擇型參數， true 和 false 不知道代表什麼意思，不如拆成數個 function 吧
- 封裝條件判斷，boolean 邏輯並不是很好懂
- 避免否定的條件判斷，盡可能採用肯定判斷
- 不要隱藏時序耦合，你照著某個順序呼叫函式，但另一個程式設計師可能不知道。
- 使用萬用字元 * 取代一長串的引入
- 不要繼承常數
- 在較大的視野使用較長的命名
