(ns clj-nav-obj-splitter (:use clojure.test))

(ns clj-nav-obj-splitter (:use clojure.test System.Text.RegularExpressions))


(clojure.test/is (= 4 (+ 2 2)))
(clojure.test/is (= 5 (+ 2 2)))


(re-find #"quick" "The quick brown fox jumps over the lazy dog")



(re-pattern "^.+ \\d+ .+")


(re-find (re-pattern #"^.+ \d+ .+") "Table 100 Test Table")

(re-find (re-pattern "^Table|Form|Report|Dataport|XMLport|Page|Codeunit") "Table 100 Test Table")

(re-find (re-pattern #"\d+") "Table 100 Test Table")

(re-find (re-pattern #"\d+") "Table 100 Test Table")

(def matcher (re-matcher #"^(\w+)" "Table 100 Test Table"))
(re-groups matcher)