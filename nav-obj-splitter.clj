(ns nav.source.split
  (:use [clojure.test]
        [clojure.string :only [join]]))

(def type-id-map {"Table" 1 "Form" 2 "Report" 3 "Dataport" 4 "Codeunit" 5 "XMLport" 6 "MenuSuite" 7 "Page" 8})
(def first-line-regex #"^OBJECT (.+) (\d+) (.+)")
(def source-file-extension ".txt")

(defn parse-first-line
  "Parses meta data from the first line of the object source file."
  [line]
  (let [matches (re-find (re-pattern first-line-regex) line)
        type (nth matches 1)
        id (nth matches 2)
        name (nth matches 3)]
    {:type-id (type-id-map type) :type type :id id :name name}))

(defn make-single-file-name
  "Returns a file name built from object meta data."
  [object-metadata]
  (let [file-name (join "-" (map object-metadata [:type-id :type :id]))]
    (str file-name source-file-extension)))

; Some tests
(def meta-data-example {:type-id 1 :type "Table" :id "70902" :name "Test Table (48)"})
(def first-line-example "OBJECT Table 70902 Test Table (48)")
(def meta-data-parsed (parse-first-line first-line-example))

(deftest parsing
  (is (= meta-data-example meta-data-parsed)))

(deftest new-file-name
  (is (= "1-Table-70902.txt" (make-single-file-name meta-data-parsed))))

(run-tests 'nav.source.split)
