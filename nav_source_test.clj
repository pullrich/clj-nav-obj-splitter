(ns nav-source-test
  (:use nav-source clojure.test))

(def expected-first-line-tokens {:type "Table" :id 70902 :name "Test Table (48)"})
(def valid-first-line "OBJECT Table 70902 Test Table (48)")
(def invalid-first-line "OOBJECT Table 70902 Test Table (48)") ; see "OOBJECT"!
(def first-line-of-right-structure-but-with-invalid-token "OBJECT Bird 70902 Test Table (48)")
(def first-line-with-invalid-structure "OBJECT 70902 Table Test Table (48)")
(def valid-last-line "}")
(def invalid-last-line " }")

(deftest matches-first-line-structure?__given-a-string-with-an-invalid-structure-for-a-first-line__should-return-false
  (is (false? (matches-first-line-structure? first-line-with-invalid-structure))))
(deftest matches-first-line-structure?__given-a-string-with-a-valid-structure-for-a-first-line__should-return-true
  (is (true? (matches-first-line-structure? valid-first-line))))



(deftest matches-last-line-structure?__given-a-valid-last-line__should-return-true
  (is (true? (matches-last-line-structure? valid-last-line))))
(deftest matches-last-line-structure?__given-an-invalid-last-line__should-return-false
  (is (false? (matches-last-line-structure? invalid-last-line))))



(deftest valid-first-line-tokens?__given-a-map-of-valid-first-line-tokens__should-return-true
  (is (true? (valid-first-line-tokens? expected-first-line-tokens))))



(deftest first-line-tokens__given-a-valid-first-line__should-return-a-map-with-first-line-tokens
  (let [tokens (first-line-tokens valid-first-line)]
    (is (= expected-first-line-tokens tokens))))



(deftest make-single-file-name__given-a-valid-map-of-first-line-tokens__should-return-a-string-for-a-uniquely-identifiable-filename
  (is (= "1-Table-70902.txt" (make-single-file-name (first-line-tokens valid-first-line)))))

(run-tests 'nav-source-test)
