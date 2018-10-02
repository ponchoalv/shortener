(ns shortener.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [shortener.routes.shorten.core :as shorten]))

(s/defschema ShortenRequest
  {:url   s/Str
   :banco (s/enum :bsj :bsc :bsf :ber)})

(s/defschema ResponseUrl
  {:url s/Str})

(def dominios {:bsj "http://a.bsj.digital/"
               :bsc "http://a.bsc.digital/"
               :bsf "http://a.bsf.digital/"
               :ber "http://a.ber.digital/"})

(defapi service-routes
  {:swagger {:ui   "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version     "1.0.0"
                           :title       "Shortener API"
                           :description "API para sistema que recorta URLS y redirecciona"}}}}

  (context "/api" []
           :tags ["shortener"]

           (GET "/redirect/:short-url" []
                :return nil
                :path-params [short-url :- s/Str]
                :summary "Redireccionar a la url solicitada."
                (shorten/handle-redirect short-url))

           (GET "/get-shorten" []
                :return ResponseUrl
                :query-params [banco :- String
                               url :- String]
                :summary "Generar URL corta según el banco. Banco debe ser bsj, bsc, bsf o ber cualquier otro valor dará error."
                (shorten/get-shorten-url! (s/validate (s/maybe ShortenRequest) {:url url :banco (keyword banco)}) dominios))))
