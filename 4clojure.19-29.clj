;; # 19. Last Element
;; Write a function which returns the last element in a sequence.
;;
;; restrictions: `last`

;; `peek` returns the first item in a list or queue, but for a vector it
;; returns the last.
(def __ #(-> % vec peek))

(= (__ [1 2 3 4 5])
   5)

(= (__ '(5 4 3))
   3)

(= (__ ["b" "c" "d"])
   "d")


;; # 20. Penultimate Element
;; Write a function which returns the second to last element from a sequence.
(def __ #(-> % reverse second))

(= (__ (list 1 2 3 4 5))
   4)

(= (__ ["a" "b" "c"])
   "b")

(= (__ [[1 2] [3 4]])
   [1 2])

;; # 21. Nth
;; Write a function which returns the Nth element from a sequence.
;;
;; restrictions: `nth`
	
;; (def __ #(last (take (inc %2) %1)))
(def __ #(get (vec %1) %2))

(= (__ '(4 5 6 7) 2)
   6)

(= (__ [:a :b :c] 0)
   :a)

(= (__ [1 2 3 4] 1)
   2)

(= (__ '([1 2] [3 4] [5 6]) 2)
   [5 6])

;; # 22. Count a Sequence	
;; Write a function which returns the total number of elements in a sequence.
;;
;; restrictions: `count`

;; golf score: 73
(def __
  #(let [temp-seq (seq %)]
     (loop [i 0]
       (if (nil? (nth temp-seq i nil))
         i
         (recur (inc i))))))

;; golf score: 51
(def __
  #(loop [i 0
          c (vec %)]
     (if (empty? c)
       i
       (recur (inc i) (pop c)))))

;; golf score: 25
;;
;; `a` is the accumulator and starts with a 0, every time the function
;; is called the accumulator increases by 1
(def __ #(reduce (fn [a x](inc a)) 0 %))

(= (__ '(1 2 3 3 1))
   5)
	
(= (__ "Hello World")
   11)
	
(= (__ [[1 2] [3 4] [5 6]])
   3)
	
(= (__ '(13))
   1)
	
(= (__ '(:a :b :c))
   3)

;; # 23. Reverse a Sequence	
;; Write a function which reverses a sequence.
;;
;; restrictions: `reverse`, `rseq`

;; golf score: 122
(def __ #(let [temp-seq (seq %)
                length (count temp-seq)]
            (loop [i 0
                   result '()]
              (if (>= i length)
                result
                (recur (inc i) (conj result (nth temp-seq i)))))))

;; golf score: 29
;;
;; the initial value for conj must be a list since items are added at
;; the first position when using `conj` with lists
(def __ #(reduce (fn [a x] (conj a x)) '() %))

;; golf score: 17
(def __ #(reduce conj '() %))

(= (__ [1 2 3 4 5])
   [5 4 3 2 1])
	
(= (__ (sorted-set 5 7 2 7))
   '(7 5 2))
	
(= (__ [[1 2][3 4][5 6]])
   [[5 6][3 4][1 2]])

;; # 24. Sum It All Up	
;; Write a function which returns the sum of a sequence of numbers.
	
;; using `reduce`
(= (reduce + [1 2 3])
   6)
	
;; `apply` does... apply a function to a variable number of arguments
(= (apply + (list 0 -2 5 5))
   8)

;; another way...

(defn sum [col]
  " Will sum the contents of a collection"
  (let [temp-seq (seq col)        ; make the collection into a sequence
        length (count temp-seq)]
    (loop [i 0
           total 0 ]
      (if (= i length)
          total
          (recur (inc i) (+ total (nth temp-seq i)))))))

(= (sum #{4 2 1})
   7)
	
(= (sum '(0 0 -1))
   -1)
	
(= (sum '(1 10 3))
   14)

;; # 25. Find the odd numbers	
;; Write a function which returns only the odd numbers from a sequence.

;; using partial and an anonymous function literal. (nested #() are not
;; allowed)
(def __ (partial remove #(zero? (mod % 2))))
(= (__ #{1 2 3 4 5})
   '(1 3 5))
	
;; using filter with odd?
(def __ #(filter odd? %))
(= ( __ [4 2 1 6])
   '(1))
	
;; using remove with even
(def __ #(remove even? %))
(= (__ [2 2 4 6])
   '())
	
(= (__ [1 1 1 3])
   '(1 1 1 3))

;; # 26. Fibonacci Sequence

;; Write a function which returns the first X Fibonacci numbers.

;; Golf Score 65
(def __
  #(loop [i 0 a [1] x 1]
     (if (>= i (dec %))
       a
       (recur (inc i) (conj a x) (+ x (last a))))))

(= (__ 3) '(1 1 2))
	
(= (__ 6) '(1 1 2 3 5 8))
	
(= (__ 8) '(1 1 2 3 5 8 13 21))

;; # 27. Palindrome Detector
;; Write a function which returns true if the given sequence is a palindrome.
;;
;; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)

;; palindrome are sequences that can be read the same way in either
;; direction
(def __ #(= % (reverse %)))

;; golf score: 50
(def __
  (fn [c]
    (= c
       (#(if (string? c) (apply str %) %)
       (reverse c)))))

;; golf score: 29
(def __
  #(let [c (vec %)]
     (= c (reverse c))))

;; golf score: 20
(def __
  #(= (vec %) (reverse %)))

(false? (__ '(1 2 3 4 5)))
	
(true? (__ "racecar"))
	
(true? (__ [:foo :bar :foo]))
	
(true? (__ '(1 1 3 3 1 1)))
	
(false? (__ '(:a :b :c)))

;; # 28. Write a function which flattens a sequence.
;; restrictions: `flatten`	

;; Golf Score: 77
(def __
  (fn [col]
    (let [pred? #(or (vector? %) (seq? %))]
      (remove pred? (tree-seq pred? seq col)))))

;; `seq?` and `sequential?` are not the similar but not the same. `seq?`
;; returns true for sequence types only, while `sequential?` includes
;; collections such as vectors.

;; Golf Score: 45
(def __ #(remove sequential? (tree-seq sequential? seq %)))

(__ '((1 2) 3 [4 [5 6]]))

(= (__ '((1 2) 3 [4 [5 6]]))
   '(1 2 3 4 5 6))
	
(= (__ ["a" ["b"] "c"])
   '("a" "b" "c"))
	
(= (__ '((((:a)))))
   '(:a))

;; # 29 Get the Caps
;; Write a function which takes a string and returns a new string
;; containing only the capital letters.

(def __ #(apply str (re-seq #"[A-Z]" %)))

(= (__ "HeLlO, WoRlD!")
   "HLOWRD")
	
(empty? (__ "nothing"))
	
(= (__ "$#A(*&987Zf")
   "AZ")
