(ns rymkevich-fp-1.core-test
  (:require [clojure.test :refer :all]
            [rymkevich-fp-1.core :refer :all]))

(deftest workSqr
  (testing "sqr function doesn't work"
    (is (= (rymkevich-fp-1.core/sqr 5) 23))))

;(workSqr)

(deftest workSqrCorrect
  (testing "sqr function  work right"
    (is (= (rymkevich-fp-1.core/sqr 5) 25))))
( workSqrCorrect )



(deftest euclidian-distance-test
  (testing "euclidian function doesn't work"
(is (= 0 (rymkevich-fp-1.core/euclidian-distance [2,2] [2,2])))
(is (= 50 (rymkevich-fp-1.core/euclidian-distance [0,0] [5,5])))
(is (= 74 (rymkevich-fp-1.core/euclidian-distance [0,0,0] [5,5,5])))
))

(deftest hamming-distance-test
  (testing "hamming function doesn't work"
(is (= 0 (rymkevich-fp-1.core/hamming-distance [2,2] [2,2])))
(is (= 2 (rymkevich-fp-1.core/hamming-distance [0,0] [1,1])))
(is (= 3 (rymkevich-fp-1.core/hamming-distance [0,0,0] [1,1,1])))
))

(euclidian-distance-test)
(hamming-distance-test)
