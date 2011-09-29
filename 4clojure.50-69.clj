;; # 50 Split by Type
;; Write a function which takes a sequence consisting of items with different
;; types and splits them up into a set of homogeneous sub-sequences.
;;
;; The internal order of each sub-sequence should be maintained, but the
;; sub-sequences themselves can be returned in any order (this is why 'set'
;; is used in the test cases).
	
;; I can separate all items by type
(partition-by class [1 :a 2 :b])     ; => ((1) (:a) (2) (:b)) 

;; or group them by class
(group-by class [1 :a 2 :b])  ; =>{java.lang.Integer [1 2], clojure.lang.Keyword [:a :b]}

;; getting only the values
(vals (group-by class [1 :a 2 :b]))

;; solution
(def __ #(vals (group-by class %)))

(= (set (__ [1 :a 2 :b 3 :c]))
   #{[1 2 3] [:a :b :c]})
	
(= (set (__ [:a "foo"  "bar" :b]))
   #{[:a :b] ["foo" "bar"]})
	
(= (set (__ [[1 2] :a [3 4] 5 6 :b]))
   #{[[1 2] [3 4]] [:a :b] [5 6]})

;; # 51 Advanced Destructuring
;; Here is an example of some more sophisticated destructuring.
	
;; a takes the value of the first item
;; b, the second
;; c, the rest
;; d, all of them
(def __ [1 2 3 4 5])

(= [1 2 [3 4 5] [1 2 3 4 5]]
   (let [[a b & c :as d] __] [a b c d]))

;; # 52 Intro to Destructuring
;; Let bindings and function parameter lists support destructuring.

;; ans: [c e]

(= [2 4]
   (let [[a b c d e f g] (range)] __))

;; # 53 Longest Increasing Sub-Seq
;; Given a vector of integers, find the longest consecutive sub-sequence
;; of increasing numbers. If two sub-sequences have the same length,
;; use the one that occurs first.
;;
;; An increasing sub-sequence must have a length of 2 or greater to qualify.
;;
;;           input            output
;;      -----------------    ---------
;;      [1 0 1 2 3 0 4 5] => [0 1 2 3]
;;	
;; partition is a good candidate for this problem. First, we pair each
;; item with the next

(let [col [1 0 1 2 3 0 4 5]]
  (partition 2 1 col))         ; ((1 0) (0 1) (1 2) (2 3) (3 0) (0 4) (4 5))

;; then we create more groups, in this case all of those whose first
;; value is greater than its second. We create a predicate function that will
;; evaluate each pair `s>f?`

(let [col [1 0 1 2 3 0 4 5]
      s>f? #(> (second %) (first %))]
  (partition-by s>f? (partition 2 1 col)))


;; the resulting sequence looks fine,
;;
;;     ( ((1 0))
;;       ((0 1) (1 2) (2 3))
;;       ((3 0))
;;       ((0 4) (4 5)) )
;;
;; but we need to remove those pairs where the second is less than the first item, 
;; this is done by evaluating each sequences first pair

(let [col [1 0 1 2 3 0 4 5]
      s>f? #(> (second %) (first %))]
      (filter #(s>f? (first %))
              (partition-by s>f? (partition 2 1 col))))

;; the filtered sequence:
;;
;; ( ((0 1) (1 2) (2 3))
;;   ((0 4) (4 5)))

;; but sometimes the filtered sequence might be empty, for example
;; if we are evaluating `col` with a value of `[7 6 5 4]` we get an
;; empty sequence, which will can potentially throw a null pointer
;; exception later
;;
;; To fix it, we just add a condition

(let [col   [ 7 6 5 4 ];;[1 0 1 2 3 0 4 5]
      s>f?  #(> (second %) (first %))
      s     (filter #(s>f? (first %))
                            (partition-by s>f? (partition 2 1 col)))]
  (if (empty? s) [] (reverse (sort-by count s))))

;; when sorting the sequences by the number of pairs each has we can
;; retrieve the largest one. The above code produces:
;;
;;      (((0 1) (1 2) (2 3))
;;       ((0 4) (4 5)))
;;
;; But in some cases we will have consecutive sequences of the same length
;; for example the vector `[5 6 1 3 2 7]` would evaluate to:
;;
;;      {1 [ ((5 6))
;;           ((6 1))
;;           ((1 3))
;;           ((3 2))
;;           ((2 7))]}
;;
;; We are asked for the first largest one we find, so grouping the sequence
;; by count makes much more sense

(let [col   [1 0 1 2 3 0 4 5]
      s>f?  #(> (second %) (first %))
      s     (filter #(s>f? (first %))
                            (partition-by s>f? (partition 2 1 col)))]
  (if (empty? s) [] (group-by count s)))


;; `group-by` will return a map whose key is the value of the function
;; applied to the sequence
;;
;;      {3 [( (0 1) (1 2) (2 3) )],
;;       2 [( (0 4) (4 5) )]}
;;

;; now we sort the map and retrieve the first vector containing the
;; largest sequences. We are only interested in the first one

(let [col   [1 0 1 2 3 0 4 5]
      s>f?  #(> (second %) (first %))
      s     (filter #(s>f? (first %))
                            (partition-by s>f? (partition 2 1 col)))]
  (if (empty? s)
    []
    (first (val (last (sort (group-by count s)))))))


;; the previous evaluates to
;;
;;       ((0 1) (1 2) (2 3))

;; The code looks OK, but notice we have a function composition 
;;
;;      f( g( h( x ) ) )
;;
;; Clojure's `->` reader macro can increase the expressiveness of the
;; code, as it does allows us to rewrite it to
;;
;;      (-> x h g f)
;;
;; So this
;;
;;      (first (val (last (sort (group-by count s)))))
;;
;; becomes this
;;
;;      (-> (group-by count s) sort last val first)))


;; going back to the largest sequence, the first pair is part of the solution
;; along with the last item from the rest of pairs, we can extract the
;; answer like this

(let [s '((0 1) (1 2) (2 3))]
  (concat [(ffirst s)] (map second s)))         ; (0 1 2 3)

(def __ (fn [col]
          (let [s>f?  #(> (second %) (first %))
                s     (filter #(s>f? (first %))
                              (partition-by s>f? (partition 2 1 col)))]
            (if (empty? s) [] (let [ls (-> (group-by count s) sort last val first)]
                                (concat [(ffirst ls)] (map second ls)))))))

(= (__ [1 0 1 2 3 0 4 5])
   [0 1 2 3])
	
(= (__ [5 6 1 3 2 7])
   [5 6])
	
(= (__ [2 3 3 4 5])
   [3 4 5])
	
(= (__ [7 6 5 4])
   [])


;; # 57. Simple Recursion	
;; A recursive function is a function which calls itself.
;; > This is one of the fundamental techniques used in functional programming.
	
(def __ '(5 4 3 2 1))
(= __ ((fn foo [x]
         (when (> x 0)
           (conj (foo (dec x)) x)))
       5))

;; In this exercise `foo` takes a number `x` when `x` is greater than 0,
;; then a recursive call is made to the next decrement in `x`, the
;; result of this new call will be combined with the value of `x` in a
;; new list.
;;
;; This is the behavior of the procedure:
;;
;;      (foo 5)             ; 5 is greater than 0
;;      (conj (foo 4) 5)
;;      (conj (conj (foo 3) 4) 5)
;;      (conj (conj (conj (foo 2) 3) 4) 5)
;;      (conj (conj (conj (conj (foo 1) 2) 3) 4) 5)
;;      (conj (conj (conj (conj (conj (foo 0) 1) 2) 3) 4) 5)
;;
;; when we get to `(foo 0)` the procedure returns `nil`
;;
;;      (conj (conj (conj (conj (conj nil 1) 2) 3) 4) 5)
;;      (conj (conj (conj (conj '(1) 2) 3) 4) 5)
;;      (conj (conj (conj '(2 1) 3) 4) 5)
;;      (conj (conj '(3 2 1) 4) 5)
;;      (conj '(4 3 2 1) 5)
;;      '(5 4 3 2 1)

;; # 64. Intro to Reduce	
;; `reduce` takes a 2 argument function and an optional starting value.
;;
;; * It applies the function to the first 2 items in the sequence (or the
;; starting value and the first element of the sequence).
;;
;; * In the next iteration the function will be called on the previous
;; return value and the next item from the sequence, thus reducing the
;; entire collection to one value.
	
(def __ +)

(= 15 (reduce __ [1 2 3 4 5]))
(=  0 (reduce __ []))
(=  6 (reduce __ 1 [2 3]))

;; # 68. Recurring Theme	
;; Clojure only has one non-stack-consuming looping construct: `recur`.
;;
;; Either a function or a loop can be used as the recursion point. Either way,
;; recur rebinds the bindings of the recursion point to the values it is passed.
;;
;; Recur must be called from the tail-position, and calling it elsewhere will
;; result in an error.
	
(def __ [7 6 5 4 3])
(= __
  (loop [x 5
         result []]
    (if (> x 0)
      (recur (dec x) (conj result (+ 2 x)))
      result)))

;; Behavior of linear iteration
;;
;;      (foo 5 [])
;;      (foo 4 [7])
;;      (foo 3 [7 6])
;;      (foo 2 [7 6 5])
;;      (foo 1 [7 6 5 4])
;;      (foo 0 [7 6 5 4 3])
;;      [7 6 5 4 3]
