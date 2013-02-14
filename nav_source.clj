(ns nav-source
  (:require [clojure.string :as string]))

(def type-id-map {"Table" 1 "Form" 2 "Report" 3 "Dataport" 4 "Codeunit" 5 "XMLport" 6 "MenuSuite" 7 "Page" 8})
(def first-line-regex #"^OBJECT (.+) (\d+) (.+)")
(def last-line-regex #"^}$")
(def source-file-extension ".txt")

;; Read big source file line by line.
;; Check if line is first line of an NAV object by testing against a regex.
;; If so, than place that line and following lines on a "current object" stack.
;; Check if the current line read is the "closing" line for the current object.
;; If so , than place that line on the "stack" and send of the source for this
;; one object to be parsed and saved in its own file.

(defn get-big-source-lines
  "doc"
  [file-name]
  (let [lines (string/split-lines (slurp file-name :encoding "ibm850"))]
    lines))

(defn first-line-of-object-source?
  "Check if line is first line of an NAV object by testing against a regex."
  [line]
  (if (re-find first-line-regex line)
    true
    false))

(defn last-line-of-object-source?
  "Check if line is the \"closing\" line for the a NAV object by testing against a regex."
  [line]
  (if (re-find last-line-regex line)
    true
    false))

(def current-object-source "")
    
(defn loop-and-print [lines]
  (loop [current (first lines)
         buffer (rest lines)]
    (if current
      (do
        (println current)
        (if (first-line-of-object-source? current)
          (def current-object-source current)
          (def current-object-source (string/join "\n" [current-object-source current])))
        (recur (first buffer) (rest buffer)))
      nil)))

    
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
  (let [file-name (string/join "-" (map object-metadata [:type-id :type :id]))]
    (str file-name source-file-extension)))
    
(defn save-single-source-file
  "Saves the source code of one object in its own file."
  [source file-name]
  (spit file-name source))
