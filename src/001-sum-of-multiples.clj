;; ##001
;; If we list all the natural numbers below 10 that are multiples of 3 or 5,
;; we get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.

;; #Solution A
;;
;; Brute Force Method
(defn sola [a b limit]
  " Filter will apply an anonymous function - that acts as a
  predicate - to the range. It'll produce a lazy sequence of
  all multiples of a and b.

  Reduce will apply sum to all the items in the filtered sequence
  and return the total sum"
  (reduce + (filter
              (fn [x]
                (or (zero? (mod x a))
                    (zero? (mod x b))))
                (range limit))))

;; #Solution B
;; Just rewriting A
(defn solb [a b limit]
  " Using a named function as predicate"

  (defn pred? [x]
    (or (zero? (mod x a))
        (zero? (mod x b))))

  (reduce + (filter pred? (range limit))))

;; #Solution C
;; Shortcut for anonymous function
;;
;; Instead of writing `fn[args](...)` we can use the macro `#(...)`
;; where args are represented in the form `%` or `%n`
(defn solc [a b limit]
  (reduce +
    (filter
      #(or
         (zero? (mod % a))
         (zero? (mod % b)))
       (range limit))))

;; #Solution D
;; Previous solutions require iterations. A much more efficient solution
;; is achieved with arithmetic series

;; In this problem there are three sequences. One of multiples of 3 and
;; the others of multiples of 5 and 15 respectively. The sum of the last
;; sequence is subtracted from the first two to avoid the adding a
;; multiple of 3 and 5 twice.
(defn solution [a b limit]
  (defn sum [d]
    "d is both the first element and the multiplier and limit is decreased by 1
    since the problem states sum must be bellow it"
    (def lasti  (int (* d (Math/floor (/ (- limit 1) d)))))
    (def length (int (/ (+ d (- lasti d)) d)))
    (* (/ length 2) (+ d lasti)))
  (+ (sum a) (sum b)
     (- (sum (* a b)))))

;; #Running
(println (str "sola: " (sola 3 5 1000)))         ; => 233168
(println (str "solb: " (solb 3 5 1000)))         ; => 233168
(println (str "solc: " (solc 3 5 1000)))         ; => 233168
(println (str "solution " (solution 3 5 1000)))  ; => 233168


;; #Performance
(println "\nEvaluating numbers until 10,000")
(println "solc")
(dotimes [_ 5] (time (solc 3 5 10000)))

(println "solution")
(dotimes [_ 5] (time (solution 3 5 10000)))

;; #Some notes
;;
;; In an arithmetic progression I can find the `nth` of a sequence of
;; natural numbers with this:
;;
;; `nth = first_item + multiplier * (total_number_of_items - 1 )`
;;
;; From the problem description I know that the first item is `3`
;; and the multiplier is `3`, also that the nth item is close to 1000,
;; but it's not it, since it must be multiple of 3
;;
;; To find the last item I need this

(defn previous-multiple [number multiplier]
  " This will look for the nearest previous multiple of a number"
  (int (* multiplier (Math/floor (/ number multiplier)))))

;; Now I know that the sequence ends at 999
;;
;; `3 6 9 ... 999`
;;
;; The only data I don't know is the total number of items in the
;; sequence. This number is important to be able to sum all items in the
;; sequence (arithemtic series).
;;
;; To find it, I just need to solve for total_number_of_items in the
;; previous equation, which I'm rewriting for clarity
;;
;; `last = first + multiplier * ( length - 1 )`
;;
;; Solving by `length`
;;
;; `length = (last - first + multiplier) / multiplier`
;;
;; `length = (999 - 3 + 3) / 3`
;;
;; `length = 333`
;;
;; In clojure this can be expressed as

(defn length [first multiplier last]
  " Solves length = (last - first + multiplier) / multiplier "
  (int (/
         (+ first (- last multiplier))
         multiplier)))

;; Now I can sum the values of the sequence `3 6 ... 999`
;;
;; `Sum = length * (first + last)`
;;
;; `Sum = 333/2 * (3 + 999)`
;;
;; `Sum = 166863`
;;
;; The function is expressed like this
(defn arithmetic-series [length first last]
  (* (/ length 2) (+ first last)))

;; Performing the sum
(let [x 3, lastn (previous-multiple 1000 x)]
  (arithmetic-series
    (length x x lastn) x lastn))      ; => 166863
