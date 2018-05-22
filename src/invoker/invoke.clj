(ns invoker.invoke
  (:require [clojure.core.async :as async]
            [clojure.spec.alpha :as s]
            [invoker.http-response :as http-resp]
            [invoker.shell :as sh]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.log :as log]))

(s/def ::action (s/and string? #{"install" "delete" "upgrade"}))
(s/def ::target string?)
(s/def ::namespace string?)
(s/def ::charts-branch string?)
(s/def ::target-branch string?)
(s/def ::json-params (s/keys :req-un [::action ::namespace ::target]
                             :opt-un [::charts-branch ::target-branch]))
(s/def ::request (s/keys :req-un [::json-params]))

(def run-commands
  (interceptor/interceptor
   {:name ::run-commands
    :enter
    (fn [{:keys [request] :as context}]
      (let [req (s/conform ::request request)]
        (if (= req ::s/invalid)
          (do (log/warn :at ::run-commands
                        :message (s/explain-data ::request request))
              (assoc context
                     :response
                     (http-resp/bad-request "Invalid input")))
          (let [{:keys [action
                        charts-branch
                        namespace
                        target
                        target-branch]} (:json-params req)
                command (cond-> (str "bin/invoker"
                                     " -a " action
                                     " -n " namespace
                                     " -t " target)
                          charts-branch (str " -B " charts-branch)
                          target-branch (str " -b " target-branch))
                channel (async/chan)]
            (async/go
              (let [result (sh/run command)
                    new-context (assoc context
                                       :response
                                       (http-resp/ok result))]
                (async/>! channel new-context)))
            channel))))}))

(s/fdef run-commands :args (s/cat :request ::request))
