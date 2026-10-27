# api-middle-practice
api中間課題

## 集計API（Postman）

HTTPメソッドはどちらも `GET`。`month` は必須で `yyyy-MM` 形式を指定する。
取引日 `trans_date` を対象に、月初以上・翌月初未満の範囲で集計する。

`postman/house-summary.postman_collection.json` をPostmanにインポートすると、
2つの集計と不正な月のチェックを実行できる。変数 `baseUrl` と `month` を環境に合わせて変更する。

### 月ごとの収支

`http://localhost:8080/api/houses/summary/monthly?month=2026-10`

```json
{
  "month": "2026-10",
  "income": 300000,
  "expense": 40000,
  "balance": 260000
}
```

`income` は type が `収入` の合計、`expense` は `支出` の合計。
`balance` はその月の収入から支出を引いた差額（前月からの繰越は含まない）。
収支合計では、それ以外の type は計算に含めない。
取引がない月は各金額を `0` で返す。

### 月ごとのカテゴリ別集計

`http://localhost:8080/api/houses/summary/categories?month=2026-10`

```json
[
  { "type": "収入", "category": "給与", "totalAmount": 300000 },
  { "type": "支出", "category": "交通費", "totalAmount": 10000 },
  { "type": "支出", "category": "食費", "totalAmount": 30000 }
]
```

同じカテゴリ名でも type ごとに分けて集計する。取引がない月は `[]` を返す。
並び順は type、category の昇順（データベースの照合順序に従う）。
不正な月（例：`2026-13`）や month の未指定は `400 Bad Request`。

Postmanでは上記URLをGETで実行し、登録済み取引の金額と照合する。
月末と翌月初の取引を登録し、翌月初の取引が当月集計に入らないことも確認する。
