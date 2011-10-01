;; # 64 Intro to Reduce	
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

;; # 68 Recurring Theme	
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
