(ns invoker.service-test
  (:require [clojure.test :refer :all]))

(deftest uncategorized-test
  (is (= 0 0)))

(deftest ^:integration integration-test
  (is (= 0 0)))

(deftest ^:unit unit-test
  (is (= 0 0)))
