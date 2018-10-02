(ns shortener.handler
  (:require 
            [shortener.routes.services :refer [service-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [compojure.route :as route]
            [shortener.env :refer [defaults]]
            [mount.core :as mount]
            [shortener.middleware :as middleware]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
          #'service-routes
          (route/not-found
             "page not found"))))

