(ns invoker.test.util
  (:require [com.stuartsierra.component :as component]
            [invoker.system :as system]))

(defn with-test-system
  [f]
  (let [s (component/start-system (system/test-system))]
    (try
      (f s)
      (finally
        (component/stop-system s)))))

(defn service
  [system]
  (get-in system [:server :running-server :io.pedestal.http/service-fn]))
