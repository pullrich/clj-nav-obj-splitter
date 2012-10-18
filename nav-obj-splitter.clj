(ns nav.source.split
  (:use [clojure.test]
        [clojure.string :only [join]]))

(defn type-id
  "Returns the NAV specific type id for the object type."
  [type]
  (cond
    (= type "Table") 1
    (= type "Form") 2
    (= type "Report") 3
    (= type "Dataport") 4
    (= type "Codeunit") 5
    (= type "XMLport") 6
    (= type "MenuSuite") 7
    (= type "Page") 8))

(defn parse-first-line
  "Parses meta data from the first line of the object source file."
  [line]
  (let [first-line-format #"^OBJECT (.+) (\d+) (.+)"
        matches (re-find (re-pattern first-line-format) line)
        type (nth matches 1)
        id (nth matches 2)
        name (nth matches 3)]
    {:type-id (type-id type) :type type :id id :name name}))

(defn make-single-file-name
  "Returns a file name built from object meta data."
  [object-metadata]
  (let [file-name (join "-" (map object-metadata [:type-id :type :id]))]
    (str file-name ".txt")))

; Some tests
(def meta-data-example {:type-id 1 :type "Table" :id "70902" :name "Test Table (48)"})
(def first-line-example "OBJECT Table 70902 Test Table (48)")
(def meta-data-parsed (parse-first-line first-line-example))

(deftest parsing
  (is (= meta-data-example meta-data-parsed)))

(deftest new-file-name
  (is (= "1-Table-70902.txt" (make-single-file-name meta-data-parsed))))

(run-tests 'nav.source.split)
