(defproject invoker "0.0.1-SNAPSHOT"
  :description "Invoker for the win."
  :url "https://github.com/zlosure/invoker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[aero "1.1.3"]
                 [camel-snake-kebab "0.4.0"]
                 [cheshire "5.8.0"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [com.stuartsierra/component "0.3.2"]
                 [nrepl "0.4.4"]
                 [io.pedestal/pedestal.jetty "0.5.4"]
                 [io.pedestal/pedestal.service "0.5.4"]
                 [org.clojure/clojure "1.9.0"]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :test-selectors {:default (fn [m] (not (or (:unit m) (:integration m))))
                   :unit :unit
                   :integration :integration}
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "invoker.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.4"]
                                  [reloaded.repl "0.2.4"]]
                   ;; TODO: Move plugins to profiles.clj
                   :plugins [[cider/cider-nrepl "0.21.1"]]
                   :repl-options {:init (go)
                                  :init-ns user
                                  :host "0.0.0.0"
                                  :port 55555
                                  :timeout 10000}
                   :source-paths ["dev"]}
             :test {:plugins [[lein-eftest "0.5.4"]]
                    :eftest {:test-warn-time 500}}})
