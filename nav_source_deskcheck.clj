(ns nav-source-deskcheck
  (:use nav-source))

(doseq [line (get-big-source-lines "2_Tables.txt")]
  (println line))
