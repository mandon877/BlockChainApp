// 3가지 방식이 있다.
// service
// factory
// provider
angular.module('todo').factory('todoStorage', function () {
  var TODO_DATA = 'TODO_DATA';
  
  var storage = {
    todos: [],
    //  todos : [
    //   {
    //     title: '요가 수련',
    //     completed: false,
    //     createdAt: Date.now()
    //   },
    //   {
    //     title: '앵귤러 학습',
    //     completed: false,
    //     createdAt: Date.now()
    //   },
    //   {
    //     title: '운동 하기',
    //     completed: true,
    //     createdAt: Date.now()
    //   }
    //  ],
     
     _saveToLocalStorage: function (data) {
       localStorage.setItem(TODO_DATA, JSON.stringify(data))
     },
     
     _getFromLocalStorage: function () {
       return JSON.parse(localStorage.getItem(TODO_DATA)) || [];
     },
     
     get: function () {
       //storage.todos = storage._getFromLocalStorage()
       angular.copy(storage._getFromLocalStorage(), storage.todos)
       return storage.todos;
     },
     
     remove: function (todo) {
        var idx = storage.todos.findIndex(function (item) {
          return item.id == todo.id;
        })
        
        // remove from todos
        if(idx > -1) {
          //remove
          storage.todos.splice(idx, 1);
          
          //save Local Storage
          storage._saveToLocalStorage(storage.todos);
        }
     },
     
     add: function (newTodoTitle) {
        // create new todo
        var newTodo = {
          title: newTodoTitle,
          completed: false,
          createdAt: Date.now()
        };
       
       // push into todos
       storage.todos.push(newTodo);
       
       //save LocalStorage 
       storage._saveToLocalStorage(storage.todos);
     },
     
     update: function () {
         storage._saveToLocalStorage(storage.todos);
     }
  }

  return storage;
});