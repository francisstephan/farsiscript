(ns farsi.core
    (:require
      [reagent.core :as r]
      [farsi.trans]
      [farsi.clipboard :as clip]))

;; -------------------------
;; Views
;https://stackoverflow.com/questions/18411350/calling-a-done-js-callback
;http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html
;https://lambdaisland.com/episodes/clojurescript-interop
;https://lambdaisland.com/episodes/javascript-libraries-clojurescript

(defn atom-input [value]
  [:input {:type "text"
           :class "small"
           :id "entree"
           :auto-focus true
           :size 120
           :on-change #(reset! value (-> % .-target .-value farsi.trans/transl))}])

(defn shared-state []
  (let [val (r/atom "")]
    (fn []
      [:div
       [:p "Type on latin keyboard: " [:input {:type "button" :class "rebut" :value "   Reset   "
                                               :on-click #(do
                                                            (.focus (.getElementById js/document "entree" ))
                                                            (reset! val "" )
                                                            (set! (.-value (.getElementById js/document "entree" ))  "" ) ) }]
                                      [:input {:type "button" :class "rebut" :value "Copy farsi to clipboard"
                                               :on-click #(clip/copyTextToClipboard  @val)}]
                                      [:br]
                                      [atom-input val]]
       [:p "Get farsi text : "]
       [:p {:class "convert" }  [:br] @val]
       ])))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [shared-state] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
