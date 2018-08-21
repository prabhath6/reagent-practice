(ns reagent-practice.fancy-fonts
  (:require [reagent.core :as r]))

;; state
(defonce greeting (r/atom "These are some fonts to test!@#$."))

;; helpers

;; event handlers
(defn text-handler []
  [:input {
           :type "text"
           :value @greeting
           :on-change (fn [e]
                        (reset! greeting (.. e -target -value)))}])

;; component
(defn fancy-fonts-component []
  (let [greet @greeting
        font-families ["Comic Sans MS" "Helvetica" "Courier New"]]
  [:div
   [:h3 "Fancy Fonts"]
   [text-handler]
   (for [f font-families]
     ^{:key f} [:p {:style {:font-family f :size "18"}} f ": " greet])]))
