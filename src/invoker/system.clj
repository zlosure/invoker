(ns invoker.system
  (:require [aero.core :as aero]
            [com.stuartsierra.component :as component]
            [invoker.server :as server]
            [invoker.service :as service]))

(defn new-system
  [profile]
  (let [cfg (aero/read-config (clojure.java.io/resource "config.edn")
                              {:profile profile})
        dependency-map {:server [:service]}
        system (component/system-map
                :service (service/new-service (:service cfg))
                :server (server/new-server))]
    (component/system-using system dependency-map)))

(defn dev-system
  []
  (new-system :dev))

(defn test-system
  []
  (new-system :test))
