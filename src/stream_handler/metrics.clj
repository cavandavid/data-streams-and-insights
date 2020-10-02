(ns stream-handler.metrics
  (:require [metrics.core :refer [new-registry]]
            [metrics.counters :refer [counter inc!]]
            [clojure.data.json :as json]))

(def registry
  "Metrics registry"
  (new-registry))

(defn metric->value
  [metric]
  (metrics.counters/value metric))

(defn mark-event
  "Marks counter to notify an important event has taken place"
  [identifier]
  (inc! (counter registry ["app" "stream-handler" (str identifier)])))

(defn get-metrics
  "Convert metrics into human readable map"
  []
  (let [metrics (.getMetrics registry)]
    (into {}
          (map (fn [[k v]]
                 [k (metric->value v)])
               metrics))))

(defn get-metrics-handler
  "Prepares a HTML response to be served"
  [request]
  {:body (json/write-str (get-metrics))
   :headers {"Content-Type" "application/json"}})
