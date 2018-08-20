(ns reagent-practice.background-color-changer
  (:require
   [reagent.core :as r]))

;; state
(defonce rgb-data (r/atom {:r 60 :g 130 :b 50}))

;; helpers
(defn greeting-message [greeting]
  [:div
   [:h3 greeting]])

(defn get-rgb [rgb]
  (let [{:keys [r g b]} rgb]
    (str "rgb(" (clojure.string/join ", " [r g b]) ")")))

;; slider
(defn slider [param value min max]
  [:input {
           :type "range"
           :value value
           :min min
           :max max
           :style {:width "80%"}
           :on-change (fn [e]
                        (swap! rgb-data assoc param (.. e -target -value)))}])

;; component
(defn bg-color-changer []
  (let [{:keys [r g b]} @rgb-data
        color (get-rgb @rgb-data)]
    [:div
    [greeting-message "RGB color changer"]
    [:div {:style {:background-color color}}
    [:div
      "R: " [slider :r r 0 255]]
    [:div
      "G: " [slider :g g 0 255]]
    [:div
      "B: " [slider :b b 0 255]]]]))
