;; # 1-28
;; Write a function to perform integer division without using
;; either the / or * operators. Find a fast way to do it.
(defn integer-division [dividend divisor]
  (loop [counter 0 sum 0]
    (if (>= sum dividend)
      counter
      (recur (inc counter)
             (+ sum divisor)))))

(integer-division 21 3)
