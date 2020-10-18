import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentTableId:''
  },
  mutations: {
    setCurrentTableId(state, tableId){
      state.currentTableId = tableId
    }
  },
  actions: {
  },
  modules: {
  }
})
