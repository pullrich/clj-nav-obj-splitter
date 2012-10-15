(ns clj-nav-obj-splitter (:use clojure.test clojure.string))

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
		(= type "Page") 7))

(defn parse-first-line [line]
	(let [matches (re-find (re-pattern #"^(.+) (\d+) (.+)") line)
		  type (nth matches 1)
		  id (nth matches 2)
		  name (nth matches 3)]
		{:type-id (type-id type) :type type :id id :name name}))

(defn make-single-file-name [{type-id :type-id type :type id :id}]
	"1-Table-100-Test Table")
	
(defn make-single-file-name []
	(+ "1" "test"))
	
(join ["1" "test"])
(join "-" ["1" "test"])
(parse-first-line "Table 100 Test Table")