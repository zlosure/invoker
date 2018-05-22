(ns invoker.http-response
  (:require [cheshire.core :as json]
            [clojure.spec.alpha :as s]))

(s/def ::body (s/or :payload map?
                    :message string?))

(defn ok
  [body]
  {:status 200
   :headers {}
   :body (json/generate-string body)})

(s/fdef ok :args (s/cat :body ::body))

(defn bad-request
  [body]
  {:status 400
   :headers {}
   :body body})

(s/fdef bad-request :args (s/cat :body ::body))
