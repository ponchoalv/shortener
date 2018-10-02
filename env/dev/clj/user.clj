(ns user
  (:require [shortener.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [shortener.core :refer [start-app]]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]
            [shortener.db.core :as db]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'shortener.core/repl-server))

(defn stop []
  (mount/stop-except #'shortener.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'db/*db*)
  (mount/start #'db/*db*)
  (binding [*ns* 'shortener.db.core]
    (conman/bind-connection db/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


