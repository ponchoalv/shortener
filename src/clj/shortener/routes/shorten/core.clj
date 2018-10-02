(ns shortener.routes.shorten.core
  (:require
   [ring.util.http-response :refer :all]
   [shortener.db.core :as db])
  (:import
   clojure.lang.Murmur3                                    ; Look what I found!
   org.apache.commons.validator.routines.UrlValidator))

(def validator (UrlValidator. (into-array ["http" "https"])))

(def hash-url (comp (partial format "%x")
                    #(Murmur3/hashUnencodedChars %)))

(defn get-shorten-url! [shorten-request dominios]
  (let [url (:url shorten-request)
        short (hash-url url)]
    (if (.isValid ^UrlValidator validator url)
      (if-let [_ (db/get-url {:short short})]
        (ok {:url (str ((:banco shorten-request) dominios) short)})
        (do
          (db/save-shorten! {:short short :url url})
          (ok {:url (str ((:banco shorten-request) dominios) short)})))
      (bad-request! "Formato de URL Incorrecto."))))

(defn handle-redirect [short-url]
  (if-let [url (db/get-url {:short short-url})]
    (moved-permanently (:url url))
    (not-found! (str "No existe una URL asociada al codigo: " short-url))))
