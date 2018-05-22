(ns invoker.shell
  (:require [clojure.java.shell :as shell]
            [clojure.spec.alpha :as s]
            [io.pedestal.log :as log]))

(defn run
  [command]
  (log/debug :at ::run :command command)
  (shell/sh "bash" "-c" command))

(s/fdef run
        :args (s/and (s/cat :command string?)
                     #(not-empty (:command %)))
        :ret map?)
