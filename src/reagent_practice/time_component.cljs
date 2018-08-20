(ns reagent-practice.time-component
  (:require
   [reagent.core :as r]))

;; state
(defonce timer (r/atom (js/Date.)))

(defonce clock-color (r/atom "#f34"))

;; helpers
(defonce setTimeInterval
  (js/setInterval
   (fn [] (reset! timer (js/Date.))) 1000))

(defn greeting-message [greeting]
  [:div greeting])

;; input handlers
(defn clock []
  (let [current-time (first (clojure.string/split (.toTimeString @timer) " "))]
    [:div
     [:p {:style {:color @clock-color}} current-time]]))

(defn clock-color-handler []
  [:div
   "Time Color: "
   [:input {:type "text"
            :value @clock-color
            :on-change (fn [e]
                         (reset! clock-color (.. e -target -value)))}]])

;; component
(defn timer-component []
  [:div
   [greeting-message "Clock Color setter."]
   [clock]
    [clock-color-handler]])

