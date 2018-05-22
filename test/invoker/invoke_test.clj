(ns invoker.invoke-test
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [invoker.system :as system]
            [invoker.test.util :as util]
            [io.pedestal.test :as p]))

(deftest ^:integration invoke-test
  (util/with-test-system
    (fn [system]
      (let [response
            (p/response-for (util/service system)
                            :post "/api/v0.1/invoke"
                            :body (json/generate-string {:action "install"
                                                         :target "console"
                                                         :namespace "szhou"})
                            :headers {"Accept" "application/json"
                                      "Content-Type" "application/json"})]
        (testing "Valid request should return 200 status"
          (is (= 200 (:status response))))))))
