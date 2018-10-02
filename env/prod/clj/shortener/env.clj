(ns shortener.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[shortener started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[shortener has shut down successfully]=-"))
   :middleware identity})
