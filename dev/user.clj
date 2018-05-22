(ns user
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [invoker.system :as app]))

(reloaded.repl/set-init! app/dev-system)
