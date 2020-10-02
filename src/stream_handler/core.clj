(ns stream-handler.core
  (:require [stream-handler.server :refer [start-web-app]]
            [stream-sender.client :refer [send-stream]])
  (:gen-class))

(def port
  "Port that server runs on. Should probably be taken from ENV variable OR config file"
  9092)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (case (first args)
    "run-app" (start-web-app port)
    "run-client"  (send-stream (str "localhost:" port "/connect"))
    "I don't know what you want me to do"))
