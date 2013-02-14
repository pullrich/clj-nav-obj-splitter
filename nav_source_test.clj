(ns nav-source-test
  (:use nav-source clojure.test))

(def expected-metadata {:type-id 1 :type "Table" :id "70902" :name "Test Table (48)"})
(def first-line-example "OBJECT Table 70902 Test Table (48)")

(deftest first-line-of-object-source?-should-return-true
  (is (true? (first-line-of-object-source? first-line-example))))

(deftest parsing-first-line-returns-map-of-named-elements
  (is (= expected-metadata (parse-first-line first-line-example))))

(deftest make-single-file-name-returns-uniquely-identifiable-filename
  (is (= "1-Table-70902.txt" (make-single-file-name (parse-first-line first-line-example)))))

(run-tests 'nav-source-test)