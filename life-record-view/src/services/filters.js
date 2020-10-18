import Vue from "vue";

Vue.filter("largeNumber", val => parseFloat(val).toLocaleString());
