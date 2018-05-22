(ns invoker.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::namespace string?)
(s/def ::query-params (s/keys :opt-un [::namespace]))
(s/def ::request (s/keys :req-un [::query-params]))
