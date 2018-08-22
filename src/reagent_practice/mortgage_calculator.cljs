(ns reagent-practice.mortgage-calculator
  (:require [reagent.core :as r]))

;; state
(defonce mortgage-info (r/atom {:property-price 200000
                                :deposit 30000
                                :interest 1.380
                                :repayment-term 30}))

(defonce mortgage-result (r/atom {:mortgage-size 170000
                                  :total-repayment 207708
                                  :lifetime-cost 37708
                                  :monthly-repayment 576.97
                                  :ltv 85}))

;; helpers
(defn string->float [num]
  (let [num-with-no-strings (clojure.string/join (clojure.string/split num ","))]
    (js/parseFloat (.toFixed (js/parseFloat num-with-no-strings) 2))))

(defn float->string [num]
  (.toLocaleString num))

(defn mortgage-size [property-price deposit]
  (let [delta (- property-price deposit)]
    (Math/max delta 0)))

(defn monthly-repayment [interest repayment-term mortgage-size]
  (let [monthly-interest (/ interest 100 12)
        num-periods (* 12 repayment-term)]
    (/ (* mortgage-size (Math/pow (+ 1 monthly-interest) num-periods) monthly-interest)
       (- (Math/pow (+ 1 monthly-interest) num-periods) 1))))

(defn total-repayment [monthly-payment repayment-term]
  (* monthly-payment repayment-term 12))

(defn total-cost [total-repayment mortgage-size]
  (- total-repayment mortgage-size))

(defn ltv [mortgage-size property-price]
  (* (/ mortgage-size property-price)))

(defn compute-mortgage []
  (let [{:keys [property-price deposit interest repayment-term]} @mortgage-info
        mortgage-size-value (mortgage-size property-price deposit)
        monthly-repayment-value (monthly-repayment interest repayment-term mortgage-size-value)
        total-repayment-value (total-repayment monthly-repayment-value repayment-term)
        lifetime-cost-value (total-cost total-repayment-value mortgage-size-value)
        ltv-value (ltv mortgage-size-value property-price)]
    (swap! mortgage-result assoc :mortgage-size mortgage-size-value)
    (swap! mortgage-result assoc :monthly-repayment monthly-repayment-value)
    (swap! mortgage-result assoc :total-repayment total-repayment-value)
    (swap! mortgage-result assoc :lifetime-cost lifetime-cost-value)
    (swap! mortgage-result assoc :ltv ltv-value)))

;; input handlers

;; component
(defn mortgage-calculator-component []
  (let [{:keys [mortgage-size total-repayment lifetime-cost monthly-repayment ltv]} (compute-mortgage)]
  [:div
   [:h3 "Mortgage Calculator"]
   [:p "Mortgage Size: " (float->string mortgage-size)]
   [:p "Total Repayment: " (float->string total-repayment)]
   [:p "Lifetime Cost: " (float->string lifetime-cost)]
   [:p "Monthly Repayment: " (float->string monthly-repayment)]
   [:p "LTV: " (float->string (* ltv 100))]]))
