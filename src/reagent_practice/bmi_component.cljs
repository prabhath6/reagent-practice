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

(defn get-color-and-diagnose [bmi]
  (cond
    (< bmi 18.5) ["orange" "underweight"]
    (< bmi 25) ["inherit" "normal"]
    (< bmi 30) ["orange" "overweight"]
    :else ["red" "obese"]))

;; input helpers
(defn slider-handler [param value min max]
  [:input {
           :type "range"
           :value value
           :min min
           :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        ;; if not bmi then set bmi to nil
                        (when (not= param :bmi)
                          (swap! bmi-state assoc param nil))
                        ;; change the param value
                        (swap! bmi-state assoc param (.. e -target -value)))}])

;; component
(defn bmi-component []
  (let [{:keys [height weight bmi]} (cal-bmi)
        [color diagnose] (get-color-and-diagnose bmi)]

    ;; first executes the on-change method which updates the bmi-state
    ;; next the whole component is re-rendered.
    ;; when the component is re-rendered bmi is calculated again.

    ;; component
    [:div
     [:h3 "BMI calculator"]
     [:div
      "Height: " (int height) " cm"
      [slider-handler :height height 100 220]]
     [:div
      "Weight: " (int weight) " kg"
      [slider-handler :weight weight 30 150]]
     [:div
      "BMI: " (int bmi) " "
      [:span {:style {:color color}} diagnose]
      [slider-handler :bmi bmi 10 50]]]))
