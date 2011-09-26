;; # 30 Compress a Sequence
;; Write a function which removes consecutive duplicates from a sequence.

;; This uses an anonymous function to keep track of the last item
;; added to a new vector while evaluating the collection with reduce.
(def __
  #(reduce (fn [a x] (if (= x (peek a)) a (conj a x))) [] %))

(= (apply str (__ "Leeeeeerrroyyy"))
   "Leroy")
	
(= (__ [1 1 2 3 3 2 2 3])
   '(1 2 3 2 3))
	
(= (__ [[1 2] [1 2] [3 4] [1 2]])
   '([1 2] [3 4] [1 2]))

;; It's also posible to group similar consecutive items with
;; `partition-by` which splits items until the passed function returns a
;; different value,
;;
;;      (partition-by + [ 1 2 1 1 2 3 4 4 4 5 4])
;;      => ((1) (2) (1 1) (2) (3) (4 4 4) (5) (4))
;;
;; Now to retreive a unique item in a group:
;;
;;      (map last '((1) (2) (1 1) (2) (3) (4 4 4) (5) (4)))
;;      => (1 2 1 2 3 4 5 4)
;;

;; # 31 Pack a Sequence
;; Write a function which packs consecutive duplicates into sub-lists.
	
(def __ #(partition-by str %))

(= (__ [1 1 2 1 1 1 3 3])
   '((1 1) (2) (1 1 1) (3 3)))
	
(= (__ [:a :a :b :b :c])
   '((:a :a) (:b :b) (:c)))
	
(= (__ [[1 2] [1 2] [3 4]])
   '(([1 2] [1 2]) ([3 4])))

;; 32 Duplicate a Sequence
;; Write a function which duplicates each element of a sequence.
	
(def __ (fn [v] (reduce #(conj %1 %2 %2) [] v)))

;; or

(def __ #(interleave % %))

(= (__ [1 2 3])
   '(1 1 2 2 3 3))
	
(= (__ [:a :a :b :b])
   '(:a :a :a :a :b :b :b :b))
	
(= (__ [[1 2] [3 4]])
   '([1 2] [1 2] [3 4] [3 4]))
	
(= (__ [[1 2] [3 4]])
   '([1 2] [1 2] [3 4] [3 4]))

;; # 33 Replicate a Sequence
;; Write a function which replicates each element of a sequence a variable
;; number of times.
	
(def __ #(if (<= %2 1)
           %
           (apply interleave (repeat %2 %))))

;; or
(def __ (fn [c n] (mapcat #(repeat n %) c)))

;; or
(def __ #(mapcat (partial repeat %2) %))

(= (__ [1 2 3] 2)
   '(1 1 2 2 3 3))
	
(= (__ [:a :b] 4)
   '(:a :a :a :a :b :b :b :b))
	
(= (__ [4 5 6] 1)
   '(4 5 6))
	
(= (__ [[1 2] [3 4]] 2)
   '([1 2] [1 2] [3 4] [3 4]))
	
(= (__ [44 33] 2)
   [44 44 33 33])

;; # 34 Implement range
;; Write a function which creates a list of all integers in a given range.
;;
;; restrictions: `range`

(def __
  #(loop [i %
          r []]
     (if (= i %2)
       r
       (recur (inc i)(conj r i)))))

;; or
(def __ (fn [s e]
          (take (- e s)
                (iterate inc s))))

(= (__ 1 4)
   '(1 2 3))
	
(= (__ -2 2)
   '(-2 -1 0 1))
	
(= (__ 5 8)
   '(5 6 7))

;; # 35. Local bindings	
;; the let form creates an immutable local scope
(def __ 7)
(= __ (let [x 5]
       (+ 2 x)))

(= __ (let [x 3, y 10]
       (- y x)))

(= __ (let [x 21]
       (let [y 3]
         (/ x y))))

;; # 36. Let it Be	
;; Can you bind x, y, and z so that these are all true?
;;
;;     (= 10 (let __ (+ x y)))
;;     (= 4 (let __ (+ y z)))
;;     (= 1 (let __ z))

(= (let [x 7 y 3 z 1]
     (conj [] (+ x y) (+ y z) z))
   '(10 4 1))

;; # 37. Regular Expressions
;; Regex patterns are supported with a special reader macro: `#""`
(def __ "ABC")	
(= __ (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

;; # 38 Maximum value
;; Write a function which takes a variable number of parameters and
;; returns the maximum value.

;; `& args` makes sure we can handle multiple arguments. The initial
;; value of 0 for `reduce` is neeeded.
(def __ (fn [& args]
          (reduce #(if (> % %2) % %2) 0 args)))

(= (__ 1 8 3 4)
   8)
	
(= (__ 30 20)
   30)
	
(= (__ 45 67 11)
   67)

;; # 39
;; Interleave Two Seqs
;; Write a function which takes two sequences and returns the first
;; item from each, then the second item from each, then the third, etc.
	
(def __ (fn [c1 c2]
          (mapcat #(conj [] % %2) c1 c2)))

;; using partial to create a function that can handle multiple args
(def __ (partial mapcat #(conj [] % %2) ))

;; or convert all args to lists and use `mapcat` to concatenate them: eg
;;
;;      (mapcat list [1 2]  [3 4 5])
;;      => (1 3 2 4)
;;
(def __ (partial mapcat list))

(= (__ [1 2 3] [:a :b :c])
   '(1 :a 2 :b 3 :c))
	
(= (__ [1 2] [3 4 5 6])
   '(1 3 2 4))
	
(= (__ [1 2 3 4] [5])
   [1 5])
	
(= (__ [30 20] [25 15])'
   [30 25 20 15])

