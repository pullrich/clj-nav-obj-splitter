(ns nav.text.split (:use clojure.test clojure.string))

;;(clojure.test/is (= 4 (+ 2 2)))
;;(lower-case "TEST")

(re-find (re-pattern #"^(.+) (\d+) (.+)") "Table 100 Test Table")

(re-find (re-pattern "^Table|Form|Report|Dataport|XMLport|Page|Codeunit") "Table 100 Test Table")

(defn type-id [type]
  (cond
    (= type "Table") 1
    (= type "Form") 2
    (= type "Report") 3
    (= type "Dataport") 4
    (= type "Codeunit") 5
    (= type "XMLport") 6
    (= type "MenuSuite") 7
    (= type "Page") 8))

(defn parse-first-line [line]
  (let [first-line-format #"^OBJECT (.+) (\d+) (.+)"
        matches (re-find (re-pattern first-line-format) line)
        type (nth matches 1)
        id (nth matches 2)
        name (nth matches 3)]
    {:type-id (type-id type) :type type :id id :name name}))

(defn make-single-file-name [object-metadata]
  (let [type-id (:type-id object-metadata)
        type (:type object-metadata)
        id (:id object-metadata)
        file-name (join "-" [type-id, type, id])]
    (join [file-name ".txt"])))


(parse-first-line "Table 100 Test Table")
(def objmeta (parse-first-line "Table 100 Test Table"))
(make-single-file-name objmeta)
(parse-first-line "OBJECT Table 70902 Test Table (48)")
