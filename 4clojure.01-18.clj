;; # 1. Nothing but the Truth
;; a clojure form
(def __ true)
(= __ true)

;; # 2. Simple Math	
;; prefix notation (aka polish notation)
(def __ 4)
(= (- 10 (* 2 3)) __)

;; # 3. Intro to Strings	
(def __ "HELLO WORLD")
(= __ (.toUpperCase "hello world"))

;; # 4. Intro to Lists	
;; List can be expressed in two ways with the `list` function or the `'`
;; quote special form.
(def __ (list :a :b :c))
(= __ '(:a :b :c))

;; # 5. Lists: conj	
;; `conj` takes a list and returns a "new list" with elements added
;; at the beginning. Clojure doesn't make a copy but a reference
;; to the new list containing both the new items and the old lists' items.
;; Imagine appending items to a binary tree and storing a reference to
;; the new current state of the tree, while keeping the state of the
;; old tree.
(def __ '(1 2 3 4))
(= __ (conj '(2 3 4) 1))
(= __ (conj '(3 4) 2 1))

;; # 6. Intro to Vectors	
;; Vectors can be expressed in many ways. They can also be compared to
;; lists.
(def __ [:a :b :c])
(= __ (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;; check type
(class (vec (list :a :b :c)))
(class (list :a :b :c))

;; # 7. Vectors: conj	
;; in a vector `conj` returns a new vector with items appended at the
;; end.
(def __ [1 2 3 4])
(= __ (conj [1 2 3] 4))
(= __ (conj [1 2] 3 4))

;; # 8. Intro to Sets	
;; sets are collections of unique values
(def __ #{:a :b :c :d})
(= __ (set '(:a :a :b :c :c :c :c :d :d)))
(= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))

;; # 9. Sets: conj	
;; in sets `conj` adds the new item only if it's not already in the list
(def __ 2)
(= #{1 2 3 4} (conj #{1 4 3} __))

;; # 10. Intro to Maps	
;; Maps store key-value pairs. Keys and values can be used as functions
;; to retrieve data. Commas are treated it as white space in Clojure.
(def __ 20)
(= __ ((hash-map :a 10, :b 20, :c 30) :b))
(= __ (:b {:a 10, :b 20, :c 30}))

;; # 11. Maps: conj	
(def __ {:b 2})
(= {:a 1, :b 2, :c 3} (conj {:a 1} __ [:c 3]))

;; # 12. Intro to Sequences	
;; Collections support sequencing
(def __ 3)
(= __ (first '(3 2 1)))
(= __ (second [2 3 4]))
(= __ (last (list 1 2 3)))

;; # 13. Sequences: rest	
(def __ [20 30 40])
(= __ (rest [10 20 30 40]))

;; # 14. Intro to Functions	
;; creating functions
(def __ 8)
(= __ ((fn add-five [x] (+ x 5)) 3))

(= __ ((fn [x] (+ x 5)) 3))            ; anonymous function

;; The macro character `#` followed by parenthesis `()` is a reader macro and represents
;; an *anonymous function literal*
(= __ (#(+ % 5) 3))

(= __ ((partial + 5) 3))

;; # 15. Double Down	
;; Write a function that doubles a number
(def __ (fn double [x] (* x 2)))
(= (__ 2) 4)

(def __ (fn [x] (* x 2)))
(= (__ 3) 6)

(def __ #(* 2 %))
(= (__ 11) 22)

(def __ (partial * 2))
(= (__ 7) 14)

;; # 16. Hello World	
;; Write a function which returns a personalized greeting.
(def __ (fn [name] (str "Hello, " name "!")))
(= (__ "Dave")
   "Hello, Dave!")

(def __ #(str "Hello, " % "!"))
(= (__ "Jenn")
   "Hello, Jenn!")

(= (__ "Rhea")
   "Hello, Rhea!")

;; # 17. Sequences: map	
;; The `map` function takes two arguments: a function and a sequence, it
;; returns a new sequence with the result of applying the function to each
;; item of the sequence.
(def __ '(6 7 8))
(= __ (map #(+ % 5) '(1 2 3)))

;; # 18. Sequences: filter	
;; `filter` applies a predicate function to each item in a sequence and
;; returns a new sequence with the items that evaluated to true.
(def __ '(6 7))
(= __ (filter #(> % 5) '(3 4 5 6 7)))
