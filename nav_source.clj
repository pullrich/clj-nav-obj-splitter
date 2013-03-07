(ns nav-source
  (:require [clojure.string :as string]))

(def nav-source-file-encoding "ibm850")
(def type-id-map {"Table" 1 "Form" 2 "Report" 3 "Dataport" 4 "Codeunit" 5 "XMLport" 6 "MenuSuite" 7 "Page" 8})
(def first-line-regex #"^OBJECT ([a-zA-Z]+) (\d+) (.+)")
(def last-line-regex #"^}$")
(def source-file-extension ".txt")
(def object-id-min 1)
(def object-id-max 20000000000) ; TODO: Check NAV max. Integer value!

;; Read big source file line by line.
;; Check if line is first line of an NAV object by testing against a regex.
;; If so, than place that line and following lines on a "current object" stack.
;; Check if the current line read is the "closing" line for the current object.
;; If so , than place that line on the "stack" and send of the source for this
;; one object to be parsed and saved in its own file.

(defn get-big-source-lines
  "doc"
  [file-name]
  (let [content (slurp file-name :encoding nav-source-file-encoding)
        lines (string/split-lines content)]
    lines))

(defn matches-first-line-structure?
  "Check if line has the structure of a first line of a NAV object by testing against a regex."
  [line]
  (if (re-find first-line-regex line)
    true
    false))

(defn valid-object-type-string?
  "Returns true if string is of a known object type."
  [object-string]
  (contains? type-id-map object-string))

(defn object-id-within-valid-range?
  "Returns true if object-id is within the allowed range."
  [object-id]
  (and
    (>= object-id object-id-min)
    (<= object-id object-id-max)))

(defn valid-first-line-tokens?
  "Returns true if the tokens match their constraints."
  [tokens]
  ; TODO: incomplete!
  (and 
    (valid-object-type-string? (tokens :type))
    (object-id-within-valid-range? (tokens :id))))

(defn matches-last-line-structure?
  "Check if line is the closing line for the a NAV object by testing against a regex."
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
        (if (matches-first-line-structure? current)
          (def current-object-source current)
          (def current-object-source (string/join "\n" [current-object-source current])))
        (recur (first buffer) (rest buffer)))
      nil)))

    
(defn first-line-tokens
  "Returns a map of tokens parsed from the first line of the object source file."
  [line]
  (let [matches (re-find (re-pattern first-line-regex) line)
        type (nth matches 1)
        id (nth matches 2)
        name (nth matches 3)]
    {:type type :id (read-string id) :name name}))

(defn make-single-file-name
  "Returns a file name built from object meta data."
  [object-metadata]
  (let [object-metadata (assoc object-metadata :type-id (type-id-map (object-metadata :type)))
        file-name (string/join "-" (map object-metadata [:type-id :type :id]))]
    (str file-name source-file-extension)))
    
(defn save-single-source-file
  "Saves the source code of one object in its own file."
  [source file-name]
  (spit file-name source))
