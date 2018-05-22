(ns invoker.service
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.log :as log]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [invoker.invoke :as invoke]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [(body-params/body-params
                      (body-params/default-parser-map
                       :json-options {:key-fn ->kebab-case-keyword}))
                     http/html-body]
     ["/about" {:get about-page}]
     ["/api/v0.1"
      ["/invoke"
       {:post [:run-commands invoke/run-commands]}]]]]])

(defrecord Service [port service-map]
  component/Lifecycle
  (start [this]
    (log/info :at ::start)
    (if service-map
      this
      (let [service-map {:env :dev
                         ::http/allowed-origins {:creds true
                                                 :allowed-origins (constantly true)}
                         ::http/routes routes
                         ::http/resource-path "/public"
                         ::http/type :jetty
                         ::http/host "0.0.0.0"
                         ::http/join? false
                         ::http/port port
                         ::http/container-options {:h2c? true
                                                   :h2? false
                                                   :ssl? false}}]
        (assoc this :service-map service-map))))
  (stop [this]
    (log/info :at ::stop)
    (dissoc this :service-map)))

(defn new-service
  [service-cfg]
  (map->Service service-cfg))
