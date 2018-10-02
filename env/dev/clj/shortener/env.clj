(ns shortener.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [shortener.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[shortener started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[shortener has shut down successfully]=-"))
   :middleware wrap-dev})
