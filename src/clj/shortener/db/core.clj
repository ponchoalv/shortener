(ns shortener.db.core
  (:require
   [clojure.java.jdbc :as jdbc]
   [clojure.tools.logging :as log]
   [conman.core :as conman]
   [shortener.config :refer [env]]
   [mount.core :refer [defstate]])
  (:import java.sql.Array
           [java.sql
            Date
            Timestamp
            PreparedStatement]))

(defstate ^:dynamic *db*
  :start (if-let [jdbc-url (env :database-url)]
           (conman/connect! {:jdbc-url              jdbc-url
                             :connection-test-query "select 1"})
           (do
             (log/warn "database connection URL was not found, please set :database-url in your config, e.g: dev-config.edn")
             *db*))
  :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

(extend-protocol jdbc/IResultSetReadColumn
  Array
  (result-set-read-column [v _ _] (vec (.getArray v))))
(defstate ^:dynamic *db*
  :start (if-let [jdbc-url (env :database-url)]
           (conman/connect! {:jdbc-url              jdbc-url
                             :connection-test-query "select 1"})
           (do
             (log/warn "database connection URL was not found, please set :database-url in your config, e.g: dev-config.edn")
             *db*))
  :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

(extend-protocol jdbc/IResultSetReadColumn
  Array
  (result-set-read-column [v _ _] (vec (.getArray v))))
