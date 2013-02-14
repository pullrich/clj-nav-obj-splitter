(ns nav-source-test
  (:use nav-source)
  (:require [clojure.test :as test]))

(def expected-metadata {:type-id 1 :type "Table" :id "70902" :name "Test Table (48)"})
(def first-line-example "OBJECT Table 70902 Test Table (48)")
(def meta-data-parsed (parse-first-line first-line-example))

(test/deftest parsing-first-line-returns-map-of-named-elements
  (test/is (= expected-metadata meta-data-parsed)))

(test/deftest make-single-file-name-returns-uniquely-identifiable-filename
  (test/is (= "1-Table-70902.txt" (make-single-file-name meta-data-parsed))))

(test/run-tests 'nav-source-test)