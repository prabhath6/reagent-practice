(ns reagent-practice.bmi-component
  (:require
    [reagent.core :as r]))

;; bmi state
(def bmi-state (r/atom {:height 180 :weight 80}))

;; helpers
(defn cal-bmi []
  (let [{:keys [height weight bmi] :as data} @bmi-state
        h (/ height 100)]
    (if (nil? bmi)
      (assoc data :bmi (/ weight (* h h))) ; if bmi is nil
      (assoc data :weight (* bmi h h))))) ; if bmi is not nil

;; input helpers
(defn slider-handler [param value min max]
  [:input {
           :type "range"
           :value value
           :min min
           :max max
           :style {:width "100%"}
           :on-change (fn [e]

                        ;; change the param value
                        (swap! bmi-state assoc param (.. e -target -value))

                        ;; if not bmi then set bmi to nil
                        (when (not= param :bmi)
                          (swap! bmi-state assoc param nil)))}])

;; component
