(ns invoker.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.log :as log]))

(defrecord Server [service running-server]
  component/Lifecycle
  (start [this]
    (log/info :at ::start)
    (if running-server
      this
      (let [runnable-server (http/create-server (:service-map service))]
        (assoc this :running-server (http/start runnable-server)))))
  (stop [this]
    (log/info :at ::stop)
    (when running-server (http/stop running-server))
    (dissoc this :running-server)))

(defn new-server []
  (map->Server {}))
