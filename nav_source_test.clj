(ns nav-source-test
  (:use nav-source clojure.test))

(def expected-first-line-tokens {:type "Table" :id 70902 :name "Test Table (48)"})
(def valid-first-line "OBJECT Table 70902 Test Table (48)")
(def invalid-first-line "OOBJECT Table 70902 Test Table (48)") ; see "OOBJECT"!
(def first-line-with-invalid-component "OBJECT Bird 70902 Test Table (48)")
(def first-line-with-invalid-structure "OBJECT 70902 Table Test Table (48)")
(def valid-last-line "}")
(def invalid-last-line " }")

(deftest given-a-string-with-an-invalid-structure-for-a-first-line_matches-first-line-structure?_should-return-false
  (is (false? (matches-first-line-structure? first-line-with-invalid-structure))))

(deftest given-a-string-with-a-valid-structure-for-a-first-line_matches-first-line-structure?_should-return-true
  (is (true? (matches-first-line-structure? valid-first-line))))



(deftest given-a-valid-last-line_matches-last-line-structure?_should-return-true
  (is (true? (matches-last-line-structure? valid-last-line))))

(deftest given-an-invalid-last-line_matches-last-line-structure?_should-return-false
  (is (false? (matches-last-line-structure? invalid-last-line))))



(deftest given_a_map_of_valid_first_line_tokens__valid-first-line-tokens?__should_return_true
  (is (true? (valid-first-line-tokens? expected-first-line-tokens))))



(deftest given_a_valid_first_line__first-line-tokens__should_return_a_map_with_first_line_tokens
  (let [tokens (first-line-tokens valid-first-line)]
    (is (= expected-first-line-tokens tokens))))


(deftest make-single-file-name-returns-uniquely-identifiable-filename
  (is (= "1-Table-70902.txt" (make-single-file-name (first-line-tokens valid-first-line)))))

(run-tests 'nav-source-test)
