(ns nav-source-test
  (:use nav-source clojure.test))

(def expected-first-line-tokens {:type "Table" :id 70902 :name "Test Table (48)"})

(def lines {:first {
              :valid-structure {
                :valid-tokens "OBJECT Table 70902 Test Table (48)"
                :invalid-object-type "OBJECT Bird 70902 Test Table (48)"}
              :invalid-structure {
                :id-at-wrong-position "OBJECT 70902 Table Test Table (48)"
                :wrong-start-token "OOBJECT Table 70902 Test Table (48)"}}
            :last {
              :valid "}"
              :invalid " }"}})
              
; matches-first-line-structure? tests
(deftest matches-first-line-structure?__given-a-string-of-valid-structure-and-with-valid-tokens__should-return-true
  (is (true? (matches-first-line-structure? (get-in lines [:first :valid-structure :valid-tokens])))))
  
(deftest matches-first-line-structure?__given-a-string-with-id-at-wrong-position__should-return-false
  (is (false? (matches-first-line-structure? (get-in lines [:first :invalid-structure :id-at-wrong-position])))))
  
(deftest matches-first-line-structure?__given-a-string-of-valid-structure-but-with-an-invalid-object-type-token__should-return-true
  (is (true? (matches-first-line-structure? (get-in lines [:first :valid-structure :invalid-object-type])))))

(deftest matches-first-line-structure?__given-a-string-with-wrong-start-token__should-return-false
  (is (false? (matches-first-line-structure? (get-in lines [:first :invalid-structure :wrong-start-token])))))


; matches-last-line-structure? tests
(deftest matches-last-line-structure?__given-a-valid-last-line__should-return-true
  (is (true? (matches-last-line-structure? (get-in lines [:last :valid])))))
  
(deftest matches-last-line-structure?__given-an-invalid-last-line__should-return-false
  (is (false? (matches-last-line-structure? (get-in lines [:last :invalid])))))


; first-line-tokens tests
(deftest first-line-tokens__given-a-string-of-valid-structure__should-return-a-map-with-tokens
  (let [tokens (first-line-tokens (get-in lines [:first :valid-structure :valid-tokens]))]
    (is (= expected-first-line-tokens tokens))))

; valid-object-type-string? tests
(deftest valid-object-type-string?__given-a-valid-object-type-string__should-return-true
  (is (true? (valid-object-type-string? "Table"))))

(deftest valid-object-type-string?__given-an-invalid-object-type-string__should-return-false
  (is (false? (valid-object-type-string? "Bird"))))

; valid-first-line-tokens? tests
(deftest valid-first-line-tokens?__given-a-map-of-valid-first-line-tokens__should-return-true
  (is (true? (valid-first-line-tokens? expected-first-line-tokens))))


; make-single-file-name tests
(deftest make-single-file-name__given-a-valid-map-of-first-line-tokens__should-return-a-string-for-a-uniquely-identifiable-filename
  (is (= "1-Table-70902.txt" (make-single-file-name (first-line-tokens (get-in lines [:first :valid-structure :valid-tokens]))))))


(run-tests 'nav-source-test)
