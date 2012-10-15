(ns clj-nav-obj-splitter (:use clojure.test))

;;(clojure.test/is (= 4 (+ 2 2)))

(re-find (re-pattern #"^(.+) (\d+) (.+)") "Table 100 Test Table")

(re-find (re-pattern "^Table|Form|Report|Dataport|XMLport|Page|Codeunit") "Table 100 Test Table")

(defn parse-first-line [line]
	(let [matches (re-find (re-pattern #"^(.+) (\d+) (.+)") line)
		  type (nth matches 1)
		  id (nth matches 2)
		  name (nth matches 3)]
		{:type type :id id :name name}))

(parse-first-line "Table 100 Test Table")