(ns nav-source-test
  (:use nav-source clojure.test))

(def expected-metadata {:type-id 1 :type "Table" :id "70902" :name "Test Table (48)"})
(def valid-first-line "OBJECT Table 70902 Test Table (48)")
(def invalid-first-line "OOBJECT Table 70902 Test Table (48)") ; see "OOBJECT"!
(def valid-last-line "}")

(deftest given-a-valid-first-line_first-line-of-object-source?_should-return-true
  (is (true? (first-line-of-object-source? valid-first-line))))

(deftest given-an-invalid-first-line_first-line-of-object-source?_should-return-false
  (is (false? (first-line-of-object-source? invalid-first-line))))
  
(deftest given-a-valid-last-line_last-line-of-object-source?_should-return-true
  (is (true? (last-line-of-object-source? valid-last-line))))

(deftest parsing-first-line-returns-map-of-named-elements
  (is (= expected-metadata (parse-first-line valid-first-line))))

(deftest make-single-file-name-returns-uniquely-identifiable-filename
  (is (= "1-Table-70902.txt" (make-single-file-name (parse-first-line valid-first-line)))))

(run-tests 'nav-source-test)
